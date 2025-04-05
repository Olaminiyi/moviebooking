package com.moviebooking.moviebooking.model;

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
public class Movie {
    private  String movieName;
    private AtomicInteger availableSeats; // thread safe for concurrent access since many booking will reduce the available seat;
    private  double pricePerSeat;
    private double taxPercentagePerSeat;

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public AtomicInteger getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(AtomicInteger availableSeats) {
        this.availableSeats = availableSeats;
    }

    public double getPricePerSeat() {
        return pricePerSeat;
    }

    public void setPricePerSeat(double pricePerSeat) {
        this.pricePerSeat = pricePerSeat;
    }

    public double getTaxPercentagePerSeat() {
        return taxPercentagePerSeat;
    }

    public void setTaxPercentagePerSeat(double taxPercentagePerSeat) {
        this.taxPercentagePerSeat = taxPercentagePerSeat;
    }

    public String getMovieName(){
        return movieName;
    }
}
