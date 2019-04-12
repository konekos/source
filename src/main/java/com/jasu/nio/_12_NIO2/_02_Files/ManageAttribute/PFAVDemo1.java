package com.jasu.nio._12_NIO2._02_Files.ManageAttribute;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;

/**
 * @author @Jasu
 * @date 2018-08-31 14:03
 */
public class PFAVDemo1 {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("E:\\SpringSourceCode\\src\\main\\java\\com\\jasu\\nio\\_12_NIO2\\_02_Files\\PFAVDemo1.java");
        System.out.printf("Group: %b%n",
                Files.getAttribute(path, "posix:group"));
        @SuppressWarnings("unchecked")
        Set<PosixFilePermission> perms =
                (Set<PosixFilePermission>)
                        Files.getAttribute(path, "posix: permissions");
        for (PosixFilePermission perm: perms)
            System.out.printf("Permission: %s%n", perm);


        GroupPrincipal gp = path.getFileSystem().
                getUserPrincipalLookupService().
                lookupPrincipalByGroupName(args[1]);
        Files.setAttribute(path, "posix:group", gp);
        System.out.printf("Group: %b%n",
                Files.getAttribute(path, "posix:group"));
    }
}
