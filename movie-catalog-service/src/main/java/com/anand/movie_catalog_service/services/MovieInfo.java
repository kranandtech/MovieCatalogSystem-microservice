package com.anand.movie_catalog_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.anand.movie_catalog_service.models.CatalogItem;
import com.anand.movie_catalog_service.models.Movie;
import com.anand.movie_catalog_service.models.Rating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class MovieInfo {
    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(
    	    fallbackMethod = "getFallbackCatalogItem",
    	    threadPoolKey = "movieInfoPool",
    	    threadPoolProperties = {
    	        @HystrixProperty(name = "coreSize", value = "20"),
    	        @HystrixProperty(name = "maxQueueSize", value = "10")
    	    }
    	)
    public CatalogItem getCatalogItem(Rating rating) {
        String url = "http://movie-info-service/movies/" + rating.getMovieId();
        System.out.println("Calling URL: " + url);

        Movie movie = restTemplate.getForObject(url, Movie.class);
        System.out.println("Received movie: " + movie.getTitle());

        return new CatalogItem(movie.getTitle(), movie.getOverview(), rating.getRating());
    }


    public CatalogItem getFallbackCatalogItem(Rating rating) {
        System.out.println("Fallback triggered for movie ID: " + rating.getMovieId());
        return new CatalogItem("Movie name not found", "", rating.getRating());
    }
}
