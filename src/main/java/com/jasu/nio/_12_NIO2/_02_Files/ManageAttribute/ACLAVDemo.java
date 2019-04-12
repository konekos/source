package com.jasu.nio._12_NIO2._02_Files.ManageAttribute;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.AclEntry;
import java.util.List;

/**
 * @author @Jasu
 * @date 2018-08-31 16:46
 */
public class ACLAVDemo {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("E:\\SpringSourceCode\\src\\main\\java\\com\\jasu\\nio\\_12_NIO2\\_02_Files\\ManageAttribute\\ACLAVDemo.java");
        System.out.printf("Owner: %s%n%n",
                Files.getAttribute(path, "acl:owner"));
        @SuppressWarnings("unchecked")
        List<AclEntry> aclentries =
                (List<AclEntry>) Files.getAttribute(path, "acl:acl");
        for (AclEntry aclentry: aclentries)
            System.out.printf("%s%n%n", aclentry);
    }
}
