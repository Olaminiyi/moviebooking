package com.moviebooking.moviebooking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.concurrent.atomic.AtomicInteger;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {
    private  String movieName;
    private AtomicInteger availableSeats; // thread safe for concurrent access since many booking will reduce the available seat;
    private  double pricePerSeat;
    private double taxPercentagePerSeat;

    public void setMovieName() {
        this.movieName = movieName;
    }


    public Movie findMovieName(String choiceMovieName){
        if(choiceMovieName != null && choiceMovieName.equals(getMovieName())){
            return Movie.builder().movieName(choiceMovieName).build();
        }
        return null;
    }
}
