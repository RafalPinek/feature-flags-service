package org.rafal.featureflags.service.feature;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class FeatureRepositoryTest {

    private static final String FEATURE_1 = "f1";
    private static final String FEATURE_2 = "f2";
    private static final String FEATURE_3 = "f3";
    private static final String FEATURE_4 = "f4";

    private FeatureRepository repo;

    @BeforeEach
    public void setUp() {
        repo = new FeatureRepositoryHashMap();
    }

    @Test
    public void shouldAddFeature() {
        // given
        assertThat(repo.featureExists(FEATURE_1)).isFalse();

        // when
        repo.addFeature(FEATURE_1);

        // then
        assertThat(repo.featureExists(FEATURE_1)).isTrue();
    }

    @Test
    public void shouldUpdateFeature() {
        // given
        repo.addFeature(FEATURE_1);
        assertThat(repo.getEnabledFeatures()).doesNotContain(FEATURE_1);

        // when
        repo.updateFeature(FEATURE_1, true);

        // then
        assertThat(repo.getEnabledFeatures()).contains(FEATURE_1);
    }

    @Test
    public void shouldCheckIfFeatureExists() {
        // when and then
        assertThat(repo.featureExists(FEATURE_1)).isFalse();
        repo.addFeature(FEATURE_1);
        assertThat(repo.featureExists(FEATURE_1)).isTrue();
    }

    @Test
    public void shouldGetEnabledFeatures() {
        // given
        repo.addFeature(FEATURE_1);
        repo.addFeature(FEATURE_2);
        repo.addFeature(FEATURE_3);
        repo.addFeature(FEATURE_4);
        repo.updateFeature(FEATURE_2, true);
        repo.updateFeature(FEATURE_3, true);

        // when
        Set<String> enabledFeatures = repo.getEnabledFeatures();

        // then
        assertThat(enabledFeatures).containsExactlyInAnyOrder(FEATURE_2, FEATURE_3);
    }

}
