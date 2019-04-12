package com.jasu.nio._13_AIO.AsyncServerSocketChannel;

import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;

/**
 * @author @Jasu
 * @date 2018-09-17 16:29
 */
public class Attachment {
    public AsynchronousServerSocketChannel channelServer;
    public AsynchronousSocketChannel channelClient;
    public boolean isReadMode;
    public ByteBuffer buffer;
    public SocketAddress clientAddr;
}
