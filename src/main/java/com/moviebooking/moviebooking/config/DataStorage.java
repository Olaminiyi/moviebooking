package com.moviebooking.moviebooking.config;
import com.moviebooking.moviebooking.model.Movie;
import com.moviebooking.moviebooking.model.MovieBooking;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.asm.TypeReference;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.ApplicationScope;

import static com.moviebooking.moviebooking.util.MovieJasonData.readFile;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@ApplicationScope
@Configuration
public class DataStorage {

    private  final Map<String, Movie> movieDb = new HashMap<>();
    private final List<MovieBooking> bookings = new CopyOnWriteArrayList<>();

    @PostConstruct
    public void loadMovies(){
        final String path = "src/test/resources/json/movies.json";
        try {
        List<Movie> movieList = readFile(path, new TypeReference<List<Movie>>() {});
        movieList.forEach(movie -> {movieDb.put(movie.getMovieName(), movie)});

        }
        catch (Exception e){
            log.info("");
        }
    }
}
