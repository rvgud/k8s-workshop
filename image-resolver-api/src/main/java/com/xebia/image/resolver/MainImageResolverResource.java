package com.xebia.image.resolver;

import imageresolver.MainImageResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
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

    private Logger logger = LoggerFactory.getLogger(MainImageResolverResource.class);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Value("${image.cache.enabled:false}")
    private boolean imageCacheEnabled;

    @GetMapping
    public ResponseEntity<Map<String, String>> extractMainImage(@RequestParam("url") String url) {
        if (imageCacheEnabled) {
            String cached = redisTemplate.opsForValue().get(url);
            if (cached != null) {
                logger.info("Serving from cache...");
                Map<String, String> response = new HashMap<>();
                response.put("url", url);
                response.put("mainImage", cached);
                response.put("cache", "true");
                return ResponseEntity.ok(response);
            }
        }

        logger.info("Fetching fresh image...");
        Optional<String> mainImageOptional = MainImageResolver.resolveMainImage(url);
        if (!mainImageOptional.isPresent()) {
            return ResponseEntity.noContent().build();
        }
        String mainImage = mainImageOptional.get();

        if(imageCacheEnabled){
            redisTemplate.opsForValue().set(url, mainImage);
        }
        Map<String, String> response = new HashMap<>();
        response.put("url", url);
        response.put("mainImage", mainImage);
        return ResponseEntity.ok(response);
    }

}
