package com.jasu.nio._07_Channels;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

/**
 * @author @Jasu
 * @date 2018-08-08 15:07
 */
public class ChannelDemo5 {

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.err.println("usage: java ChannelDemo filespec");
            return;
        }
        try (FileInputStream fis = new FileInputStream(args[0])) {
            FileChannel inChannel = fis.getChannel();
            WritableByteChannel outChannel = Channels.newChannel(System.out);
            inChannel.transferTo(0, inChannel.size(), outChannel);
        }
    }
}
