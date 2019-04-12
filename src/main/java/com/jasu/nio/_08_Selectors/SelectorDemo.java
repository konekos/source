package com.jasu.nio._08_Selectors;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author @Jasu
 * @date 2018-08-10 14:45
 */
public class SelectorDemo {
    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel scChannel = ServerSocketChannel.open();
        scChannel.configureBlocking(false);
        while (true) {
            int numReadyChannels = selector.select();
            if (numReadyChannels == 0) {
                continue;
            }
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                if (key.isAcceptable()) {
                    // A connection was accepted by a ServerSocketChannel
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    SocketChannel client = server.accept();
                    if (client == null) { // in case accept() returns null
                        continue;
                    }
                    client.configureBlocking(false);// must be nonblocking
                    // Register socket channel with selector for read operations.
                    client.register(selector, SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    // A socket channel is ready for reading.
                    SocketChannel client = (SocketChannel) key.channel();
                    // Perform work on the socket channel.
                } else if (key.isWritable()) {
                    // A socket channel is ready for writing.
                    SocketChannel client = (SocketChannel) key.channel();
                }
                keyIterator.remove();
            }
        }
    }
}
