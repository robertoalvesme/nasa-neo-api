package br.com.rhfactor.nasaneoapi.services;

import br.com.rhfactor.nasaneoapi.dtos.NasaResponse;
import org.springframework.http.ResponseEntity;

public interface NasaRequestService {

    ResponseEntity<NasaResponse> getPotentiallyHazardousAsteroid(String startDate, String endDate);

}
