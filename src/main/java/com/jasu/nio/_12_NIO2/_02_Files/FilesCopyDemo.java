package com.jasu.nio._12_NIO2._02_Files;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @author @Jasu
 * @date 2018-09-05 11:12
 */
public class FilesCopyDemo {
    public static void main(String[] args) throws IOException {
        URL url = new URL("http://www.baidu.com");
        Files.copy(url.openStream(), Paths.get("page.html"), StandardCopyOption.REPLACE_EXISTING);

        Files.copy(Paths.get("page.html"), new FileOutputStream("copy.bak"));

        Files.copy(Paths.get("page.html"), Paths.get("copy.bak"), StandardCopyOption.REPLACE_EXISTING);

        Files.move(Paths.get("page.html"), Paths.get("p.html"));

        Files.deleteIfExists(Paths.get("copy.bak"));
    }
}
