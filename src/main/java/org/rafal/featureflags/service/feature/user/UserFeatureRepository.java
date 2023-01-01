package org.rafal.featureflags.service.feature.user;

import java.util.Set;

public interface UserFeatureRepository {

    void enableFeatureForUser(String feature, String username);

    void disableFeatureForUser(String feature, String username);

    Set<String> getAllFeaturesForUser(String username);

}
