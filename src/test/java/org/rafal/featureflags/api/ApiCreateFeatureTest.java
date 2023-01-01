package org.rafal.featureflags.api;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;


public class ApiCreateFeatureTest extends BaseIntegrationTest {

    private static final String FEATURE_1 = "f1";

    @Test
    public void shouldCreateFeature() {
        // when
        ResponseEntity<FeatureDto> response = adminClient.createFeature(FEATURE_1);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void shouldNotCreateFeatureTwice() {
        // given
        adminClient.createFeature(FEATURE_1);

        // when
        ResponseEntity<FeatureDto> response = adminClient.createFeature(FEATURE_1);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    public void shouldAllowOnlyAdmin() {
        // when
        ResponseEntity<FeatureDto> response = userClient.createFeature(FEATURE_1);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }
}
