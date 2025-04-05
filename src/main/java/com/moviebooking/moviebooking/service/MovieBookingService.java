package com.moviebooking.moviebooking.service;
import com.moviebooking.moviebooking.config.DataStorage;
import com.moviebooking.moviebooking.exception.model.BookingException;
import com.moviebooking.moviebooking.model.Movie;
import com.moviebooking.moviebooking.model.MovieBooking;
import com.moviebooking.moviebooking.request.BookingRequest;
import com.moviebooking.moviebooking.response.BookingResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    public List<MovieBooking> getAllBookings(){

        return dataStorage.allMovieBooking();
    }

    public Map<String, Double> movieRevenue(){
                movieBookingValue =  dataStorage.allMovieBooking().stream()
                .collect(Collectors.toMap(
                         moviebooked -> moviebooked.getMovieName(),
                        moviebooked -> moviebooked.getTotalPrice() + moviebooked.getTotalTax(), Double::sum
                ));
                return  movieBookingValue;
    }

    public double totalRevenue(){
        for (Double mapElement : movieBookingValue.values())
            myTotalRevenue += mapElement;
        return myTotalRevenue;
    }

    public void highestRevenue() {
        Optional<Map.Entry<String, Double>> maxEntry = movieBookingValue.entrySet().stream()
                .max(Map.Entry.comparingByValue());

        if (maxEntry.isPresent()) {
            log.info("Movie that has got the highest revenue (including tax) in a single booking: {}", maxEntry.get().getKey());
        }
    }

    public void eachMovie(){
        dataStorage.allMovieBooking().stream()
                .forEach(movie -> {

                });
    }

}
