package com.kushnir.paperki.service.exceptions;

public class NotEnoughQuantityAvailableException extends Throwable {
    public NotEnoughQuantityAvailableException(String message){
        super(message);
    }
}
