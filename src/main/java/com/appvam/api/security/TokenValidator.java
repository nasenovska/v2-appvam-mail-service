package com.appvam.api.security;

import com.appvam.api.payload.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Configuration
public class TokenValidator {

    @Value(value = "${token.validation-path}")
    private String url;

    private RestTemplate restTemplate;

    public MessageResponse validate(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        ResponseEntity<MessageResponse> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<MessageResponse>() {
                }
        );

        return responseEntity.getBody();
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
