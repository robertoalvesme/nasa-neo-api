package br.com.rhfactor.nasaneoapi.services;

import br.com.rhfactor.nasaneoapi.dtos.JwtResponse;
import br.com.rhfactor.nasaneoapi.dtos.LoginForm;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface LoginService extends UserDetailsService {

    ResponseEntity<JwtResponse> authenticate(LoginForm loginRequest) throws Exception;

    ResponseEntity<JwtResponse> refreshToken(DefaultClaims claims);

}
