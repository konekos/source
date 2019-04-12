package com.jasu.nettyinaction._01_Reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 那么可以有其他方式来更好的处理么，我们可以采用NIO来处理，NIO中支持的基本机制:
 *
 * 1. 非阻塞的IO读写
 *
 * 2. 基于IO事件进行分发任务，同时支持对多个fd的监听
 *
 * NIO例子已经差不多拥有reactor的影子了
 *
 * 1. 基于事件驱动-> selector（支持对多个socketChannel的监听）
 *
 * 2. 统一的事件分派中心-> dispatch
 *
 * 3. 事件处理服务-> read & write
 *
 * 事实上NIO已经解决了上述BIO暴露的1&2问题了，服务器的并发客户端有了量的提升，不再受限于一个客户端一个线程来处理，而是一个线程可以维护多个客户端（selector 支持对多个socketChannel 监听）。
 *
 * 但这依然不是一个完善的Reactor Pattern ,首先Reactor 是一种设计模式，好的模式应该是支持更好的扩展性，显然以上的并不支持，另外好的Reactor Pattern 必须有以下特点：
 *
 * 1. 更少的资源利用，通常不需要一个客户端一个线程
 *
 * 2. 更少的开销，更少的上下文切换以及locking
 *
 * 3. 能够跟踪服务器状态
 *
 * 4. 能够管理handler 对event的绑定
 *
 * @author @Jasu
 * @date 2018-12-25 15:34
 */
public class NIOServer {
    private final Selector selector;
    private final ServerSocketChannel serverSocketChannel;

    public NIOServer(int port) throws Exception {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void run() {
        while (!Thread.interrupted()) {
            try {
                //阻塞等待事件
                selector.select();
                //事件列表
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectedKeys.iterator();
                while (iterator.hasNext()) {
                    iterator.remove();
                    //分发事件
                    dispatch((SelectionKey) iterator.next());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void dispatch(SelectionKey key) throws Exception {
        if (key.isAcceptable()) {
            register(key);
        } else if (key.isReadable()) {
            read(key);
        } else if (key.isWritable()) {
            write(key);
        }
    }

    private void write(SelectionKey key) throws IOException {

    }

    private void read(SelectionKey key) throws IOException {
        ServerSocketChannel server = (ServerSocketChannel) key.channel();
        SocketChannel channel = server.accept();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (buffer.hasRemaining()) {
            System.out.println(buffer.getChar());
        }
    }

    private void register(SelectionKey key) throws Exception {
        ServerSocketChannel server = (ServerSocketChannel) key.channel();
        //获得和客户端的连接通道
        SocketChannel channel = server.accept();
        channel.configureBlocking(false);
        //客户端通道注册到selector
        channel.register(this.selector, SelectionKey.OP_READ);
    }
}
