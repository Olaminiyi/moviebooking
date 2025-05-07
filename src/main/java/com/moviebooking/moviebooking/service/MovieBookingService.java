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

        return  new BookingResponse(booking.getMovieName(), booking.getNumberOfSeats(), booking.getTotalPrice(), booking.getTotalTax(),"movie booked");
    }

    public List<MovieBooking> allMovieBooking(){

        return dataStorage.allMovieBooking();
    }

    public List<Movie> findAllTheMovie(){
        return dataStorage.findAllMovie();
    }

    public BookingResponse updateBooking(String movieName, LocalDateTime originalDateTime, BookingRequest bookingRequest){
        List <MovieBooking> bookings = dataStorage.allMovieBooking();
        Movie movie = dataStorage.findMovie(movieName);

        if(bookings == null || bookings.isEmpty() || movie == null ){
            throw  new BookingException("search not successful", HttpStatus.BAD_REQUEST.value());
        }

        MovieBooking existingMovieBooking = null;
        for(int i = 0; i < bookings.size(); i++){
            if(bookings.get(i).getMovieName().equalsIgnoreCase(movie.getMovieName()) && bookings.get(i).getDateTime().isEqual(originalDateTime)){
                existingMovieBooking = bookings.get(i);
                break;
            }
        }

        if(existingMovieBooking == null){
            throw new BookingException("Matching booking not found", HttpStatus.BAD_REQUEST.value());
        }

        if(bookingRequest.getNumberOfSeat() > movie.getAvailableSeats().get() ){
            throw new BookingException("The number of seat requested is more than seat available", HttpStatus.BAD_REQUEST.value());
        }

        double totalPrice = bookingRequest.getNumberOfSeat() * movie.getPricePerSeat();
        double totalTax = totalPrice * (movie.getTaxPercentagePerSeat()/100.0);

        movie.getAvailableSeats().addAndGet(-bookingRequest.getNumberOfSeat());

        MovieBooking updatedBooking = MovieBooking.builder()
                .movieName(bookingRequest.getMovieName())
                .numberOfSeats(new AtomicInteger(bookingRequest.getNumberOfSeat()))
                .totalTax(totalPrice)
                .dateTime(LocalDateTime.now())
                .totalTax(totalTax)
                .build();

        dataStorage.deleteMovieBooking(existingMovieBooking);
        dataStorage.addMovieBooking(updatedBooking);
        return new BookingResponse(updatedBooking.getMovieName(), updatedBooking.getNumberOfSeats(), updatedBooking.getTotalPrice(),
                updatedBooking.getTotalTax(), "booking updated");
    }

    public BookingResponse deleteMovieBooking(String movieName, LocalDateTime originalDateTime) throws DeleteBookingException {
        List<MovieBooking> bookings = dataStorage.allMovieBooking();
        Movie movie = dataStorage.findMovie(movieName);

        if (bookings == null || bookings.isEmpty() || movie == null) {
            throw new BookingException("No booking found", HttpStatus.BAD_REQUEST.value());
        }

        MovieBooking existingMovieBooking = null;
        for (int i = 0; i < bookings.size(); i++) {
            if (bookings.get(i).getMovieName().equalsIgnoreCase(movie.getMovieName()) && bookings.get(i).getDateTime().isEqual(originalDateTime)) {
                existingMovieBooking = bookings.get(i);
                break;
            }
        }

        if (existingMovieBooking == null) {
            throw new BookingException("Matching booking not found", HttpStatus.BAD_REQUEST.value());
        }
        dataStorage.deleteMovieBooking(existingMovieBooking);
        return  new BookingResponse("Booking successfully deleted");
    }

    public void addMovie(String movieName, AtomicInteger availableSeats, double pricePerSeat, double taxPercentagePerSeat){
        dataStorage.addMovie(movieName, availableSeats, pricePerSeat, taxPercentagePerSeat);
    }

        public void generateReport() {
        List<MovieBooking> bookings = dataStorage.allMovieBooking();

        if (bookings.isEmpty()){
            log.info("no bookings available.");
            return;
        }

        double totalRevenueAllMovies = 0.0;
        MovieBooking highestRevenueBooking = null;

        Map<String, List<MovieBooking>> movieBookingMap = new HashMap<>();

        for (MovieBooking booking : bookings) {
            String movieName = booking.getMovieName();
            double currentBookingRevenue = booking.getTotalPrice() + booking.getTotalTax();
            totalRevenueAllMovies += currentBookingRevenue;

            if (highestRevenueBooking == null || currentBookingRevenue > (highestRevenueBooking.getTotalPrice() + highestRevenueBooking.getTotalTax())){
                highestRevenueBooking = booking;
            }

            if (!movieBookingMap.containsKey(movieName)) {
                movieBookingMap.put(movieName, new ArrayList<>());
            }
            movieBookingMap.get(movieName).add(booking);
        }

        System.out.println("🎟️ Total Revenue (including tax) Across All Movies: £" + totalRevenueAllMovies);

        if (highestRevenueBooking != null) {
            System.out.println("\n💰 Movie with the Highest Revenue (Single Booking):");
            System.out.println("Movie: " + highestRevenueBooking.getMovieName());
            System.out.println("Revenue: £" + (highestRevenueBooking.getTotalPrice() + highestRevenueBooking.getTotalTax()));
        }

        for (Map.Entry<String, List<MovieBooking>> entry : movieBookingMap.entrySet()){
            String movieName = entry.getKey();
            List<MovieBooking> movieBookings = entry.getValue();

            double totalMovieRevenue = movieBookings.stream()
                    .mapToDouble(movie -> movie.getTotalPrice() + movie.getTotalTax())
                    .sum();

            System.out.println("\n🎬 Movie: " + movieName);
            System.out.println("Total Revenue: £" + totalMovieRevenue);
            System.out.println("Bookings:");
            System.out.println("Datetime | Seats | Revenue (inc. tax)");

            for (MovieBooking movie : movieBookings) {
                double revenue = movie.getTotalPrice() + movie.getTotalTax();
                System.out.println(movie.getDateTime() + " | " + movie.getNumberOfSeats() + " | £" + revenue);
            }
        }
    }
}
