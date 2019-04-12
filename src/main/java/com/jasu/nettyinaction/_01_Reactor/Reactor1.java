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
 * 在应用Java NIO构建Reactor Pattern中，大神 Doug Lea（让人无限景仰的java 大神）在“Scalable IO in Java”中给了很好的阐述。我们采用大神介绍的3种Reactor 来分别介绍。
 *
 * 首先我们基于Reactor Pattern 处理模式中，定义以下三种角色:
 *
 * Reactor 将I/O事件分派给对应的Handler
 *
 * Acceptor 处理客户端新连接，并分派请求到处理器链中
 *
 * Handlers 执行非阻塞读/写 任务
 *
 *
 *
 * 1、单Reactor单线程模型
 *
 * 这是最基本的单Reactor单线程模型。其中Reactor线程，负责多路分离套接字，有新连接到来触发connect 事件之后，交由Acceptor进行处理，有IO读写事件之后交给hanlder 处理。
 *
 * Acceptor主要任务就是构建handler ，在获取到和client相关的SocketChannel之后 ，绑定到相应的hanlder上，对应的SocketChannel有读写事件之后，基于racotor 分发,hanlder就可以处理了（所有的IO事件都绑定到selector上，有Reactor分发）。
 *
 * 该模型 适用于处理器链中业务处理组件能快速完成的场景。不过，这种单线程模型不能充分利用多核资源，所以实际使用的不多。
 *
 * @author @Jasu
 * @date 2018-12-25 17:02
 */
public class Reactor1 implements Runnable {
    private final Selector selector;
    private final ServerSocketChannel serverSocketChannel;

    public static void main(String[] args) throws Exception {
        Reactor1 reactor1 = new Reactor1(44444);
        reactor1.run();
    }

    Reactor1(int port) throws Exception {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);
        SelectionKey key = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //处理新连接
        key.attach(new Acceptor());
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    dispatch((SelectionKey) iterator.next());
                }
                selectionKeys.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void dispatch(SelectionKey key) {
        Runnable r = (Runnable) key.attachment();
        if (r != null) {
            r.run();
        }
    }

    private class Acceptor implements Runnable {

        @Override
        public void run() {
            try {
                SocketChannel c = serverSocketChannel.accept();
                if (c != null) {
                    new Handler(selector, c);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    final class Handler implements Runnable {
        final SocketChannel socketChannel;
        final SelectionKey key;
        ByteBuffer input = ByteBuffer.allocate(1024);
        ByteBuffer output = ByteBuffer.allocate(1024);
        static final int READING = 0, SENDING = 1;
        int state = READING;
         Handler(Selector selector, SocketChannel c) throws Exception{
             socketChannel = c;
             c.configureBlocking(false);
             // Optionally try first read now
             key = socketChannel.register(selector, 0);
             key.attach(this);
             key.interestOps(SelectionKey.OP_READ);
             selector.wakeup();
        }

        boolean inputIsComplete() {
             //...
            return true;
        }

        boolean outputIsComplete() {
             //...
            return true;
        }

        void process(){
             //...
        }


        @Override
        public void run() {
            try {
                if (state == READING) {
                    read();
                } else if (state == SENDING) {
                    send();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void send() throws IOException {
            socketChannel.read(input);
            if (inputIsComplete()) {
                process();
                state = SENDING;
                // Normally also do first write now
                key.interestOps(SelectionKey.OP_WRITE);
            }
        }

        private void read() throws IOException {
            socketChannel.write(output);
            if (outputIsComplete()) {
                key.cancel();
            }
        }
    }


}
