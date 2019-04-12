package com.jasu.nio._12_NIO2._02_Files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author @Jasu
 * @date 2018-09-05 15:31
 */
public class SymbolicLinkDemo {
    public static void main(String[] args) throws IOException {

        Files.createSymbolicLink(Paths.get("D:/clicker"), Paths.get("D:/clicker.link"));

        Files.isSymbolicLink(Paths.get("D:/clicker.link"));

        Files.readSymbolicLink(Paths.get("D:/clicker.link"));
    }
}
