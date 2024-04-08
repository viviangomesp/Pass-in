package com.gomes.passin.dto.attendee.exceptions;

public class AttendeeAlreadyExistExeception extends RuntimeException{
    public AttendeeAlreadyExistExeception(String message){
        super(message);
    }
}
