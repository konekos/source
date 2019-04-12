package com.jasu.nettyinaction._01_Reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 单Reactor多线程模型
 *
 * 相对于第一种单线程的模式来说，在处理业务逻辑，也就是获取到IO的读写事件之后，交由线程池来处理，这样可以减小主reactor的性能开销，从而更专注的做事件分发工作了，从而提升整个应用的吞吐。
 *
 * 我们看下实现方式：
 *
 * @author @Jasu
 * @date 2018-12-26 10:50
 */
public class Reactor2 implements Runnable{
    private final Selector selector;
    private final ServerSocketChannel serverSocketChannel;

    public static void main(String[] args) throws Exception {
        Reactor1 reactor1 = new Reactor1(44444);
        reactor1.run();
    }

    public Reactor2(int port) throws Exception {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);
        SelectionKey key = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //处理新连接
        key.attach(new Reactor2.Acceptor());
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
                    new MutilThreadHandler(selector, c);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
