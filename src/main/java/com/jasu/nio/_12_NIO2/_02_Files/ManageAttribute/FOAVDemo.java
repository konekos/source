package com.jasu.nio._12_NIO2._02_Files.ManageAttribute;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.UserPrincipal;

/**
 * @author @Jasu
 * @date 2018-08-31 15:04
 */
public class FOAVDemo {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("E:\\SpringSourceCode\\src\\main\\java\\com\\jasu\\nio\\_12_NIO2\\_02_Files\\FOAVDemo.java");
        System.out.printf("Owner: %s%n", Files.getOwner(path));
        UserPrincipal up = path.getFileSystem().
                getUserPrincipalLookupService().
                lookupPrincipalByName("Judy");
        System.out.println(up);
        Files.setOwner(path, up);
        System.out.printf("Owner: %s%n", Files.getOwner(path));
    }
}
