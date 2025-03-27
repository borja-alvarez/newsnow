package org.example.newsnow.infrastructure.output.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.AllArgsConstructor;
import org.example.newsnow.domain.service.CloudinaryService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Service implementation for handling Cloudinary operations.
 */
@Service
@AllArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;

    /**
     * Uploads an image file to Cloudinary.
     *
     * @param file the image file to upload
     * @return the URL of the uploaded image
     * @throws IOException if an I/O error occurs during upload
     */
    @Override
    public String uploadImage(File file) throws IOException {
        Map params = ObjectUtils.asMap(
                "use_filename", true,
                "unique_filename", false,
                "overwrite", true
        );

        Map imageFromCloudinary = cloudinary.uploader().upload(file, params);
        return (String) imageFromCloudinary.get("url");
    }
}