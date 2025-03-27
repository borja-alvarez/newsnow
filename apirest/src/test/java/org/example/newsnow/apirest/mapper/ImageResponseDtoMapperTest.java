package org.example.newsnow.apirest.mapper;

import org.example.newsnow.domain.entity.Task;
import org.example.newsnow.model.ImageResponseDto;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ImageResponseDtoMapperImpl.class})
class ImageResponseDtoMapperTest {

    @Autowired
    ImageResponseDtoMapper imageResponseDtoMapper;

    private Task task;

    @Test
    void toDto() {
        Task task = Instancio.create(Task.class);
        ImageResponseDto result = imageResponseDtoMapper.toDto(task);

        assertNotNull(result);
        assertEquals(task.getId(), result.getId());
        assertEquals(task.getUrl(), result.getUrl());
    }

    @Test
    void toDto_NullTask() {
        ImageResponseDto result = imageResponseDtoMapper.toDto(null);

        assertNull(result);
    }
}