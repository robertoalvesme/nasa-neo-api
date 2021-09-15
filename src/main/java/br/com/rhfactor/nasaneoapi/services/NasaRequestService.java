package br.com.rhfactor.nasaneoapi.services;

import org.springframework.http.ResponseEntity;

public interface NasaRequestService {

    ResponseEntity<String> getPotentiallyHazardousAsteroid(String startDate, String endDate);

}
