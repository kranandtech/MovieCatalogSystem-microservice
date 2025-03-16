package com.anand.movie_catalog_service.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.anand.movie_catalog_service.models.CatalogItem;
import com.anand.movie_catalog_service.models.UserRating;
import com.anand.movie_catalog_service.services.MovieInfo;
import com.anand.movie_catalog_service.services.UserRatingInfo;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	WebClient.Builder webClientBuilder;

	@Autowired
	MovieInfo movieInfo;

	@Autowired
	UserRatingInfo userRatingInfo;

	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
	    System.out.println("Fetching user ratings for: " + userId);
	    
	    UserRating userRating = userRatingInfo.getUserRating(userId);
	    
	    System.out.println("Ratings received: " + userRating.getRatings());

	    return userRating.getRatings().stream()
	            .map(rating -> {
	                System.out.println("Fetching movie info for: " + rating.getMovieId());
	                return movieInfo.getCatalogItem(rating);
	            })
	            .collect(Collectors.toList());
	}


}

/*
 * Alternative WebClient way Movie movie =
 * webClientBuilder.build().get().uri("http://localhost:8082/movies/"+
 * rating.getMovieId()) .retrieve().bodyToMono(Movie.class).block();
 */