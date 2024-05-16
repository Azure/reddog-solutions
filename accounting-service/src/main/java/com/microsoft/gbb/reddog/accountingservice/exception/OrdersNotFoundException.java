package com.microsoft.gbb.reddog.accountingservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * The type Order not found exception.
 */
public class OrdersNotFoundException extends ResponseStatusException {
    /**
     * Instantiates a new Order not found exception.
     *
     * @param message the message
     */
    public OrdersNotFoundException(String message){
        super(HttpStatus.BAD_REQUEST, message);
    }
}
