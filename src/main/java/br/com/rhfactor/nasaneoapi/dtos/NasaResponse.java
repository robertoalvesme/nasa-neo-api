package br.com.rhfactor.nasaneoapi.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class NasaResponse {

    /**
     * "links": {
     *     "next": "http://www.neowsapp.com/rest/v1/feed?start_date=1986-07-04&end_date=1986-07-04&detailed=false&api_key=IoXbCTZeb4J8ZMAIt5NQKBiBF7jJc4PcvO8HCF63",
     *     "prev": "http://www.neowsapp.com/rest/v1/feed?start_date=1986-07-02&end_date=1986-07-02&detailed=false&api_key=IoXbCTZeb4J8ZMAIt5NQKBiBF7jJc4PcvO8HCF63",
     *     "self": "http://www.neowsapp.com/rest/v1/feed?start_date=1986-07-03&end_date=1986-07-03&detailed=false&api_key=IoXbCTZeb4J8ZMAIt5NQKBiBF7jJc4PcvO8HCF63"
     *  }
     */
//    @JsonProperty("links")
//    List<HashMap<String,String>> links;

    /**
     * "element_count": 6
     */
    @JsonProperty("element_count")
    Integer elements;

    /**
     * A data é o Key
     * O valor são as informações recebidas
     */
//    @JsonProperty("near_earth_objects")
//    List<HashMap<String,List<NearObject>>> nearObjects;


}
