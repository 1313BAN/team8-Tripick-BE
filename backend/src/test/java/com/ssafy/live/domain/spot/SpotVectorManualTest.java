package com.ssafy.live.domain.spot;

import com.ssafy.live.domain.spot.service.SpotVectorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SpotVectorManualTest {

    @Autowired
    private SpotVectorService spotVectorService;

    @Test
    void manualVectorStoreInsert() {
        spotVectorService.storeAllToVector();
    }
}

