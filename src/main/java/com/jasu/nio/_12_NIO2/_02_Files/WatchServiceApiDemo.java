package com.jasu.nio._12_NIO2._02_Files;

import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.*;

/**
 * @author @Jasu
 * @date 2018-09-13 17:23
 */
public class WatchServiceApiDemo {
    public static void main(String[] args) throws IOException {

        FileSystem fileSystem = FileSystems.getDefault();

            WatchService watchService = fileSystem.newWatchService();
            Path dir = fileSystem.getPath("D:/bin");
            dir.register(watchService, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
            for (; ; ) {
                WatchKey key;
                try {
                    key = watchService.take();
                } catch (InterruptedException e) {
                    return;
                }
                for (WatchEvent event : key.pollEvents()) {
                    WatchEvent.Kind kind = event.kind();
                    if (kind == OVERFLOW) {
                        System.out.println("overflow");
                        continue;
                    }
                    WatchEvent watchEvent = event;
                    Path path = (Path) watchEvent.context();
                    System.out.printf("%s: %s%n", watchEvent.kind(), path);
                }
                boolean valid = key.reset();
                if (!valid)
                    break;
            }
    }
}
