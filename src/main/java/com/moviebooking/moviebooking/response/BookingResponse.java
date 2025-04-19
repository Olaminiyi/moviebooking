package com.moviebooking.moviebooking.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.atomic.AtomicInteger;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingResponse {
    private  String movieName;
    private AtomicInteger seatBooked;
    private  double totalPrice;
    private double totalTax;
    private String message;

    public BookingResponse(String message){
        this.message = message;
    }

}
