package org.example.newsnow.application.usecase;

import org.example.newsnow.application.helper.CalculateMD5Helper;
import org.example.newsnow.domain.entity.Task;
import org.example.newsnow.domain.repository.TaskRepository;
import org.example.newsnow.domain.usecase.GetTaskUseCase;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {GetTaskUseCaseImpl.class})
class GetTaskUseCaseImplTest {

    @Autowired
    GetTaskUseCase getTaskUseCase;

    @MockBean
    TaskRepository taskRepository;

    @Test
    void getTask_TaskExists() {
        Task task = Instancio.create(Task.class);
        Mockito.when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));

        Task result = getTaskUseCase.getTask(task.getId());

        assertNotNull(result);
        assertEquals(task, result);
    }

    @Test
    void getTask_TaskNotFound() {
        Mockito.when(taskRepository.findById("1")).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            getTaskUseCase.getTask("1");
        });
    }
}