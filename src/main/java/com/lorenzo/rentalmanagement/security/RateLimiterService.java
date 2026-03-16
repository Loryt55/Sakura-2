package com.lorenzo.rentalmanagement.security;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimiterService {

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    public Bucket resolveBucket(String ipAddress) {
        return buckets.computeIfAbsent(ipAddress, this::createNewBucket);
    }

    private Bucket createNewBucket(String ipAddress) {
        Bandwidth limit = Bandwidth.builder()
                .capacity(5)
                .refillIntervally(1, Duration.ofSeconds(30))
                .build();

        return Bucket.builder()
                .addLimit(limit)
                .build();
    }

    public boolean tryConsume(String ipAddress) {
        return resolveBucket(ipAddress).tryConsume(1);
    }
}