package org.example.newsnow.application.helper;

import org.example.newsnow.domain.entity.ImageRescale;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {ImageRescaleHelper.class})
class ImageRescaleHelperTest {

    @Autowired
    ImageRescaleHelper imageRescaleHelper;

    @MockBean
    ThumbnailsHelper thumbnailsService;

    ImageRescale imageRescale;

    @BeforeEach
    void setUp() {
        imageRescale = Instancio.create(ImageRescale.class);
    }

    @Test
    void rescaleImage() throws IOException {
        File result = imageRescaleHelper.rescaleImage(imageRescale);
        verify(thumbnailsService, times(1))
                .resizeImage(eq(imageRescale.getImage()), eq(imageRescale.getWidth()), eq(imageRescale.getHeight()), any(OutputStream.class));
        assertNotNull(result);
        result.deleteOnExit();
    }

    @Test
    void rescaleImage_IOException() throws IOException {
        doThrow(IOException.class).when(thumbnailsService)
                .resizeImage(eq(imageRescale.getImage()), eq(imageRescale.getWidth()), eq(imageRescale.getHeight()), any(OutputStream.class));

        assertThrows(IOException.class, () -> imageRescaleHelper.rescaleImage(imageRescale));
    }
}