package br.com.rhfactor.nasaneoapi.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class PotentiallyHazardousAsteroid {

    Long id;
    LocalDate date;
    String name;
    Double kmPerHour;

}
