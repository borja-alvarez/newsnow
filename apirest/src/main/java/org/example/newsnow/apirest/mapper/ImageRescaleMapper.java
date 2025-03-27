package org.example.newsnow.apirest.mapper;

import org.example.newsnow.domain.entity.ImageRescale;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

/**
 * Mapper interface for converting MultipartFile to ImageRescale.
 */
@Mapper(componentModel = "spring")
public interface ImageRescaleMapper {

    /**
     * Maps the given MultipartFile and dimensions to an ImageRescale object.
     *
     * @param image  the image file to be rescaled
     * @param width  the target width of the image
     * @param height the target height of the image
     * @return the ImageRescale object containing the rescaled image and dimensions
     * @throws IOException if an I/O error occurs during file reading
     * @throws IllegalArgumentException if the file is not an image
     */
    @Mapping(target = "width", source = "width")
    @Mapping(target = "height", source = "height")
    @Mapping(target = "image", source = "image")
    ImageRescale toImageRescale(MultipartFile image, Integer width, Integer height) throws IOException, IllegalArgumentException;

    /**
     * Converts the given MultipartFile to a byte array.
     *
     * @param value the MultipartFile to be converted
     * @return the byte array representation of the file
     * @throws IOException if an I/O error occurs during file reading
     * @throws IllegalArgumentException if the file is not an image
     */
    default byte[] map(MultipartFile value) throws IOException, IllegalArgumentException {
        if (!Objects.requireNonNull(value.getContentType()).startsWith("image/")) {
            throw new IllegalArgumentException("File must be an image");
        }
            return value.getBytes();
    }
}