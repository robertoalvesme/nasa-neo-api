package br.com.rhfactor.nasaneoapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.client.RestTemplateBuilderConfigurer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class NasaNeoApiApplication {

    @Value("${nasa.api.url}")
    private String nasaUrl;

    public static void main(String[] args) {
        SpringApplication.run(NasaNeoApiApplication.class, args);
    }

    @Bean
    public RestTemplate getDefaultRestTemplate(){
        return new RestTemplateBuilder()
                .rootUri( nasaUrl )
                .build();

    }

}
