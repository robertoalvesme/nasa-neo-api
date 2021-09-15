package br.com.rhfactor.nasaneoapi.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NasaResponse {


    /**
     * "element_count": 6
     */
    @JsonProperty("element_count")
    Integer elements;

    /**
     * A data é o Key
     * O valor são as informações recebidas
     */
    @JsonProperty("near_earth_objects")
    HashMap<String,List<NearObject>> nearObjects;


}
