package com.jasu.nio._13_AIO.AsynchronousSocketChannel;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;

/**
 * @author @Jasu
 * @date 2018-09-17 18:02
 */
public class Attachment {
    public AsynchronousSocketChannel channel;
    public boolean isReadMode;
    public ByteBuffer buffer;
    public Thread mainThd;
}
