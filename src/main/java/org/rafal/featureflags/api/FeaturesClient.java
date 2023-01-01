package org.rafal.featureflags.api;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

public class FeaturesClient {

    private final String baseUrl;

    private final RestTemplate restTemplate;

    public FeaturesClient(String baseUrl, String username, String password) {
        this.baseUrl = baseUrl;
        this.restTemplate = new RestTemplateBuilder()
                .basicAuthentication(username, password)
                .build();
    }

    public ResponseEntity<FeatureDto> createFeature(String name) {
        try {
            return restTemplate.postForEntity(baseUrl + "/features", name,
                    FeatureDto.class);
        } catch (HttpStatusCodeException e) {
            return new ResponseEntity<>(null, e.getStatusCode());
        }
    }

    public ResponseEntity<FeatureDto> updateFeature(String name, boolean enabled) {
        try {
            return restTemplate.exchange(baseUrl + "/features/" + name,
                    HttpMethod.PUT,
                    new HttpEntity<>(new FeatureSwitcherDto(enabled)),
                    FeatureDto.class);
        } catch (HttpStatusCodeException e) {
            return new ResponseEntity<>(null, e.getStatusCode());
        }
    }

    public ResponseEntity<FeaturesDto> getFeaturesForUser(String username) {
        try {
            return restTemplate.getForEntity(baseUrl + "/users/" + username + "/features",
                    FeaturesDto.class);
        } catch (HttpStatusCodeException e) {
            return new ResponseEntity<>(null, e.getStatusCode());
        }
    }

    public ResponseEntity<FeatureDto> updateFeatureForUser(
            String username, String feature, boolean enabled) {
        try {
            return restTemplate.exchange(baseUrl + "/users/" + username + "/features/" + feature,
                    HttpMethod.PUT,
                    new HttpEntity<>(new FeatureSwitcherDto(enabled)),
                    FeatureDto.class);
        } catch (HttpStatusCodeException e) {
            return new ResponseEntity<>(null, e.getStatusCode());
        }
    }
}
