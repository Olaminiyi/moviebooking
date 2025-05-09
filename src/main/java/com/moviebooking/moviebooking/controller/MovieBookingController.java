package com.moviebooking.moviebooking.controller;

import com.moviebooking.moviebooking.exception.model.BookingException;
import com.moviebooking.moviebooking.exception.model.DeleteBookingException;
import com.moviebooking.moviebooking.model.MovieBooking;
import com.moviebooking.moviebooking.request.BookingRequest;
import com.moviebooking.moviebooking.response.BookingResponse;
import com.moviebooking.moviebooking.service.MovieBookingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("api/v1/seat-booking")
@AllArgsConstructor
public class MovieBookingController {

    private MovieBookingService movieBookingService;

    @GetMapping("/")
    public String home() {
        return "Movie Booking App is running!";
    }

    @PostMapping("/book-seats")
    public ResponseEntity<BookingResponse> bookSeats(@RequestBody BookingRequest bookingRequest) {
       try {
           BookingResponse bookingResponse  = movieBookingService.bookSeat(bookingRequest);
           return ResponseEntity.ok(bookingResponse);
       } catch (BookingException e){
        return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(BookingResponse.builder()
                        .movieName("")
                        .seatBooked(new AtomicInteger(0))
                        .totalPrice(0)
                        .totalTax(0)
                        .message(e.getMessage())
                        .build());
       }
    }

    @GetMapping("/all-moviebookings")
    public List<MovieBooking> getAllBookings() {
        return movieBookingService.allMovieBooking();
    }

    @PutMapping("/update-moviebooking")
    public ResponseEntity<BookingResponse> updateBooking(@RequestParam  String movieName, LocalDateTime originalDateTime,
                                                       @RequestBody  BookingRequest bookingRequest) {
        try {
            BookingResponse bookingResponse = movieBookingService.updateBooking(movieName, originalDateTime, bookingRequest);
            return ResponseEntity.ok(bookingResponse);
        }catch (BookingException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(BookingResponse.builder()
                            .movieName("")
                            .seatBooked(new AtomicInteger(0))
                            .totalTax(0)
                            .totalPrice(0)
                            .message(e.getMessage())
                            .build()
                    );
        }
    }

    public ResponseEntity<BookingResponse> deleteMovieBooking(@RequestParam String movieName, LocalDateTime originalDateTime) throws DeleteBookingException {
        try{
            BookingResponse bookingResponse = movieBookingService.deleteMovieBooking(movieName, originalDateTime);
            return ResponseEntity.ok(bookingResponse);
        }catch (DeleteBookingException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new BookingResponse("Booking with the name and and time not found"));
        }
    }
}
