package br.com.rhfactor.nasaneoapi.dtos;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class NearObject {

    @JsonProperty("neo_reference_id")
    Long neoReferenceId;

    @JsonProperty("name")
    String name;

    @JsonProperty("close_approach_data")
    List<CloseApproachData> approachList = new ArrayList<>();


}
