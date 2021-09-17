package br.com.rhfactor.nasaneoapi.controllers;

import br.com.rhfactor.nasaneoapi.dtos.JwtResponse;
import br.com.rhfactor.nasaneoapi.dtos.LoginForm;
import br.com.rhfactor.nasaneoapi.dtos.SignupForm;
import br.com.rhfactor.nasaneoapi.services.CredentialService;
import br.com.rhfactor.nasaneoapi.services.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(AuthenticationController.PATH)
public class AuthenticationController {

    public static final String PATH = "/api/auth";

    @Autowired private CredentialService credentialService;
    @Autowired private LoginService loginService;

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginForm loginRequest) throws Exception {
        return loginService.authenticate( loginRequest );
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("signup")
    public void signUp( @NotNull @Valid @RequestBody SignupForm signupForm) {
        credentialService.create( signupForm );
    }



}
