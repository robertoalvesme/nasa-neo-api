package br.com.rhfactor.nasaneoapi.services;

import br.com.rhfactor.nasaneoapi.dtos.NasaResponse;
import br.com.rhfactor.nasaneoapi.dtos.PotentiallyHazardousAsteroid;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Validated
public class NasaRequestServiceImpl implements NasaRequestService {

    private final String PATH = "/feed";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${nasa.api.key}")
    private String key;

    @Override
    public List<PotentiallyHazardousAsteroid> getListOfPotentiallyHazardousAsteroid(@NotNull LocalDate selectedDate) throws JsonProcessingException {

        String strSelectedDate = getDateAsString( selectedDate );
        ResponseEntity<NasaResponse> call = getPotentiallyHazardousAsteroid(strSelectedDate, strSelectedDate);
        log.info("Request result {} , {} ", call.getStatusCode() , objectMapper.writeValueAsString(call.getBody()) );


        NasaResponse response = call.getBody();
        // Não chamar o método da classe porque logo abaixo iremos executar ele novamente
        if( response.getNearObjects() == null || response.getNearObjects().size() <= 0 ){
            throw new RuntimeException("not found");
        }

        // Transformar na lista de data, id, nome, etc..
        List<PotentiallyHazardousAsteroid> potentialList = new ArrayList<>();

        response.getPotentiallyHazardousAsteroidCloseToEarth().entrySet().forEach( date -> {
                date.getValue().forEach( nearObject -> {
                         nearObject.getApproachList().forEach( relativeVelocity -> {
                             potentialList.add( PotentiallyHazardousAsteroid.builder()
                                             .date( getDateFromString( date.getKey() ) )
                                             .id( nearObject.getNeoReferenceId() )
                                             .kmPerHour( relativeVelocity.getRelativeVelocityKmPerHour() )
                                             .name( nearObject.getName() )
                                     .build() );
                         });
                     });
        });

        if( potentialList.size() == 0 ) throw new RuntimeException("not found");

        return potentialList;
    }

    private String getDateAsString(LocalDate selectedDate) {
        return selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    private LocalDate getDateFromString(String date){
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }


    /**
     * Receberemos o dia inicial e o dia final.
     * Quando não passamos os dados dos dias eles retornam 7 dias por página
     * Se passarmos o mesmo dia nas duas datas a resposta é mais rápida
     * E temos apenas uma resposta.
     *  @param startDate
     * @param endDate
     * @return
     */
    @Override
    public ResponseEntity<NasaResponse> getPotentiallyHazardousAsteroid(String startDate, String endDate){

        // https://api.nasa.gov/neo/rest/v1/feed?start_date=2021-01-01&api_key=IoXbCTZeb4J8ZMAIt5NQKBiBF7jJc4PcvO8HCF63
        return this.restTemplate.exchange( PATH.concat( "?start_date={startDate}&end_date={endDate}&api_key={key}" )
                        , HttpMethod.GET
                        , null
                        , NasaResponse.class
                        , startDate
                        , endDate
                        , key);

    }


}
