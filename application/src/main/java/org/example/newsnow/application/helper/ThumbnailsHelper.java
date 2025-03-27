package org.example.newsnow.application.helper;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;

@Component
public class ThumbnailsHelper {
    public void resizeImage(byte[] imageBytes, int width, int height, OutputStream outputStream) throws IOException {
        Thumbnails.of(new ByteArrayInputStream(imageBytes))
                .size(width, height)
                .keepAspectRatio(false)
                .outputFormat("jpg")
                .toOutputStream(outputStream);
    }
}
