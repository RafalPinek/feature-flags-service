package org.rafal.featureflags.service.feature;

import lombok.ToString;
import org.rafal.featureflags.api.FeatureDto;

import java.util.Objects;

@ToString
public class Feature {

    private final String name;

    private boolean globallyEnabled;

    public Feature(String name, boolean globallyEnabled) {
        this.name = name;
        this.globallyEnabled = globallyEnabled;
    }

    public void enable() {
        globallyEnabled = true;
    }

    public void disable() {
        globallyEnabled = false;
    }

    public boolean isGloballyEnabled() {
        return globallyEnabled;
    }

    public String getName() {
        return name;
    }

    public FeatureDto toDto() {
        return new FeatureDto(name, globallyEnabled);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Feature)) return false;
        Feature feature = (Feature) o;
        return getName().equals(feature.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
