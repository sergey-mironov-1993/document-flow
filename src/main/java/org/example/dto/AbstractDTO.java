package org.example.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class AbstractDTO {
    private LocalDateTime createdAt = LocalDateTime.now();
    private Boolean isDeleted = false;
}
