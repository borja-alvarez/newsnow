package org.example.newsnow.apirest.controller;

import lombok.AllArgsConstructor;
import org.example.newsnow.apirest.mapper.ImageRescaleMapper;
import org.example.newsnow.apirest.mapper.ImageResponseDtoMapper;
import org.example.newsnow.apirest.mapper.TaskDtoMapper;
import org.example.newsnow.controller.TaskApi;
import org.example.newsnow.domain.entity.ImageRescale;
import org.example.newsnow.domain.entity.Task;
import org.example.newsnow.domain.usecase.GetTaskUseCase;
import org.example.newsnow.domain.usecase.RescaleImageUseCase;
import org.example.newsnow.model.ImageResponseDto;
import org.example.newsnow.model.TaskDto;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * TaskController is a REST controller that handles task-related operations.
 * It provides endpoints to create a task and retrieve a task by its ID.
 */
@Validated
@RestController
@AllArgsConstructor
public class TaskController  implements TaskApi {

    private final RescaleImageUseCase rescaleImageUseCase;

    private final GetTaskUseCase getTaskUseCase;

    private final ImageRescaleMapper imageRescaleMapper;

    private final ImageResponseDtoMapper imageResponseDtoMapper;

    private final TaskDtoMapper taskDtoMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<ImageResponseDto> createTask(@RequestParam("image") MultipartFile image,
                                                       @RequestParam("width") Integer width,
                                                       @RequestParam("height") Integer height) {
        try {
            ImageRescale imageRescale = imageRescaleMapper.toImageRescale(image,width,height);
            Task task = this.rescaleImageUseCase.rescaleImage(imageRescale);
            return ResponseEntity.ok(imageResponseDtoMapper.toDto(task));
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NoSuchAlgorithmException | IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<TaskDto> getTaskById(@PathVariable String taskId) {
        try{
            Task task = getTaskUseCase.getTask(taskId);
            return ResponseEntity.ok(taskDtoMapper.toDto(task));
        }catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
