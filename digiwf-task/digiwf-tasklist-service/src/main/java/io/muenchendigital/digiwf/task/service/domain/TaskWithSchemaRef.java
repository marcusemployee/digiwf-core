package io.muenchendigital.digiwf.task.service.domain;

import io.holunda.polyflow.view.Task;
import lombok.Data;

@Data
public class TaskWithSchemaRef {
  private final Task task;
  private final String schemaRef;
}