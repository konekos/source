package com.jasu.nio._12_NIO2._02_Files;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author @Jasu
 * @date 2018-09-03 17:09
 */
public class WritingFilesDemo {
    public static void main(String[] args) throws IOException {
        URL url = new URL("https://www.baidu.com");
        InputStreamReader inputStreamReader = new InputStreamReader(url.openStream());
        BufferedReader reader = new BufferedReader(inputStreamReader);
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        Files.write(Paths.get("C:/page.html"), lines);
    }
}
