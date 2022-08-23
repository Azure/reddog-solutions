package com.microsoft.gbb.rasa.orderservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * The type Order not found exception.
 */
public class OrderNotFoundException extends ResponseStatusException {
    /**
     * Instantiates a new Order not found exception.
     *
     * @param message the message
     */
    public OrderNotFoundException(String message){
        super(HttpStatus.BAD_REQUEST, message);
    }
}
