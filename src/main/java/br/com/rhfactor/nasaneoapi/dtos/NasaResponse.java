package br.com.rhfactor.nasaneoapi.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
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
    Map<String,List<NearObject>> nearObjects;


    public boolean hasPotentiallyHazardousAsteroidCloseToEarth() {

        // Nem chamar o método se não existir entries
        if( this.nearObjects == null || this.nearObjects.size() <= 0 ){
            return false;
        }

        return  getPotentiallyHazardousAsteroidCloseToEarth().size() > 0;
    }

    /**
     * Filtrar somente os objetos que fazem parte dos requisitos
     * @return
     */
    public Map<String, List<NearObject>> getPotentiallyHazardousAsteroidCloseToEarth(){
        return this.nearObjects.entrySet().stream()
                .filter(dayList -> dayList != null && dayList.getValue().stream().allMatch(
                            nearObject -> nearObject.getPotentiallyHazardousAsteroid() &&
                                    nearObject.getApproachList() != null && nearObject.getApproachList().stream().allMatch(
                                    approach -> approach.isCloseToEarth()
                            )
                    )
                ).collect(Collectors.toMap(no -> no.getKey(), no -> no.getValue()));
    }
}
