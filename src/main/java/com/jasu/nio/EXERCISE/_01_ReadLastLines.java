package com.jasu.nio.EXERCISE;

import com.google.common.base.Charsets;
import org.apache.commons.io.input.ReversedLinesFileReader;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author @Jasu
 * @date 2018-09-12 18:22
 */
public class _01_ReadLastLines {

    public static void main(String[] args) throws IOException {
        System.out.println(readLast(Paths.get("page.html"), 3));

        reversedReader();


        getTopMiddleLastLines();
    }

    private static void reversedReader() throws IOException {
        ReversedLinesFileReader reader = new ReversedLinesFileReader(Paths.get("page.html").toFile(), Charsets.UTF_8);
        for (int i = 0; i < 3; i++) {
            System.out.println(reader.readLine());
        }
    }

    private static void getTopMiddleLastLines() throws IOException {
        long lineNum = Files.lines(Paths.get("page.html")).count();
        Stream<String> lines = Files.lines(Paths.get("page.html"));
        Iterator<String> iterator = lines.iterator();

        for (int i = 0; i < lineNum; i++) {
            if (i >= 0 && i < 2) {
                System.out.println(iterator.next());
            }
            if (i > 2 && i <= 4) {
                System.out.println(iterator.next());
            }

            if (i > lineNum - 2 && i < lineNum-1) {
                System.out.println(iterator.next());
            }
            if (iterator.hasNext()) {
                iterator.next();
            }
        }
    }


    private static List<String> readLast(Path path, long linesNum) {
        List<String> result = new ArrayList<>();
        long count = 0;
        RandomAccessFile fileRead = null;
        try {
            fileRead = new RandomAccessFile(path.toFile(), "r");
            //file size
            long length = fileRead.length();
            if (length == 0) {
                return null;
            }
            long pos = length - 1;
            while (pos > 0) {
                fileRead.seek(pos--);
                if (fileRead.readByte() == '\n') {
                    String line = fileRead.readLine();
                    result.add(line);
                    System.out.println(line);
                    count++;
                    if (count == linesNum) {
                        break;
                    }
                }
            }
            if (pos == 0) {
                fileRead.seek(0);
                result.add(fileRead.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fileRead.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
