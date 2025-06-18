package com.malexj.task;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/*
 * Note: When using PanacheMongoEntity, the id field is handled for you — but you can also customize it if needed.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Task extends PanacheMongoEntity {
  public String name;
  public String description;
}
