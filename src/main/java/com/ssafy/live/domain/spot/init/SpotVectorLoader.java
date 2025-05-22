package com.ssafy.live.domain.spot.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ssafy.live.domain.spot.service.SpotVectorService;

import lombok.RequiredArgsConstructor;

// @Component
@RequiredArgsConstructor
public class SpotVectorLoader implements CommandLineRunner {

    private final SpotVectorService spotVectorService;

    @Override
    public void run(String... args) throws Exception {
        spotVectorService.storeAllToVector();
    }
}
