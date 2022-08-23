package com.microsoft.gbb.rasa.orderservice.dto;

import java.io.Serializable;

public record CustomerOrderItemDto(Long id, int quantity) implements Serializable {
}
