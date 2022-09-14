package com.microsoft.gbb.rasa.accountingservice.dto;

import java.io.Serializable;

public record CustomerOrderItemDto(Long id, int quantity) implements Serializable {
}
