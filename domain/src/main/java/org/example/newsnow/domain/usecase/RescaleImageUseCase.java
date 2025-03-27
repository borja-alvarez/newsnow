package org.example.newsnow.domain.usecase;

import org.example.newsnow.domain.entity.ImageRescale;
import org.example.newsnow.domain.entity.Task;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * Interface for rescaling images.
 */
public interface RescaleImageUseCase {

    /**
     * Rescales the given image.
     *
     * @param imageRescale the image to be rescaled
     * @return the path to the rescaled image
     * @throws IOException if an I/O error occurs
     */
    Task rescaleImage(ImageRescale imageRescale) throws IOException, NoSuchAlgorithmException;
}
