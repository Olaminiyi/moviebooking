package com.moviebooking.moviebooking.service;
import com.moviebooking.moviebooking.config.DataStorage;
import com.moviebooking.moviebooking.exception.model.BookingException;
import com.moviebooking.moviebooking.exception.model.DeleteBookingException;
import com.moviebooking.moviebooking.model.Movie;
import com.moviebooking.moviebooking.model.MovieBooking;
import com.moviebooking.moviebooking.request.BookingRequest;
import com.moviebooking.moviebooking.response.BookingResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Slf4j
@Service
public class MovieBookingService {

    private final DataStorage dataStorage;
    Map<String, Double> movieBookingValue = new HashMap<>();
    Double myTotalRevenue;

    @Autowired
    public MovieBookingService(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    public BookingResponse bookSeat (BookingRequest request){
        if( request.getNumberOfSeat() < 1 || request.getNumberOfSeat() > 4){
            throw new BookingException("invalid number of seat requested", HttpStatus.BAD_REQUEST.value());
        }

        Movie movie = dataStorage.findMovie(request.getMovieName());
        if(movie == null){
            throw new BookingException("invalid movie name", HttpStatus.BAD_REQUEST.value());
        }

        if(movie.getAvailableSeats().get() < request.getNumberOfSeat()){
            throw new BookingException("not enough seat available", HttpStatus.BAD_REQUEST.value());
        }

        movie.getAvailableSeats().addAndGet(-request.getNumberOfSeat());

        final  double totalPrice = movie.getPricePerSeat() * request.getNumberOfSeat();
        final double totalTax = totalPrice * (movie.getTaxPercentagePerSeat()/100);

        final  MovieBooking booking = MovieBooking.builder()
                .dateTime(LocalDateTime.now())
                .movieName(movie.getMovieName())
                .numberOfSeats(new AtomicInteger(request.getNumberOfSeat()))
                .totalPrice(totalPrice)
                .totalTax(totalTax)
                .build();

        dataStorage.addMovieBooking(booking);

        return  new BookingResponse(booking.getNumberOfSeats() + " Number of seat are booked for " + booking.getMovieName());
    }

    public List<MovieBooking> allMovieBooking(){

        return dataStorage.allMovieBooking();
    }

    public List<Movie> findAllTheMovie(){
        return dataStorage.findAllMovie();
    }

    public void deleteMovieBooking(MovieBooking booking) throws DeleteBookingException {
        List <MovieBooking> bookings = dataStorage.allMovieBooking();
        if(bookings == null || bookings.isEmpty()){
            throw  new DeleteBookingException("Booking list is empty", HttpStatus.BAD_REQUEST.value());
        }
               boolean removed =  bookings.removeIf(existingMovie -> existingMovie.getMovieName()
                                .equalsIgnoreCase(booking.getMovieName()) && existingMovie.getDateTime()
                                        .isEqual(booking.getDateTime()) && existingMovie.getNumberOfSeats().equals(booking.getNumberOfSeats()));
    }

}
