package com.jasu.nio._13_AIO.AsyncServerSocketChannel;

import java.io.IOException;
import java.nio.channels.CompletionHandler;

/**
 * @author @Jasu
 * @date 2018-09-17 16:38
 */
public class ReadWriteHandler implements CompletionHandler<Integer, Attachment> {

    @Override
    public void completed(Integer result, Attachment att) {
        if (result == -1) {
            try {
                att.channelClient.close();
                System.out.printf("Stopped listening to client %s%n",
                        att.clientAddr);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            return;
        }

        if (att.isReadMode) {
            att.buffer.flip();
            int limit = att.buffer.limit();
            byte[] bytes = new byte[limit];
            att.buffer.get(bytes, 0, limit);
            System.out.printf("Client at %s sends message: %s%n",
                    att.clientAddr,
                    new String(bytes));
            att.isReadMode = false;
            att.buffer.rewind();
            att.channelClient.write(att.buffer, att, this);
        } else {
            att.isReadMode = true;
            att.buffer.clear();
            att.channelClient.read(att.buffer, att, this);
        }
    }

    @Override
    public void failed(Throwable exc, Attachment attachment) {
        System.out.println("Connection with client broken");
    }
}
