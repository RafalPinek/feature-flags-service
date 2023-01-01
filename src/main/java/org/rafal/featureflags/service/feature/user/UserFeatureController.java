package org.rafal.featureflags.service.feature.user;

import org.rafal.featureflags.api.FeatureDto;
import org.rafal.featureflags.api.FeatureSwitcherDto;
import org.rafal.featureflags.api.FeaturesDto;
import org.rafal.featureflags.service.feature.FeatureRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;
import java.util.Set;

@RestController
public class UserFeatureController {

    private final FeatureRepository featureRepository;

    private final UserFeatureRepository userFeatureRepository;

    public UserFeatureController(UserFeatureRepository userFeatureRepository, FeatureRepository featureRepository) {
        this.userFeatureRepository = userFeatureRepository;
        this.featureRepository = featureRepository;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/users/{username}/features")
    public ResponseEntity<FeaturesDto> getFeaturesForUser(@PathVariable String username,
                                                          @ApiIgnore Principal principal) {
        if (!username.equals(principal.getName())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Set<String> features = userFeatureRepository.getAllFeaturesForUser(username);
        return new ResponseEntity<>(new FeaturesDto(features), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/users/{username}/features/{feature}")
    public ResponseEntity<FeatureDto> updateFeatureForUser(@PathVariable String username,
                                                           @PathVariable String feature,
                                                           @RequestBody FeatureSwitcherDto switcher) {
        if (!featureRepository.featureExists(feature)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (switcher.enabled()) {
            userFeatureRepository.enableFeatureForUser(feature, username);
        } else {
            userFeatureRepository.disableFeatureForUser(feature, username);
        }
        return new ResponseEntity<>(new FeatureDto(feature, switcher.enabled()), HttpStatus.OK);
    }
}
