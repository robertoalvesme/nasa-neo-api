package br.com.rhfactor.nasaneoapi.controllers;

import br.com.rhfactor.nasaneoapi.dtos.JwtResponse;
import br.com.rhfactor.nasaneoapi.dtos.LoginForm;
import br.com.rhfactor.nasaneoapi.dtos.SignupForm;
import br.com.rhfactor.nasaneoapi.services.CredentialService;
import br.com.rhfactor.nasaneoapi.services.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired private CredentialService credentialService;
    @Autowired private LoginService loginService;

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginForm loginRequest) throws Exception {
        return loginService.authenticate( loginRequest );
    }

    @PostMapping("signup")
    public Boolean signUp( @NotNull @Valid @RequestBody SignupForm signupForm) {
        try{
            credentialService.create( signupForm );
        }catch (Exception e){
            return false;
        }
        return true;
    }



}
