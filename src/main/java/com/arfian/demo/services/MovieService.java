package com.arfian.demo.services;

import com.arfian.demo.models.entity.Movie;
import com.arfian.demo.models.response.ApiResponse;
import com.arfian.demo.repositories.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    private static final Logger logger = LoggerFactory.getLogger(MovieService.class);

    @Autowired
    private MovieRepository movieRepository;

    public Movie addMovie(Movie movie){
        return movieRepository.save(movie);
    }

    public ApiResponse<Movie> updateMovie(Movie movie){
        List<Movie> checkMovie = movieRepository.findAll();

        if(checkMovie != null){
            Movie updateUser = movieRepository.save(movie);
            return new ApiResponse<>(200, "Berhasil update akun", updateUser);
        }
        return new ApiResponse<>(404, "Akun tidak ditemukan", null);
    }

    public ApiResponse<List<Movie>> getListAllMovie() {
        List<Movie> allMovie = movieRepository.findAll();
        if(!allMovie.isEmpty()){
            return new ApiResponse<>(200, "Success", allMovie);
        }
        return new ApiResponse<>(404, "Movie tidak ditemukan", null);
    }

    public void deleteMovie(Long idMovie){
        movieRepository.deleteById(idMovie);
    }

    public ApiResponse<Movie> getMovieById(long id) {
        Optional<Movie> movie = movieRepository.findById(id);
        if(movie.isPresent()){
            return new ApiResponse(200, "Success", movie);
        }
        return new ApiResponse<>(404, "Movie tidak ditemukan", null);
    }
}
