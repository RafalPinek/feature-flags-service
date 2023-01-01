package org.rafal.featureflags.service.feature;

import org.rafal.featureflags.api.FeatureDto;
import org.rafal.featureflags.api.FeatureSwitcherDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class FeatureController {

    private final FeatureRepository featureRepository;

    public FeatureController(FeatureRepository featureRepository) {
        this.featureRepository = featureRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/features")
    public ResponseEntity<FeatureDto> createFeature(@RequestBody String name) {
        if (featureRepository.featureExists(name)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Feature feature = featureRepository.addFeature(name);
        return new ResponseEntity<>(feature.toDto(), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/features/{name}")
    public ResponseEntity<FeatureDto> updateFeature(@PathVariable String name,
                                                    @RequestBody FeatureSwitcherDto switcher) {

        Feature feature = featureRepository.updateFeature(name, switcher.enabled());
        return new ResponseEntity<>(feature.toDto(), HttpStatus.OK);
    }
}
