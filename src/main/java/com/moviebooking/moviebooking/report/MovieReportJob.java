package com.moviebooking.moviebooking.report;
import com.moviebooking.moviebooking.model.MovieBooking;
import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class MovieReportJob {
    private final List<MovieBooking> bookings;

    public  MovieReportJob(List<MovieBooking> bookings){
        this.bookings = bookings;
    }

    public void generateReport() {
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
