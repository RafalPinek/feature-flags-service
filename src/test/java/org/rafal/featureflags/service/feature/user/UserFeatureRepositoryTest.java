package org.rafal.featureflags.service.feature.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rafal.featureflags.service.feature.FeatureRepository;
import org.rafal.featureflags.service.feature.FeatureRepositoryHashMap;

import java.util.Set;

public class UserFeatureRepositoryTest {

    private static final String USER = "user";
    private static final String FEATURE_1 = "f1";
    private static final String FEATURE_2 = "f2";
    private static final String FEATURE_3 = "f3";
    private static final String FEATURE_4 = "f4";

    private FeatureRepository featureRepository;

    private UserFeatureRepository userRepo;

    @BeforeEach
    public void setUp() {
        featureRepository = new FeatureRepositoryHashMap();
        prepareFeatures();
        userRepo = new UserFeatureRepositoryHashMap(featureRepository);
    }

    @Test
    public void shouldEnableFeatureForUser() {
        // given
        Assertions.assertThat(userRepo.getAllFeaturesForUser(USER)).doesNotContain(FEATURE_1);

        // when
        userRepo.enableFeatureForUser(FEATURE_1, USER);

        // then
        Assertions.assertThat(userRepo.getAllFeaturesForUser(USER)).contains(FEATURE_1);
    }

    @Test
    public void shouldDisableFeatureForUser() {
        // given
        Assertions.assertThat(userRepo.getAllFeaturesForUser(USER)).contains(FEATURE_2);

        // when
        userRepo.disableFeatureForUser(FEATURE_2, USER);

        // then
        Assertions.assertThat(userRepo.getAllFeaturesForUser(USER)).doesNotContain(FEATURE_2);
    }

    @Test
    public void shouldGetAllFeaturesForUser() {
        // given
        userRepo.enableFeatureForUser(FEATURE_1, USER);

        // when
        Set<String> enabledFeatures = userRepo.getAllFeaturesForUser(USER);

        // then
        Assertions.assertThat(enabledFeatures).containsExactlyInAnyOrder(FEATURE_1, FEATURE_2, FEATURE_3);
    }

    private void prepareFeatures() {
        featureRepository.addFeature(FEATURE_1);
        featureRepository.addFeature(FEATURE_2);
        featureRepository.addFeature(FEATURE_3);
        featureRepository.addFeature(FEATURE_4);
        featureRepository.updateFeature(FEATURE_2, true);
        featureRepository.updateFeature(FEATURE_3, true);
    }
}
