package org.example.newsnow.application.usecase;

import org.example.newsnow.domain.entity.Task;
import org.example.newsnow.domain.usecase.GetTaskUseCase;
import org.example.newsnow.domain.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Implementation of the GetTaskUseCase interface.
 * This class is responsible for retrieving tasks from the repository.
 */
@Component
public class GetTaskUseCaseImpl implements GetTaskUseCase {

    @Autowired
    TaskRepository taskRepository;


    /**
     * {@inheritDoc}
     */
    @Override
    public Task getTask(String taskId) {
        Optional<Task> task = taskRepository.findById(taskId);
        return task.orElseThrow(() -> new IllegalArgumentException("Task not found"));
    }
}
