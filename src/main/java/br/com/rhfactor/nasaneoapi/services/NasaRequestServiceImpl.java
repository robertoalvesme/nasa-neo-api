package br.com.rhfactor.nasaneoapi.services;

import br.com.rhfactor.nasaneoapi.dtos.NasaResponse;
import br.com.rhfactor.nasaneoapi.dtos.PotentiallyHazardousAsteroid;
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
import java.util.List;

@Service
@Validated
public class NasaRequestServiceImpl implements NasaRequestService {

    private final String PATH = "/feed";

    @Autowired
    private RestTemplate restTemplate;

    @Value("${nasa.api.key}")
    private String key;

    @Override
    public List<PotentiallyHazardousAsteroid> getListOfPotentiallyHazardousAsteroid(@NotNull LocalDate selectedDate) {

        String strSelectedDate = getConvertedDate( selectedDate );
        NasaResponse response = getPotentiallyHazardousAsteroid(strSelectedDate, strSelectedDate).getBody();

        if( response.hasPotentiallyHazardousAsteroidCloseToEarth() ){
            throw new RuntimeException("not found");
        }




        return null;
    }

    private String getConvertedDate(LocalDate selectedDate) {
        return selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
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
