package org.rafal.featureflags.api;

import java.util.Set;

public record FeaturesDto(Set<String> enabledFeatures) {

}
