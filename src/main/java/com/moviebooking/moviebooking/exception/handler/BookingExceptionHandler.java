package com.moviebooking.moviebooking.exception.handler;
import com.moviebooking.moviebooking.exception.model.BookingException;
import com.moviebooking.moviebooking.exception.model.ExceptionDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class BookingExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BookingException.class)
    public ResponseEntity<Object> handleBookingException(BookingException e){
        ExceptionDetail details = ExceptionDetail.builder()
                .message(e.getMessage())
                .status(e.getStatus())
                .build();
        return new ResponseEntity<>(details, HttpStatusCode.valueOf(e.getStatus()));
    }

    // generic exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception e){
        ExceptionDetail details = ExceptionDetail.builder()
                .message("internal server error")
                .status(500)
                .build();
        return new ResponseEntity<>(details, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
