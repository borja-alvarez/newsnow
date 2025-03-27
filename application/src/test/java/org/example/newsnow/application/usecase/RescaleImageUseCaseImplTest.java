package org.example.newsnow.application.usecase;

import org.example.newsnow.application.helper.CalculateMD5Helper;
import org.example.newsnow.application.helper.ImageRescaleHelper;
import org.example.newsnow.domain.entity.ImageRescale;
import org.example.newsnow.domain.entity.Task;
import org.example.newsnow.domain.repository.TaskRepository;
import org.example.newsnow.domain.service.CloudinaryService;
import org.example.newsnow.domain.usecase.RescaleImageUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {RescaleImageUseCaseImpl.class})
class RescaleImageUseCaseImplTest {

    @Autowired
    RescaleImageUseCase rescaleImageUseCase;

    @MockBean
    TaskRepository taskRepository;

    @MockBean
    ImageRescaleHelper imageRescaleHelper;

    @MockBean
    CalculateMD5Helper calculateMD5Helper;

    @MockBean
    CloudinaryService cloudinaryService;

    private ImageRescale imageRescale;
    private Task task;

    @BeforeEach
    void setUp() {
        imageRescale = new ImageRescale();
        imageRescale.setImage(new byte[]{1, 2, 3});
        imageRescale.setWidth(100);
        imageRescale.setHeight(100);

        task = Task.builder()
                .url("http://example.com/image.jpg")
                .timestamp(LocalDateTime.now())
                .md5("md5hash")
                .resolution("100x100")
                .build();
    }

    @Test
    void rescaleImage_Success() throws IOException, NoSuchAlgorithmException {
        File tempFile = File.createTempFile("upload", ".jpg");
        when(imageRescaleHelper.rescaleImage(imageRescale)).thenReturn(tempFile);
        when(cloudinaryService.uploadImage(tempFile)).thenReturn("http://example.com/image.jpg");
        when(calculateMD5Helper.calculateMD5(imageRescale.getImage())).thenReturn("md5hash");
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task result = rescaleImageUseCase.rescaleImage(imageRescale);

        assertNotNull(result);
        assertEquals("http://example.com/image.jpg", result.getUrl());
        assertEquals("md5hash", result.getMd5());
        assertEquals("100x100", result.getResolution());
    }

    @Test
    void rescaleImage_IOException() throws IOException {
        when(imageRescaleHelper.rescaleImage(imageRescale)).thenThrow(new IOException());

        assertThrows(IOException.class, () -> rescaleImageUseCase.rescaleImage(imageRescale));
    }

    @Test
    void rescaleImage_NoSuchAlgorithmException() throws IOException, NoSuchAlgorithmException {
        File tempFile = File.createTempFile("upload", ".jpg");
        when(imageRescaleHelper.rescaleImage(imageRescale)).thenReturn(tempFile);
        when(cloudinaryService.uploadImage(tempFile)).thenReturn("http://example.com/image.jpg");
        when(calculateMD5Helper.calculateMD5(imageRescale.getImage())).thenThrow(new NoSuchAlgorithmException());

        assertThrows(NoSuchAlgorithmException.class, () -> rescaleImageUseCase.rescaleImage(imageRescale));
    }
}