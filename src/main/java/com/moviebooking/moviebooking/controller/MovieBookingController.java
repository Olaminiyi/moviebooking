package com.moviebooking.moviebooking.controller;

import com.moviebooking.moviebooking.model.MovieBooking;
import com.moviebooking.moviebooking.request.BookingRequest;
import com.moviebooking.moviebooking.response.BookingResponse;
import com.moviebooking.moviebooking.service.MovieBookingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/seat-booking")
@AllArgsConstructor
public class MovieBookingController {

    private MovieBookingService movieBookingService;

    @PostMapping("/book-seats")
    public BookingResponse bookSeats(@RequestBody BookingRequest bookingRequest){
        return movieBookingService.bookSeat(bookingRequest);
    }

    @GetMapping("/all-movies")
    public List<MovieBooking> getAllBookings(){
        return movieBookingService.allMovieBooking();
    }


}
