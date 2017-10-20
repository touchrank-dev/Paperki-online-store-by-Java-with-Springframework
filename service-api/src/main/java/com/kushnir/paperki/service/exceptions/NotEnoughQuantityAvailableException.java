package com.kushnir.paperki.service.exceptions;

public class NotEnoughQuantityAvailableException extends Exception {
    public NotEnoughQuantityAvailableException(String message){
        super(message);
    }
}
