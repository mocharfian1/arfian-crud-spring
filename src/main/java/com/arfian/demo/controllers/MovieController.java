package com.arfian.demo.controllers;

import com.arfian.demo.models.entity.Movie;
import com.arfian.demo.models.response.ApiResponse;
import com.arfian.demo.services.MovieService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@RestController
@RequestMapping(value = "/Movies")
public class MovieController {
    private static final Logger logger = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    private MovieService movieService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Movie>>> getAllMovie() {
        ApiResponse<List<Movie>> getListAll = movieService.getListAllMovie();
        return ResponseEntity.ok(getListAll);
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<ApiResponse<Movie>> getMovieById(@PathVariable(value = "id") long id) {
        ApiResponse<Movie> getMovieById = movieService.getMovieById(id);
        return ResponseEntity.ok(getMovieById);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Movie>> addMovie(@Valid @RequestBody Movie movie, Errors errors) {
        if(movie.getId() != null){
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, "ID must be blank", null));
        }
        if(errors.hasErrors()){
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, errors.getFieldError().getDefaultMessage(), null));
        }

        Movie submitMovie = movieService.addMovie(movie);
        if (submitMovie == null) {
            return ResponseEntity.ok(new ApiResponse<>(400, "", null));
        }
        return ResponseEntity.ok().body(new ApiResponse<>(200, "Success", submitMovie));
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<ApiResponse<Movie>> updateMovie(@Valid @RequestBody Movie movie, @PathVariable(value = "id") long id,  Errors errors) {
        if(movie.getId() != null){
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>(400, "ID must be blank", null
                    )
            );
        }

        if(errors.hasErrors()){
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>(400, errors.getFieldError().getDefaultMessage(), null
                    )
            );
        }

        movie.setId(id);
        Movie submitMovie = movieService.addMovie(movie);
        if (submitMovie == null) {
            return ResponseEntity.ok(new ApiResponse<>(400, "", null));
        }
        return ResponseEntity.ok().body(new ApiResponse<>(200, "Success update Movie", submitMovie));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ApiResponse> deleteMovie(@PathVariable(value = "id") long id, MethodArgumentTypeMismatchException ex) {
        try {
            movieService.deleteMovie(id);
            return ResponseEntity.ok().body(new ApiResponse<>(200, "Success delete Movie", null));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>(400, "", null
                    )
            );
        }

    }
}
