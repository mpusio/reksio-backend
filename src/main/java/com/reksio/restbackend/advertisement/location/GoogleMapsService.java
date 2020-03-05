package com.reksio.restbackend.advertisement.location;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class GoogleMapsService {

    @Value("${google-maps-api-key}")
    private String apiKey;

    private Logger log = LoggerFactory.getLogger(GoogleMapsService.class);

    public Optional<LatLng> recognizeLatLngParams(String city, String postcode)  {
        Optional<GeocodingResult[]> results = Optional.empty();

        try {
            GeoApiContext context = new GeoApiContext.Builder()
                    .apiKey("AIzaSyA1XySlsfAIMm9U-0Ry-BfZgALWb-ILf64")
                    .build();

            results = Optional.ofNullable(GeocodingApi
                    .geocode(context, postcode + " " + city)
                    .await());

        } catch (ApiException | InterruptedException | IOException e) {
            log.error("Google maps api failed.", e);
        }

        return results.map(geocodingResults -> geocodingResults[0].geometry.location);
    }
}
