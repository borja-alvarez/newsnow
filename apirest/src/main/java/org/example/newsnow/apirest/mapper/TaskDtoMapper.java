package org.example.newsnow.apirest.mapper;

import org.example.newsnow.domain.entity.Task;
import org.example.newsnow.model.TaskDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 * Mapper interface for converting Task entities to TaskDto objects.
 */
@Mapper(componentModel = "spring")
public interface TaskDtoMapper {

    /**
     * Converts a Task entity to a TaskDto.
     *
     * @param task the Task entity to convert
     * @return the converted TaskDto
     */
    @Mapping(target = "timestamp", source = "timestamp", qualifiedByName = "mapLocalDateTimeToOffsetDateTime")
    TaskDto toDto(Task task);

    /**
     * Maps a LocalDateTime to an OffsetDateTime with UTC offset.
     *
     * @param value the LocalDateTime to map
     * @return the mapped OffsetDateTime, or null if the input is null
     */
    @Named("mapLocalDateTimeToOffsetDateTime")
    default OffsetDateTime mapLocalDateTimeToOffsetDateTime(LocalDateTime value) {
        return value != null ? value.atOffset(ZoneOffset.UTC) : null;
    }
}
