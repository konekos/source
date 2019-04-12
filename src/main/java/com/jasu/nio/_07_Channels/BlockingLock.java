package com.jasu.nio._07_Channels;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author @Jasu
 * @date 2018-08-08 16:26
 */
public class BlockingLock {


    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        SocketChannel sc = null;
        Object lock = ssc.blockingLock();
        // Thread might block when obtaining the lock associated with the lock object.

        synchronized (lock) {
            // Current thread owns the lock. No other thread can change blocking mode.
            // Obtaining server socket channel's current blocking mode.
            boolean blocking = ssc.isBlocking();

            // Set server socket channel to nonblocking.
            ssc.configureBlocking(false);

            // Obtain next connection, which is null when there is no connection.
            sc = ssc.accept();

            // Restore previous blocking mode.
            ssc.configureBlocking(blocking);

            // The lock is released and some other thread may modify the server socket channel's blocking mode.
            if (sc != null) {
                communicateWithSocket(sc);
            }
        }
    }

    private static void communicateWithSocket(SocketChannel sc) {

    }
}
