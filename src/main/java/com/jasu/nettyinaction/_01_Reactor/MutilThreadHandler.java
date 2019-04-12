package com.jasu.nettyinaction._01_Reactor;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author @Jasu
 * @date 2018-12-26 14:54
 */
public class MutilThreadHandler implements Runnable {
    public static final int READING = 0, WRITING = 1, PROCESSING = 3;
    int state;
    final SocketChannel socket;
    final SelectionKey sk;
    static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public MutilThreadHandler(Selector selector, SocketChannel c) throws IOException {
        this.state = READING;
        this.socket = c;
        sk = socket.register(selector, SelectionKey.OP_READ);
        sk.attach(this);
        socket.configureBlocking(false);
    }

    @Override
    public void run() {
        if (state == READING) {
            read();
        } else if (state == WRITING) {
            write();
        }
    }

    private void read() {
        //任务异步处理
        executorService.submit(this::process);
        //下一步处理写事件
        sk.interestOps(SelectionKey.OP_WRITE);
        this.state = WRITING;
    }
    private void write() {
        //任务异步处理
        executorService.submit(this::process);
        //下一步处理读事件
        sk.interestOps(SelectionKey.OP_READ);
        this.state = READING;
    }
    /**
     * task 业务处理
     */
    public void process() {
        //do IO ,task,queue something
    }
}
