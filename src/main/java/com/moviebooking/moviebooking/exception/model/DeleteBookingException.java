package com.moviebooking.moviebooking.exception.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class DeleteBookingException extends Exception{
     private final int status;

     public DeleteBookingException(String message, int status){
         super(message);
         this.status = status;
     }
}
