package br.com.rhfactor.nasaneoapi.services;

import br.com.rhfactor.nasaneoapi.dtos.NasaResponse;
import br.com.rhfactor.nasaneoapi.dtos.PotentiallyHazardousAsteroid;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public interface NasaRequestService {

    List<PotentiallyHazardousAsteroid> getListOfPotentiallyHazardousAsteroid(@NotNull LocalDate selectedDate) throws JsonProcessingException;

    ResponseEntity<NasaResponse> getPotentiallyHazardousAsteroid(String startDate, String endDate);

}
