package br.com.rhfactor.nasaneoapi.dtos;

import org.hamcrest.collection.IsMapContaining;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testar se o método hasPotentiallyHazardousAsteroidCloseToEarth
 * estará funcionando sempre.
 *
 * - Verificar quando não existe itens
 * - Verifiar quando existe item
 *
 */
class NasaResponseTest {

    @Test
    void hasPotentiallyHazardousAsteroidCloseToEarth() {


        NasaResponse nasaResponse = NasaResponse.builder()
                .nearObjects(new HashMap<>(){{

                    // Com hazardous
                    put("1986-07-03", new ArrayList<>(){{
                        add( NearObject.builder()
                                .potentiallyHazardousAsteroid(true)
                                .approachList( new ArrayList<>(){{
                                    add( CloseApproachData.builder()
                                            .orbitingBody("Earth")
                                            .build() );
                                }} )
                                .build() );
                    }} );

                    // Sem hazardous no parametro potential
                    put("1986-07-04", new ArrayList<>(){{
                        add( NearObject.builder()
                                .potentiallyHazardousAsteroid(false)
                                .approachList( new ArrayList<>(){{
                                    add( CloseApproachData.builder()
                                            .orbitingBody("Earth")
                                            .build() );
                                }} )
                                .build() );
                    }} );

                    // Sem hazardous no parametro terra
                    put("1986-07-05", new ArrayList<>(){{
                        add( NearObject.builder()
                                .potentiallyHazardousAsteroid(true)
                                .approachList( new ArrayList<>(){{
                                    add( CloseApproachData.builder()
                                            .orbitingBody("Mars")
                                            .build() );
                                }} )
                                .build() );
                    }} );


                }})
                .build();

        assertTrue( nasaResponse.hasPotentiallyHazardousAsteroidCloseToEarth() );
        assertThat( nasaResponse.getPotentiallyHazardousAsteroidCloseToEarth().size() , equalTo(1));
        assertThat( nasaResponse.getPotentiallyHazardousAsteroidCloseToEarth() , IsMapContaining.hasKey("1986-07-03") );

    }
}
