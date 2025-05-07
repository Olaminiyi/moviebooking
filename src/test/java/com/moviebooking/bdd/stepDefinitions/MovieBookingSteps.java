package com.moviebooking.bdd.stepDefinitions;

import com.moviebooking.moviebooking.config.DataStorage;
import com.moviebooking.moviebooking.request.BookingRequest;
import com.moviebooking.moviebooking.response.BookingResponse;
import com.moviebooking.moviebooking.service.MovieBookingService;
import io.cucumber.java.en.*;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovieBookingSteps {

    private final DataStorage dataStorage = new DataStorage();
    private final MovieBookingService movieBookingService = new MovieBookingService(dataStorage);
    private BookingResponse response;

    @Given("a movie {string} with {int} available seats")
    public void a_movie_with_available_seats(String movieName, Integer availableSeat) {
        dataStorage.addMovie(movieName, new AtomicInteger(availableSeat),  5.0, 2.0);
    }

    @When("a customer book movie {string} with {int} seats")
    public void a_customer_book_for_seats(String movieName, Integer numberOfSeatRequested) {
        BookingRequest bookingRequest = new BookingRequest(movieName, numberOfSeatRequested);
        response = movieBookingService.bookSeat(bookingRequest);
    }

    @Then("the total price should be {double}")
    public void the_total_price_should_be(Double expectedPrice) {
        assertEquals(expectedPrice, response.getTotalPrice());
    }

    @Then("the total tax should be {double}")
    public void the_total_tax_should_be(Double expectedTax) {
        assertEquals(expectedTax, response.getTotalTax());
    }
}
