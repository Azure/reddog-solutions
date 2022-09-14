package com.microsoft.gbb.reddog.loyaltyservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AbstractDto<E> {

    private LocalDateTime createAt;

    private LocalDateTime lastModifiedAt;

    private String createdBy;

    private String lastModifiedBy;
}