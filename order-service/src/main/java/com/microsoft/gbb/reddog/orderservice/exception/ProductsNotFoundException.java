package com.microsoft.gbb.reddog.orderservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * The type Order not found exception.
 */
public class ProductsNotFoundException extends ResponseStatusException {
    /**
     * Instantiates a new Order not found exception.
     *
     * @param message the message
     */
    public ProductsNotFoundException(String message){
        super(HttpStatus.BAD_REQUEST, message);
    }
}
