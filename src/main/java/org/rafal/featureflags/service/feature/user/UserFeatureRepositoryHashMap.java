package org.rafal.featureflags.service.feature.user;

import org.rafal.featureflags.service.feature.FeatureRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserFeatureRepositoryHashMap implements UserFeatureRepository {

    private FeatureRepository featureRepository;

    private Map<String, Map<Boolean, Set<String>>> userFeatures;

    public UserFeatureRepositoryHashMap(FeatureRepository featureRepository) {
        this.featureRepository = featureRepository;
        userFeatures = new HashMap<>();
    }

    @Override
    public void enableFeatureForUser(String feature, String username) {
        userFeatures.putIfAbsent(username, new HashMap<>());

        userFeatures.get(username).putIfAbsent(true, new HashSet<>());
        userFeatures.get(username).get(true).add(feature);

        userFeatures.get(username).putIfAbsent(false, new HashSet<>());
        userFeatures.get(username).get(false).remove(feature);
    }

    @Override
    public void disableFeatureForUser(String feature, String username) {
        userFeatures.putIfAbsent(username, new HashMap<>());

        userFeatures.get(username).putIfAbsent(false, new HashSet<>());
        userFeatures.get(username).get(false).add(feature);

        userFeatures.get(username).putIfAbsent(true, new HashSet<>());
        userFeatures.get(username).get(true).remove(feature);
    }

    @Override
    public Set<String> getAllFeaturesForUser(String username) {
        Set<String> featuresEnabledToUser = Optional.ofNullable(userFeatures.get(username))
                .map(userFeaturesMap -> userFeaturesMap.get(true))
                .orElse(Set.of());

        Set<String> featuresDisabledToUser = Optional.ofNullable(userFeatures.get(username))
                .map(userFeaturesMap -> userFeaturesMap.get(false))
                .orElse(Set.of());

        Set<String> globallyEnabledFeatures = featureRepository.getEnabledFeatures();

        Set<String> result = new HashSet<>(globallyEnabledFeatures);
        result.removeAll(featuresDisabledToUser);
        result.addAll(featuresEnabledToUser);

        return result;
    }

}
