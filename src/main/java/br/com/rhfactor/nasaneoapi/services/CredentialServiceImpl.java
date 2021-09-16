package br.com.rhfactor.nasaneoapi.services;

import br.com.rhfactor.nasaneoapi.domains.Credential;
import br.com.rhfactor.nasaneoapi.dtos.SignupForm;
import br.com.rhfactor.nasaneoapi.repositories.CredentialRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Log4j2
@Service
@Validated
public class CredentialServiceImpl implements CredentialService {

    @Autowired
    private CredentialRepository credentialRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Credential create(@NotNull @Valid SignupForm signupForm) {

        if( existsByUsername( signupForm.getUsername() ) ){
            throw new IllegalArgumentException("Duplicated credential");
        }

        return credentialRepository.save( Credential.builder()
                        .username( signupForm.getUsername() )
                        .password( passwordEncoder.encode( signupForm.getPassword() ) )
                .build() );
    }

    @Override
    public boolean existsByUsername( String username ){
        return credentialRepository.existsByUsername( username );
    }


}
