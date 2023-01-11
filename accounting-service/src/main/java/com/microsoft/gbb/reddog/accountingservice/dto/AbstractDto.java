package com.microsoft.gbb.reddog.accountingservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AbstractDto<E> {
    @JsonProperty("createdAt")
    private LocalDateTime createdAt = LocalDateTime.now();
    @JsonProperty("lastModifiedAt")
    private LocalDateTime lastModifiedAt;
}