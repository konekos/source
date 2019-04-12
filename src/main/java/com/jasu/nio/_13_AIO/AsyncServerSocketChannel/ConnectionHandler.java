package com.jasu.nio._13_AIO.AsyncServerSocketChannel;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @author @Jasu
 * @date 2018-09-17 16:31
 */
public class ConnectionHandler implements CompletionHandler<AsynchronousSocketChannel, Attachment> {

    @Override
    public void completed(AsynchronousSocketChannel channelClient, Attachment attachment) {
        SocketAddress clientAddr = null;
        try {
            clientAddr = channelClient.getRemoteAddress();
            System.out.printf("Accepted connection from %s%n", clientAddr);
            attachment.channelServer.accept(attachment, this);

            Attachment newAtt = new Attachment();
            newAtt.channelServer = attachment.channelServer;
            newAtt.channelClient = channelClient;
            newAtt.isReadMode = true;
            newAtt.buffer = ByteBuffer.allocate(2048);
            newAtt.clientAddr = clientAddr;
            ReadWriteHandler rwh = new ReadWriteHandler();
            channelClient.read(newAtt.buffer, newAtt, rwh);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void failed(Throwable exc, Attachment attachment) {
        System.out.println("Failed to accept connection");
    }
}
