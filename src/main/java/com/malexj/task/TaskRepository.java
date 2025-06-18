package com.malexj.task;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TaskRepository implements PanacheMongoRepository<Task> {
  // Optional custom methods like findByName, etc.
}
