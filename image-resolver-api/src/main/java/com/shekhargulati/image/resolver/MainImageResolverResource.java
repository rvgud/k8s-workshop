package com.shekhargulati.image.resolver;

import imageresolver.MainImageResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/images")
public class MainImageResolverResource {

    @GetMapping
    public ResponseEntity<Map<String, String>> extractMainImage(@RequestParam("url") String url) {
        Optional<String> mainImage = MainImageResolver.resolveMainImage(url);
        if (!mainImage.isPresent()) {
            return ResponseEntity.noContent().build();
        }
        Map<String, String> response = new HashMap<>();
        response.put("url", url);
        response.put("mainImage", mainImage.get());
        return ResponseEntity.ok(response);
    }

}
