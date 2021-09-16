package br.com.rhfactor.nasaneoapi;

import br.com.rhfactor.nasaneoapi.dtos.SignupForm;
import br.com.rhfactor.nasaneoapi.repositories.CredentialRepository;
import br.com.rhfactor.nasaneoapi.services.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

//@Component
public class Initialization {

//    @Autowired
    private CredentialService credentialService;

//    @PostConstruct
    public void createAdminUser(){
        if( !credentialService.existsByUsername( "admin@test.com.br" ) ){
            credentialService.create( SignupForm.builder()
                    .username("admin@test.com.br")
                    .password("senha1234")
                    .passwordConfirmation("senha1234").build() );
        }
    }

}
