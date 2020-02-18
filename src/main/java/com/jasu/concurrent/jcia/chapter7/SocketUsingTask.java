package com.jasu.concurrent.jcia.chapter7;

import javax.annotation.concurrent.GuardedBy;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

/*****************************************
 * @author hjs
 * @date 2020-02-18 0:05
 *****************************************/
abstract public class SocketUsingTask<T> implements CancellableTask<T> {
    @GuardedBy("this")
    private Socket socket;

    public synchronized void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public synchronized void cancel(){
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException ignored) {

        }
    }

    @Override
    public RunnableFuture<T> newTask() {
        return new FutureTask<T>(this) {
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                try {
                    SocketUsingTask.this.cancel();
                }finally {
                    return super.cancel(mayInterruptIfRunning);
                }
            }
        };
    }
}
