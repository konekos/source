package com.jasu.reactor;

import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SchedulerDemo {

    public static void main(String[] args) throws IOException {
        Scheduler immediate = Schedulers.immediate();

        Files.list(Paths.get("/Users/huangjiashu/go/src/alg")).forEach(
                path -> {
                    File file = path.toFile();
                    String name = file.getName();
                    String s = name.replaceAll("\\[", "").replaceAll("]", "");
                    file.renameTo(new File(s));
                }
        );
    }
}
