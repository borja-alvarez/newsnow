package org.example.newsnow.domain.repository;

import org.example.newsnow.domain.entity.Task;
import org.springframework.data.mongodb.repository.MongoRepository;


/**
 * Repository interface for managing `Task` entities in MongoDB.
 */
public interface TaskRepository extends MongoRepository<Task, String> {
}