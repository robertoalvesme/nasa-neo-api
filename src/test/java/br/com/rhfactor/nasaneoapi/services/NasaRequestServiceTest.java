package br.com.rhfactor.nasaneoapi.services;

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
    void testRequest(){

        ResponseEntity<String> response = service.getPotentiallyHazardousAsteroid("2021-03-07", "2021-03-07");
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

    }

}
