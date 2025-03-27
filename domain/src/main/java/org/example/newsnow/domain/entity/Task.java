package org.example.newsnow.domain.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Represents a task entity.
 */
@Data
@Builder
@EqualsAndHashCode
@Document(collection = "tasks")
public class Task {
    /**
     * The unique identifier of the task.
     */
    @Id
    private String id;

    /**
     * The URL associated with the task.
     */
    private String url;

    /**
     * The timestamp when the task was created.
     */
    private LocalDateTime timestamp;

    /**
     * The MD5 hash of the task content.
     */
    private String md5;

    /**
     * The resolution status of the task.
     */
    private String resolution;
}