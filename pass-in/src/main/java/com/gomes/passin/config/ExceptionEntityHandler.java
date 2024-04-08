package com.gomes.passin.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.gomes.passin.domain.checkin.CheckInAlreadyExistsException;
import com.gomes.passin.domain.events.exceptions.EventNotFoundException;
import com.gomes.passin.dto.attendee.exceptions.AttendeeAlreadyExistExeception;
import com.gomes.passin.dto.attendee.exceptions.AttendeeNotFoundException;
import com.gomes.passin.dto.attendee.exceptions.EventFullException;
import com.gomes.passin.dto.general.ErrorResponseDTO;

@ControllerAdvice
public class ExceptionEntityHandler {
    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity handleEventNotFound(EventNotFoundException exception){
        return ResponseEntity.notFound().build();
    }
    
    @ExceptionHandler(EventFullException.class)
    public ResponseEntity<ErrorResponseDTO>handleEventFull(EventFullException exception){
        return ResponseEntity.badRequest().body(new ErrorResponseDTO(exception.getMessage()));
    }

    @ExceptionHandler(AttendeeNotFoundException.class)
    public ResponseEntity handleAteendeeNotFound(AttendeeNotFoundException exception){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(AttendeeAlreadyExistExeception.class)
    public ResponseEntity handleAteendeeAttendeeAlreadyExists(AttendeeAlreadyExistExeception exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(CheckInAlreadyExistsException.class)
    public ResponseEntity handleCheckInAlreadyExistsException(CheckInAlreadyExistsException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

}
