package org.tub.tubtextservice.adapter.out.file;

import java.nio.file.Path;

public class FileWatcher {
    public static void waitForFile(Path path, int attempts) {
        if (attempts == 4) {
            throw new RuntimeException("File not found: " + path.toAbsolutePath());
        }
        if (!path.toFile().exists()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            waitForFile(path, attempts + 1);
        }
    }
}
