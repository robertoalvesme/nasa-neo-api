package br.com.rhfactor.nasaneoapi.services;

import br.com.rhfactor.nasaneoapi.dtos.CloseApproachData;
import br.com.rhfactor.nasaneoapi.dtos.NasaResponse;
import br.com.rhfactor.nasaneoapi.dtos.NearObject;
import br.com.rhfactor.nasaneoapi.dtos.PotentiallyHazardousAsteroid;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NasaRequestServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private NasaRequestService service = new NasaRequestServiceImpl();

    @Test
    void getListOfPotentiallyHazardousAsteroid() {

        ResponseEntity<NasaResponse> responseEntity = new ResponseEntity<NasaResponse>(
                getNasaResponse(),
                null,
                HttpStatus.OK
        );

        when(restTemplate.exchange(eq("/feed?start_date={startDate}&end_date={endDate}&api_key={key}")
                , eq(HttpMethod.GET)
                , eq(null)
                , eq(NasaResponse.class)
                , any(String.class)
                , any(String.class)
                , eq(null)
        )).thenReturn(responseEntity);

        LocalDate selectedDate = LocalDate.of(1986, 07, 03);

        List<PotentiallyHazardousAsteroid> objectList = service.getListOfPotentiallyHazardousAsteroid(selectedDate);
        assertThat(objectList, hasSize(1));
        assertThat(objectList, everyItem(
                allOf(
                        hasProperty("id", equalTo(120L))
                        , hasProperty("date", equalTo(LocalDate.of(1986, 07, 03)))
                        , hasProperty("name", equalTo("Name 1"))
                        , hasProperty("kmPerHour", equalTo(940.00))
                )
        ));

    }

    private NasaResponse getNasaResponse() {
        return NasaResponse.builder()
                .nearObjects(new HashMap<>() {{

                    // Com hazardous
                    put("1986-07-03", new ArrayList<>() {{
                        add(NearObject.builder()
                                .neoReferenceId(120L)
                                .name("Name 1")
                                .potentiallyHazardousAsteroid(true)
                                .approachList(new ArrayList<>() {{
                                    add(CloseApproachData.builder()
                                            .orbitingBody("Earth")
                                            .relativeVelocity(new HashMap<>() {{
                                                put("kilometers_per_hour", 940.00);
                                            }})
                                            .build());
                                }})
                                .build());
                    }});

                    // Sem hazardous no parametro potential
                    put("1986-07-04", new ArrayList<>() {{
                        add(NearObject.builder()
                                .neoReferenceId(121L)
                                .name("Name 2")
                                .potentiallyHazardousAsteroid(false)
                                .approachList(new ArrayList<>() {{
                                    add(CloseApproachData.builder()
                                            .orbitingBody("Earth")
                                            .build());
                                }})
                                .build());
                    }});

                    // Sem hazardous no parametro terra
                    put("1986-07-05", new ArrayList<>() {{
                        add(NearObject.builder()
                                .neoReferenceId(122L)
                                .name("Name 3")
                                .potentiallyHazardousAsteroid(true)
                                .approachList(new ArrayList<>() {{
                                    add(CloseApproachData.builder()
                                            .orbitingBody("Mars")
                                            .build());
                                }})
                                .build());
                    }});


                }})
                .build();
    }
}
