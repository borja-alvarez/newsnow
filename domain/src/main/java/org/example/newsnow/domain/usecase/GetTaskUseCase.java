package org.example.newsnow.domain.usecase;

import org.example.newsnow.domain.entity.Task;

/**
 * Interface for retrieving tasks.
 */
public interface GetTaskUseCase {

    /**
     * Retrieves a task by its ID.
     *
     * @param taskId the ID of the task to retrieve
     * @return the task as a String
     */
    Task getTask(String taskId);
}
