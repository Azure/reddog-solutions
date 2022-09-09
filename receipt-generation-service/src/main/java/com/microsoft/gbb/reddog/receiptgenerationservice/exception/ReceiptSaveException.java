package com.microsoft.gbb.reddog.receiptgenerationservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * The type Order not found exception.
 */
public class ReceiptSaveException extends ResponseStatusException {
    /**
     * Instantiates a new Order not found exception.
     *
     * @param message the message
     */
    public ReceiptSaveException(String message){
        super(HttpStatus.BAD_REQUEST, message);
    }
}
