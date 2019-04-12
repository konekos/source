package com.jasu.nio._12_NIO2._02_Files.ManageAttribute;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.UserDefinedFileAttributeView;

/**
 * @author @Jasu
 * @date 2018-08-31 17:10
 */
public class UDAVDemo {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("E:\\SpringSourceCode\\src\\main\\java\\com\\jasu\\nio\\_12_NIO2\\_02_Files\\ManageAttribute\\UDAVDemo.java");
        UserDefinedFileAttributeView udfav =
                Files.getFileAttributeView(path,
                        UserDefinedFileAttributeView.class);
        switch (args[0].charAt(0))
        {
            case 'W':
            case 'w': udfav.write("file.description",
                    Charset.defaultCharset().encode("sample"));
                break;
            case 'L':
            case 'l': for (String name: udfav.list())
                System.out.println(name);
                break;
            case 'R':
            case 'r': int size = udfav.size("file.description");
                ByteBuffer buf = ByteBuffer.allocateDirect(size);
                udfav.read("file.description", buf);
                buf.flip();
                System.out.println(Charset.defaultCharset().decode(buf));
                break;
            case 'D':
            case 'd': udfav.delete("file.description");
        }
    }
}
