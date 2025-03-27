package org.example.newsnow.apirest.mapper;

import org.example.newsnow.domain.entity.Task;
import org.example.newsnow.model.ImageResponseDto;
import org.mapstruct.Mapper;


/**
 * Mapper interface for converting a URL string to an ImageResponseDto.
 */
@Mapper(componentModel = "spring")
public interface ImageResponseDtoMapper {

    /**
     * Converts a URL string to an ImageResponseDto.
     *
     * @param url the URL of the image
     * @return the ImageResponseDto containing the image data
     */
    ImageResponseDto toDto(Task imageUrl);
}
