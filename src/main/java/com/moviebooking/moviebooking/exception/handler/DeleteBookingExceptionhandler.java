package com.moviebooking.moviebooking.exception.handler;

import com.moviebooking.moviebooking.exception.model.DeleteBookingException;
import com.moviebooking.moviebooking.exception.model.ExceptionDetail;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class DeleteBookingExceptionhandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DeleteBookingException.class)
    public ResponseEntity<Object> deleteExceptionHandler(DeleteBookingException e){
        ExceptionDetail detail = ExceptionDetail.builder()
                .message(e.getMessage())
                .status(e.getStatus())
                .build();
        return new ResponseEntity<>(detail, HttpStatusCode.valueOf(e.getStatus()));
    }
}
