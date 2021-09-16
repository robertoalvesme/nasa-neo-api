package br.com.rhfactor.nasaneoapi.config.jwt;

import br.com.rhfactor.nasaneoapi.services.LoginService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Log4j2
public class AuthTokenFilter extends OncePerRequestFilter {

    public static final List<String> PUBLIC_ENDPOINT = new ArrayList<>(){{
    }};

    public static final List<String> PUBLIC_PATH = new ArrayList<>(){{
        add("/api/auth");
    }};

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private LoginService loginService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String jwt = parseJwt(request);

        // Verificar se o endpoint é liberado
        if( PUBLIC_ENDPOINT.stream().filter( k-> request.getRequestURI().equals( k ) ).count() > 0 ){
            filterChain.doFilter(request, response);
            return;
        }

        // Verificar se contém o path como publico
        if( PUBLIC_PATH.stream().filter( k-> request.getRequestURI().contains( k ) ).count() > 0 ){
            filterChain.doFilter(request, response);
            return;
        }

        if( jwt == null ){
            throw new ServletException("Sorry, you are not authorized");
        }

        try {

            if ( jwtUtils.validateJwtToken(jwt) ) {
                String username = jwtUtils.getUserNameFromJwtToken(jwt);

                UserDetails userDetails = loginService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                final Claims claims = Jwts.parser().setSigningKey( jwtUtils.getSecret() ).parseClaimsJws(jwt).getBody();
                request.setAttribute("claims", claims);

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (ExpiredJwtException ex) {

            String isRefreshToken = request.getHeader("isRefreshToken");
            String requestURL = request.getRequestURL().toString();

            // allow for Refresh Token creation if following conditions are true.
            if (isRefreshToken != null && isRefreshToken.equals("true") && requestURL.contains("refreshtoken")) {
                allowForRefreshToken(ex, request);
            } else {
                request.setAttribute("exception", ex);
            }

        } catch (BadCredentialsException ex) {
            request.setAttribute("exception", ex);
        } catch (Exception ex) {
            System.out.println(ex);
        }

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }

        return null;
    }

    private void allowForRefreshToken(ExpiredJwtException ex, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(null, null, null);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        request.setAttribute("claims", ex.getClaims());
    }

}
