package org.example.newsnow.apirest.mapper;

import org.example.newsnow.domain.entity.Task;
import org.example.newsnow.model.TaskDto;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TaskDtoMapperImpl.class})
class TaskDtoMapperTest {

    @Autowired
    TaskDtoMapper taskDtoMapper;

    private Task task;

    @BeforeEach
    void setUp() {
        task = Instancio.create(Task.class);
    }

    @Test
    void toDto() {
        TaskDto result = taskDtoMapper.toDto(task);

        assertNotNull(result);
        assertEquals(task.getId(), result.getId());
        assertEquals(task.getUrl(), result.getUrl());
        assertEquals(task.getMd5(), result.getMd5());
        assertEquals(task.getResolution(), result.getResolution());
        assertEquals(task.getTimestamp().atOffset(ZoneOffset.UTC), result.getTimestamp());
    }

    @Test
    void toDto_NullTask() {
        TaskDto result = taskDtoMapper.toDto(null);

        assertNull(result);
    }
}