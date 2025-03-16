package com.anand.movie_info_service.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.anand.movie_info_service.models.Movie;
import com.anand.movie_info_service.models.MovieSummary;

@RestController
@RequestMapping("/movies")
public class MovieResource {
	@Value("${api.key}")
	private String apiKey;
	@Autowired
	private RestTemplate restTemplate;
	@RequestMapping("/{movieId}")
	public Movie getMovieInfo(@PathVariable("movieId") String movieId) {
	    System.out.println("Fetching movie data for ID: " + movieId);  // Debugging log

	    MovieSummary movieSummary = restTemplate.getForObject(
	        "https://api.themoviedb.org/3/movie/" + movieId + "?api_key=" + apiKey, 
	        MovieSummary.class
	    );

	    System.out.println("Received Data: " + movieSummary);  // Check if data is received

	    return new Movie(movieId, movieSummary.getTitle(), movieSummary.getOverview());
	}



}
