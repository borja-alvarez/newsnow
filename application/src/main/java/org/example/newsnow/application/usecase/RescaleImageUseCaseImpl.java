package org.example.newsnow.application.usecase;

import org.example.newsnow.application.helper.CalculateMD5Helper;
import org.example.newsnow.application.helper.ImageRescaleHelper;
import org.example.newsnow.domain.entity.ImageRescale;
import org.example.newsnow.domain.entity.Task;
import org.example.newsnow.domain.repository.TaskRepository;
import org.example.newsnow.domain.service.CloudinaryService;
import org.example.newsnow.domain.usecase.RescaleImageUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

/**
 * Implementation of the RescaleImageUseCase interface.
 * This class is responsible for rescaling images and uploading them to Cloudinary.
 */
@Component
@AllArgsConstructor
public class RescaleImageUseCaseImpl implements RescaleImageUseCase {

    private final ImageRescaleHelper imageRescaleHelper;
    private final CalculateMD5Helper calculateMD5Helper;
    private final CloudinaryService cloudinaryService;
    private final TaskRepository taskRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Task rescaleImage(ImageRescale imageRescale) throws IOException, NoSuchAlgorithmException {
        File tempFile = imageRescaleHelper.rescaleImage(imageRescale);
        String url = cloudinaryService.uploadImage(tempFile);
        String md5 = calculateMD5Helper.calculateMD5(imageRescale.getImage());
        String resolution = imageRescale.getWidth() + "x" + imageRescale.getHeight();
        Task imageUrl = Task.builder()
                .url(url)
                .timestamp(LocalDateTime.now())
                .md5(md5)
                .resolution(resolution)
                .build();
        taskRepository.save(imageUrl);
        return imageUrl;
    }


}
