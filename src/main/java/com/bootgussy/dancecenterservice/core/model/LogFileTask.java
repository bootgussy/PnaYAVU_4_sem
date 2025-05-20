package com.bootgussy.dancecenterservice.core.model;

import java.nio.file.Path;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogFileTask {
    private String taskId;
    private String status;
    private Path filePath;
    private String errorMessage;

    public LogFileTask(String taskId) {
        this.taskId = taskId;
        this.status = "PENDING";
    }
}