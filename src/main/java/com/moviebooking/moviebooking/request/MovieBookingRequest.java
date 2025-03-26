package com.moviebooking.moviebooking.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieBookingRequest {

    private String movieName;
    private  String customerName;
    private String email;
    private LocalDateTime bookingDate;
}
