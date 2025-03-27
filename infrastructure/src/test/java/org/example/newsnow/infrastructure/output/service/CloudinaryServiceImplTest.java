package org.example.newsnow.infrastructure.output.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import org.example.newsnow.domain.service.CloudinaryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = {CloudinaryServiceImpl.class})
class CloudinaryServiceImplTest {

    @MockBean
    Cloudinary cloudinary;

    @MockBean
    Uploader uploader;

    @Autowired
    CloudinaryService cloudinaryService;

    @Test
    void uploadImage_Success() throws IOException {
        File file = new File("test.jpg");
        Map<String, String> uploadResult = Map.of("url", "http://example.com/image.jpg");
        when(cloudinary.uploader()).thenReturn(uploader);
        when(uploader.upload(any(), any())).thenReturn(uploadResult);

        String result = cloudinaryService.uploadImage(file);

        assertNotNull(result);
        assertEquals("http://example.com/image.jpg", result);
    }

    @Test
    void uploadImage_IOException() throws IOException {
        File file = new File("test.jpg");
        when(cloudinary.uploader()).thenReturn(uploader);
        when(uploader.upload(any(File.class), any(Map.class))).thenThrow(new IOException());

        assertThrows(IOException.class, () -> cloudinaryService.uploadImage(file));
    }
}