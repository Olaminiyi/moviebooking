package com.moviebooking.moviebooking.config;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moviebooking.moviebooking.model.Movie;
import com.moviebooking.moviebooking.model.MovieBooking;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.asm.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.ApplicationScope;

import static com.moviebooking.moviebooking.util.MovieJsonData.readFile;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@ApplicationScope
@Configuration
public class DataStorage {

    private  final Map<String, Movie> movieDb = new HashMap<>();
    private final List<MovieBooking> bookings = new CopyOnWriteArrayList<>();
    private final Logger log = LoggerFactory.getLogger(DataStorage.class);

    @PostConstruct
    public void loadMovies(){
        final String path = "src/main/resources/json/movies.json";
        try {
        List<Movie> movieList = readFile(path, new TypeReference<List<Movie>>() {});
            log.info("loaded movies: {}", new ObjectMapper().writeValueAsString(movieList));

        movieList.forEach(movie -> {movieDb.put(movie.getMovieName(), movie);});
        }
        catch (Exception e){
            log.info("");
        }
    }

    public Movie findMovie(String movieName){
        return movieDb.get(movieName);
    }

    public void addMovieBooking(MovieBooking booking){
        bookings.add(booking);
    }

    public List<Movie> findAllMovie(){
         return  movieDb.values().stream().toList();
    }

    public List<MovieBooking> allMovieBooking(){
        return  bookings;
    }

    public void deleteMovieBooking(MovieBooking booking){
        bookings.remove(booking);

    }
}
