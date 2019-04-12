package com.jasu.nio._12_NIO2._02_Files;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumSet;

import static java.nio.file.StandardOpenOption.*;

/**
 * @author @Jasu
 * @date 2018-09-04 11:15
 */
public class SeekableByteChannelDemo {
    final static int REC_LEN = 50;
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("emp");
        FileChannel fc;
        fc = FileChannel.open(path, CREATE, WRITE, SYNC).position(REC_LEN * 2);
        ByteBuffer buffer = ByteBuffer.wrap("John Smith".getBytes());
        fc.write(buffer);
        buffer.clear();
        SeekableByteChannel sbc;
        sbc = Files.newByteChannel(path, EnumSet.of(READ)).position(REC_LEN * 2);
        sbc.read(buffer);
        sbc.close();
        System.out.println(new String(buffer.array()));
    }
}
