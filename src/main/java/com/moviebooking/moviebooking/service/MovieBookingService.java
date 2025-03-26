package com.moviebooking.moviebooking.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moviebooking.moviebooking.model.Movie;
import com.moviebooking.moviebooking.model.MovieBooking;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MovieBookingService {

    private MovieBooking movieBooking;
    private  Movie movie;

    List<MovieBooking> movieBookingList = new ArrayList<>();

    public MovieBookingService( MovieBooking movieBooking, Movie movie){
        this.movie = movie;
        this.movieBooking = movieBooking;
    }

//    public Movie findMovieByName(Movie selectedMovie){
//        try {
//            if(selectedMovie.getMovieName() != null && selectedMovie.getMovieName().equals(movie.getMovieName())){
//                return selectedMovie;
//            }
//        }
//        catch (Exception e){
//            log.info("The movie is not listed");
//        }
//         return null;
//    }

    public void bookMovie (String movieName, int numberOfSeatRequested, String email){
        Movie newMovie = movie.findMovieName(movieName);

        if (newMovie != null ){

        }
        if (numberOfSeatRequested < 1 || numberOfSeatRequested > 4){
            throw new IllegalArgumentException("Invalid number of seat booking");
        }

        if (numberOfSeatRequested > newMovie.getAvailableSeats().get()){
            throw  new IllegalArgumentException("The number of seat requested is more than the number of seat available");
        }

        double totalPrice = newMovie.getPricePerSeat() * numberOfSeatRequested;
        double totalTax = (newMovie.getTaxPercentagePerSeat() / 100) * totalPrice;

        MovieBooking movieBooking1 = new MovieBooking();
        movieBooking1.setMovie(newMovie);
        movieBooking1.setBookingTime(LocalDateTime.now());
        movieBooking1.setTotalTax(totalTax);
        movieBooking1.setSeatBooked(numberOfSeatRequested);

        movieBookingList.add(movieBooking1);
    }

    public  String saveBookingsToJson() throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(movieBookingList);
    }
}
