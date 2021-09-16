package br.com.rhfactor.nasaneoapi.repositories;

import br.com.rhfactor.nasaneoapi.domains.Credential;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataJpaTest
//@H2DatabaseTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class CredentialRepositoryTest {

    @Autowired private TestEntityManager entityManager;
    @Autowired private CredentialRepository credentialRepository;

    @Test
    @Rollback
    @DisplayName("test insert role")
    void findAllByOrderByUsernameAsc(){

        List<String> usenames = new ArrayList<>(){{
            add( "roberto" );
            add( "amanha" );
            add( "cao" );
            add( "bola" );
            add( "zebra" );
        }};

        usenames.forEach(i->{
            Credential credential = this.credentialRepository.save( Credential.builder()
                    .username(i)
                    .password("senha1234")
                    .build() );
            assertThat(credential, hasProperty("id", greaterThanOrEqualTo(1L)));
        });

        List<Credential> credentialsFromDatabase = this.credentialRepository.findAll(Sort.by("username"));
        assertThat( credentialsFromDatabase
                .stream()
                .map( it-> it.getUsername() )
                .collect(Collectors.toList()) , contains( "amanha" , "bola" , "cao" , "roberto", "zebra" ) );

    }


}
