package org.example.newsnow.apirest.mapper;

import org.example.newsnow.domain.entity.ImageRescale;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {ImageRescaleMapperImpl.class})
class ImageRescaleMapperTest {

    @Autowired
    ImageRescaleMapper imageRescaleMapper;

    private MultipartFile image;

    @BeforeEach
    void setUp() {
        image = new MockMultipartFile(
                "file",
                "image.png",
                "image/png",
                "test image content".getBytes()
        );
    }

    @Test
    void toImageRescale() throws IOException {
        Integer width = Instancio.create(Integer.class);
        Integer height = Instancio.create(Integer.class);

        ImageRescale expectedImageRescale = new ImageRescale();
        expectedImageRescale.setImage(image.getBytes());
        expectedImageRescale.setWidth(width);
        expectedImageRescale.setHeight(height);

        ImageRescale result = imageRescaleMapper.toImageRescale(image, width, height);

        assertNotNull(result);
        assertEquals(expectedImageRescale, result);
    }

    @Test
    void toImageRescale_IllegalArgumentException() throws IOException {
        MultipartFile nonImageFile = new MockMultipartFile(
                "file",
                "document.txt",
                "text/plain",
                "test content".getBytes()
        );

        assertThrows(IllegalArgumentException.class, () -> {
            imageRescaleMapper.toImageRescale(nonImageFile, 100, 100);
        });
    }

    @Test
    void toImageRescale_NullArguments() throws IOException {
        ImageRescale result = imageRescaleMapper.toImageRescale(null, null, null);
        assertNull(result);
    }
}