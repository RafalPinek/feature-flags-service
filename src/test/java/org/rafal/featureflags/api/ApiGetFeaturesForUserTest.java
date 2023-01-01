package org.rafal.featureflags.api;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class ApiGetFeaturesForUserTest extends BaseIntegrationTest {

    private static final String USER = "user";
    private static final String FEATURE_1 = "f1";
    private static final String FEATURE_2 = "f2";
    private static final String FEATURE_3 = "f3";
    private static final String FEATURE_4 = "f4";

    @Test
    public void shouldGetAllEnabledFeaturesPerUser() {
        // given
        adminClient.createFeature(FEATURE_1);
        adminClient.createFeature(FEATURE_2);
        adminClient.createFeature(FEATURE_3);
        adminClient.createFeature(FEATURE_4);
        adminClient.updateFeature(FEATURE_1, true);
        adminClient.updateFeature(FEATURE_2, true);
        adminClient.updateFeatureForUser(USER, FEATURE_3, true);
        adminClient.updateFeatureForUser(USER, FEATURE_1, false);

        // when
        ResponseEntity<FeaturesDto> response = userClient.getFeaturesForUser(USER);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().enabledFeatures()).containsExactly(FEATURE_2, FEATURE_3);
    }

    @Test
    public void shouldNotAllowGetOtherUsersFeature() {
        // when
        ResponseEntity<FeaturesDto> response = userClient.getFeaturesForUser("non-existing-user");

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }
}
