package com.moviebooking.unit.service;

import com.moviebooking.moviebooking.config.DataStorage;
import com.moviebooking.moviebooking.exception.model.BookingException;
import com.moviebooking.moviebooking.model.Movie;
import com.moviebooking.moviebooking.request.BookingRequest;
import com.moviebooking.moviebooking.response.BookingResponse;
import com.moviebooking.moviebooking.service.MovieBookingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MovieBookingServiceTest {

    @InjectMocks
    private MovieBookingService movieBookingService;

    @Mock
    private DataStorage dataStorage;

    @Test
    void findAllMovie(){
      Movie movie =  dataStorage.addMovie("Joy",new AtomicInteger(5), 7, 5);
        lenient().when(dataStorage.findAllMovie()).thenReturn((List<Movie>) movie);

    }


    @Test
    void numberOfSeatRequestedGreaterThanTest(){
        BookingRequest request = new BookingRequest("Spiderman",7);

        Movie movie = Movie.builder()
                .movieName("Spiderman")
                .availableSeats(new AtomicInteger(10))
                .pricePerSeat(40.0)
                .taxPercentagePerSeat(10.0)
                .build();

       lenient().when(dataStorage.findMovie("Spiderman")).thenReturn(movie);

        BookingException exception = assertThrows(BookingException.class, () -> movieBookingService.bookSeat(request));
        assertEquals("invalid number of seat requested", exception.getMessage());
        assertEquals(400, exception.getStatus());
    }

    @Test
    void numberOfSeatRequestedLessThanTest(){
        BookingRequest request = new BookingRequest("Spiderman",0);

        Movie movie = Movie.builder()
                .movieName("Spiderman")
                .availableSeats(new AtomicInteger(10))
                .pricePerSeat(40.0)
                .taxPercentagePerSeat(10.0)
                .build();

        lenient().when(dataStorage.findMovie("Spiderman")).thenReturn(movie);

        BookingException exception = assertThrows(BookingException.class, () -> movieBookingService.bookSeat(request));
        assertEquals("invalid number of seat requested", exception.getMessage());
        assertEquals(400, exception.getStatus());
    }

    @Test
    void nullMovieTes(){
        BookingRequest request = new BookingRequest("Lion King",3);

        BookingException exception = assertThrows(BookingException.class, () -> movieBookingService.bookSeat(request));
        assertEquals("invalid movie name", exception.getMessage());
        assertEquals(400, exception.getStatus());
    }

    @Test
    void numberOfSeatAvailableTest(){
        BookingRequest request= new BookingRequest("Breadth of life", 4);

        Movie movie = Movie.builder()
                .movieName("Breadth of life")
                .availableSeats(new AtomicInteger(3))
                .pricePerSeat(20.0)
                .taxPercentagePerSeat(5.0)
                .build();

        when(dataStorage.findMovie("Breadth of life")).thenReturn(movie);
        BookingException exception = assertThrows(BookingException.class, () -> movieBookingService.bookSeat(request));
        assertEquals("not enough seat available", exception.getMessage());
        assertEquals(400, exception.getStatus());
    }

    @Test
    void bookMovieTest() {
        BookingRequest request = new BookingRequest("Alakada", 4);

        Movie movie = Movie.builder()
                .movieName("Alakada")
                .availableSeats(new AtomicInteger(7))
                .pricePerSeat(5.0)
                .taxPercentagePerSeat(2.0)
                .build();

        when(dataStorage.findMovie("Alakada")).thenReturn(movie);
        BookingResponse response = movieBookingService.bookSeat(request);
        assertEquals("movie booked", response.getMessage());
        assertEquals(20, response.getTotalPrice());
        assertEquals(0.4, response.getTotalTax());
    }
}
