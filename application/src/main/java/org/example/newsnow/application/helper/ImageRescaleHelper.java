package org.example.newsnow.application.helper;

import net.coobird.thumbnailator.Thumbnails;
import org.example.newsnow.domain.entity.ImageRescale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Helper class for rescaling images.
 */
@Component
public class ImageRescaleHelper {

    @Autowired
    private ThumbnailsHelper thumbnailsHelper;

    /**
     * Rescales the given image to the specified width and height.
     *
     * @param imageRescale the image rescale parameters, including the image data, width, and height
     * @return a temporary file containing the rescaled image
     * @throws IOException if an I/O error occurs during the rescaling process
     */
    public File rescaleImage(ImageRescale imageRescale) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        thumbnailsHelper.resizeImage(imageRescale.getImage(), imageRescale.getWidth(), imageRescale.getHeight(), outputStream);

        byte[] fileBytes = outputStream.toByteArray();

        File tempFile = File.createTempFile("upload", ".jpg");
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(fileBytes);
        }

        return tempFile;
    }
}