package org.example.newsnow.domain.entity;

import lombok.Data;


/**
 * Represents an image that can be rescaled.
 */
@Data
public class ImageRescale {

    /**
     * The image data in byte array format.
     */
    private byte[] image;

    /**
     * The width to which the image should be rescaled.
     */
    private Integer width;

    /**
     * The height to which the image should be rescaled.
     */
    private Integer height;

}
