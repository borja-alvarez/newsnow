package org.example.newsnow.domain.service;

import java.io.File;
import java.io.IOException;

/**
 * Interface for Cloudinary service operations.
 */
public interface CloudinaryService {

    /**
     * Uploads an image to Cloudinary.
     *
     * @param file the image file to upload
     * @return the URL of the uploaded image
     * @throws IOException if an I/O error occurs during upload
     */
    public String uploadImage(File file) throws IOException;
}