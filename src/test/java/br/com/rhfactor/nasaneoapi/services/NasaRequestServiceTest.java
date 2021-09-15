package br.com.rhfactor.nasaneoapi.services;

import br.com.rhfactor.nasaneoapi.dtos.NasaResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
class NasaRequestServiceTest {

    @Autowired
    private NasaRequestService service;

    @Test
    void testRequest() {

        ResponseEntity<NasaResponse> response = service.getPotentiallyHazardousAsteroid("1986-07-03", "1986-07-03");
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

        assertThat(response.getBody(), allOf(
                hasProperty("elements", equalTo(6))
        ));


    }

}
