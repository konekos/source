package com.jasu.concurrent.jcia.chapter6;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/*****************************************
 * @author: Jasu Wong
 * @Date: 2020-02-17 1:41
 *****************************************/
public class Renderer {
    private final ExecutorService executor;


    public Renderer(ExecutorService executor) {
        this.executor = executor;
    }

    void renderPage(CharSequence source) {
        final List<Renderer.ImageInfo> imageInfos = scanForImageInfo(source);
        CompletionService<ImageData> completionService = new ExecutorCompletionService<>(executor);
        for (ImageInfo imageInfo : imageInfos) {
            completionService.submit(new Callable<ImageData>() {
                @Override
                public ImageData call() throws Exception {
                    return imageInfo.downloadImage();
                }
            });
        }

        renderText(source);

        try {
            for (ImageInfo imageInfo : imageInfos) {
                Future<ImageData> future = completionService.take();
                ImageData imageData = future.get();
                renderImage(imageData);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            throw launderThrowable(e.getCause());
        }

    }

    private void renderText(CharSequence source) {

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

    private void renderImage(Renderer.ImageData imageDatum) {

    }

    private List<Renderer.ImageInfo> scanForImageInfo(CharSequence source) {
        return null;
    }

    private class ImageInfo {
        public Renderer.ImageData downloadImage() {
            return null;
        }
    }

    private class ImageData {
    }
}
