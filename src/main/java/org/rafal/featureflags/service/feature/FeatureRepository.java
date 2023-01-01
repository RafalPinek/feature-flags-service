package org.rafal.featureflags.service.feature;

import java.util.Set;


public interface FeatureRepository {

    Feature addFeature(String feature);

    Feature updateFeature(String feature, boolean enabled);

    boolean featureExists(String feature);

    Set<String> getEnabledFeatures();

}
