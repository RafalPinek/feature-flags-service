package org.rafal.featureflags.service.feature;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class FeatureRepositoryHashMap implements FeatureRepository {

    private Map<String, Boolean> features;

    public FeatureRepositoryHashMap() {
        features = new HashMap<>();
    }

    @Override
    public Feature addFeature(String feature) {
        features.putIfAbsent(feature, false);
        return new Feature(feature, features.get(feature));
    }

    @Override
    public Feature updateFeature(String feature, boolean enabled) {
        features.put(feature, enabled);
        return new Feature(feature, features.get(feature));
    }

    @Override
    public boolean featureExists(String feature) {
        return features.containsKey(feature);
    }

    @Override
    public Set<String> getEnabledFeatures() {
        return features.entrySet().stream()
                .filter(e -> e.getValue().equals(true))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }


}
