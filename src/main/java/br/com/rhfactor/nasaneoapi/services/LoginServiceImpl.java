package br.com.rhfactor.nasaneoapi.services;

import br.com.rhfactor.nasaneoapi.config.jwt.JwtUtils;
import br.com.rhfactor.nasaneoapi.domains.Credential;
import br.com.rhfactor.nasaneoapi.dtos.JwtResponse;
import br.com.rhfactor.nasaneoapi.dtos.LoginForm;
import br.com.rhfactor.nasaneoapi.repositories.CredentialRepository;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@Validated
public class LoginServiceImpl implements LoginService {

    @Autowired private CredentialRepository credentialRepository;
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JwtUtils jwtUtils;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return credentialRepository.findByUsername(username)
                .orElseThrow( ()->new UsernameNotFoundException("Invalid credentials"))
                ;
    }

    @Override
    public ResponseEntity<JwtResponse> authenticate(LoginForm loginRequest) throws Exception {
        Authentication authentication = null;

        try {
            authentication = authenticationManager.authenticate( new UsernamePasswordAuthenticationToken( loginRequest.getUsername(), loginRequest.getPassword() ));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            // Se deu invalid credential limpar o cache
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        Credential credential = (Credential) authentication.getPrincipal();

        return ResponseEntity.ok(JwtResponse.builder()
                .token( jwt )
                .username( credential.getUsername() )
//                .roles( credential.getRoles()
//                        .stream()
//                        .map(r->r.getName())
//                        .collect(Collectors.toList()) )
                .build());
    }

    @Override
    public ResponseEntity<JwtResponse> refreshToken(DefaultClaims claims) {
        Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);
        String token = jwtUtils.doGenerateRefreshToken(expectedMap, expectedMap.get("sub").toString());
        return ResponseEntity.ok(JwtResponse.builder()
                .token( token )
                .build());
    }

    public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
        Map<String, Object> expectedMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : claims.entrySet()) {
            expectedMap.put(entry.getKey(), entry.getValue());
        }
        return expectedMap;
    }
}
