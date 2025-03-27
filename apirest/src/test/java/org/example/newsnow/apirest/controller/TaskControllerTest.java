package org.example.newsnow.apirest.controller;

import org.example.newsnow.apirest.mapper.ImageRescaleMapper;
import org.example.newsnow.apirest.mapper.ImageResponseDtoMapper;
import org.example.newsnow.apirest.mapper.TaskDtoMapper;
import org.example.newsnow.domain.entity.ImageRescale;
import org.example.newsnow.domain.entity.Task;
import org.example.newsnow.domain.usecase.GetTaskUseCase;
import org.example.newsnow.domain.usecase.RescaleImageUseCase;
import org.example.newsnow.model.ImageResponseDto;
import org.example.newsnow.model.TaskDto;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {TaskController.class})
class TaskControllerTest {

    @Autowired
    TaskController taskController;

    @MockBean
    RescaleImageUseCase rescaleImageUseCase;

    @MockBean
    GetTaskUseCase getTaskUseCase;

    @MockBean
    ImageRescaleMapper imageRescaleMapper;

    @MockBean
    ImageResponseDtoMapper imageResponseDtoMapper;

    @MockBean
    TaskDtoMapper taskDtoMapper;

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
    void createTask() throws IOException, NoSuchAlgorithmException {
        Integer width = 100;
        Integer height = 100;

        Task task = Instancio.create(Task.class);
        ImageRescale imageRescale = Instancio.create(ImageRescale.class);
        ImageResponseDto imageResponseDto = Instancio.create(ImageResponseDto.class);

        when(imageRescaleMapper.toImageRescale(image, width, height)).thenReturn(imageRescale);
        when(rescaleImageUseCase.rescaleImage(imageRescale)).thenReturn(task);
        when(imageResponseDtoMapper.toDto(task)).thenReturn(imageResponseDto);

        ResponseEntity<ImageResponseDto> response = taskController.createTask(image, width, height);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(imageResponseDto, response.getBody());
    }

    @Test
    void createTask_BAD_REQUEST() throws IOException {
        Integer width = 100;
        Integer height = 100;

        when(imageRescaleMapper.toImageRescale(image, width, height)).thenThrow(new IllegalArgumentException());
        ResponseEntity<ImageResponseDto> response = taskController.createTask(image, width, height);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void createTask_INTERNAL_SERVER_ERROR() throws IOException {
        Integer width = 100;
        Integer height = 100;

        when(imageRescaleMapper.toImageRescale(image, width, height)).thenThrow(new IOException());
        ResponseEntity<ImageResponseDto> response = taskController.createTask(image, width, height);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void getTaskById() {
        String taskId = "1";
        Task task = Instancio.create(Task.class);
        TaskDto taskDto = Instancio.create(TaskDto.class);

        when(getTaskUseCase.getTask(taskId)).thenReturn(task);
        when(taskDtoMapper.toDto(task)).thenReturn(taskDto);

        ResponseEntity<TaskDto> response = taskController.getTaskById(taskId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(taskDto, response.getBody());
    }

    @Test
    void getTaskById_BAD_REQUEST() {
        when(getTaskUseCase.getTask(any(String.class))).thenThrow(new IllegalArgumentException());
        ResponseEntity<TaskDto> response = taskController.getTaskById("taskId");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}