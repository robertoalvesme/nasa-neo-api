package br.com.rhfactor.nasaneoapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Potentially Hazardous Asteroid not found")
public class PotentiallyHazardousAsteroidNotFoundException extends RuntimeException {
}
