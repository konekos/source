package com.jasu.concurrent.jcia.chapter6;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/*****************************************
 * @author: Jasu Wong
 * @Date: 2020-02-17 1:21
 *****************************************/
public class FutureRenderer {
    private final ExecutorService executor = Executors.newFixedThreadPool(100);

    void renderPage(CharSequence source) {
        final List<ImageInfo> imageInfos = scanForImageInfo(source);

        Callable<List<ImageData>> callable = new Callable<List<ImageData>>() {

            @Override
            public List<ImageData> call() throws Exception {
                List<ImageData> imageData = new ArrayList<>();
                for (ImageInfo imageInfo : imageInfos) {
                    imageData.add(imageInfo.downloadImage());
                }
                return imageData;
            }
        };

        Future<List<ImageData>> future = executor.submit(callable);

        try {
            List<ImageData> imageData = future.get();
            for (ImageData imageDatum : imageData) {
                renderImage(imageDatum);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            future.cancel(true);
        } catch (ExecutionException e) {
            throw launderThrowable(e.getCause());
        }

    }

    private RuntimeException launderThrowable(Throwable t) {
        if (t instanceof RuntimeException) {
            return (RuntimeException) t;
        } else if (t instanceof Error) {
            throw (Error) t;
        } else {
            throw new IllegalStateException("Not checked", t);
        }
    }

    private void renderImage(ImageData imageDatum) {

    }

    private List<ImageInfo> scanForImageInfo(CharSequence source) {
        return null;
    }

    private class ImageInfo {
        public ImageData downloadImage() {
            return null;
        }
    }

    private class ImageData {
    }
}
