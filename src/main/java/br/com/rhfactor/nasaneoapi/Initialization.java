package br.com.rhfactor.nasaneoapi;

import br.com.rhfactor.nasaneoapi.dtos.SignupForm;
import br.com.rhfactor.nasaneoapi.services.CredentialService;

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
