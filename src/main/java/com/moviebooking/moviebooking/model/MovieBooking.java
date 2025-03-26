package com.moviebooking.moviebooking.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieBooking {

    Movie movie;
    private int SeatBooked;
    private LocalDateTime bookingTime;
    private double totalPrice;
    private double totalTax;

}
