package br.com.rhfactor.nasaneoapi.services;

import br.com.rhfactor.nasaneoapi.domains.Credential;
import br.com.rhfactor.nasaneoapi.dtos.SignupForm;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface CredentialService {

    @Transactional
    Credential create(@NotNull @Valid SignupForm signupForm);

    boolean existsByUsername(String username);
}
