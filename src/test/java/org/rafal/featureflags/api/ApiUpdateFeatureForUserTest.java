package org.rafal.featureflags.api;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class ApiUpdateFeatureForUserTest extends BaseIntegrationTest {

    private static final String USER = "user";
    private static final String FEATURE_1 = "f1";
    private static final String FEATURE_2 = "f2";

    @Test
    public void shouldEnableFeatureForUser() {
        // given
        adminClient.createFeature(FEATURE_1);
        adminClient.createFeature(FEATURE_2);

        // when
        ResponseEntity<FeatureDto> response = adminClient.updateFeatureForUser(USER, FEATURE_1, true);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(userClient.getFeaturesForUser(USER).getBody().enabledFeatures()).containsExactly(FEATURE_1);
    }

    @Test
    public void shouldDisableFeature() {
        // given
        adminClient.createFeature(FEATURE_1);
        adminClient.createFeature(FEATURE_2);
        adminClient.updateFeature(FEATURE_1, true);
        assertThat(userClient.getFeaturesForUser(USER).getBody().enabledFeatures()).containsExactly(FEATURE_1);

        // when
        ResponseEntity<FeatureDto> response = adminClient.updateFeatureForUser(USER, FEATURE_1, false);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(userClient.getFeaturesForUser(USER).getBody().enabledFeatures()).isEmpty();
    }

    @Test
    public void shouldNotUpdateNonExistingFeature() {
        // when
        ResponseEntity<FeatureDto> response = adminClient.updateFeatureForUser(USER, FEATURE_1, true);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void shouldAllowOnlyAdmin() {
        // given
        adminClient.createFeature(FEATURE_1);

        // when
        ResponseEntity<FeatureDto> response = userClient.updateFeatureForUser(USER, FEATURE_1, true);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }
}
