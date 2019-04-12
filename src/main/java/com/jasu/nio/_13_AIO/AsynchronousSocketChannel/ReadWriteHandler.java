package com.jasu.nio._13_AIO.AsynchronousSocketChannel;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;

/**
 * @author @Jasu
 * @date 2018-09-17 18:06
 */
public class ReadWriteHandler implements CompletionHandler<Integer, Attachment> {

    private BufferedReader conReader =
            new BufferedReader(new InputStreamReader(System.in));

    @Override
    public void completed(Integer result, Attachment attachment) {
        if (attachment.isReadMode) {
            attachment.buffer.flip();
            int limit = attachment.buffer.limit();
            byte[] bytes = new byte[limit];
            attachment.buffer.get(bytes, 0, limit);
            String msg = new String(bytes, Charset.defaultCharset());
            System.out.printf("Server responded: %s%n", msg);

            try {
                msg = "";
                while (msg.length() == 0) {
                    System.out.print("Enter message (\"end\" to quit): ");
                    msg = conReader.readLine();
                }
                if (msg.equalsIgnoreCase("end")) {
                    attachment.mainThd.interrupt();
                    return;
                }
            } catch (IOException e) {
                System.err.println("Unable to read from console");
            }

            attachment.isReadMode = false;
            attachment.buffer.clear();
            byte[] data = msg.getBytes();
            attachment.buffer.put(data);
            attachment.buffer.flip();
            attachment.channel.write(attachment.buffer, attachment, this);
        } else {
            attachment.isReadMode = true;
            attachment.buffer.clear();
            attachment.channel.read(attachment.buffer, attachment, this);
        }
    }

    @Override
    public void failed(Throwable exc, Attachment attachment) {
        System.err.println("Server not responding");
        System.exit(1);
    }
}
