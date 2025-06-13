package com.ssafy.live.domain.spot.controller;

import com.ssafy.live.domain.spot.service.SpotVectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/vector")
@RequiredArgsConstructor
public class SpotVectorAdminController {

    private final SpotVectorService spotVectorService;

    @PostMapping("/init")
    public ResponseEntity<String> initVectorStore() {
        System.out.println("✅ [ADMIN API] /admin/vector/init 진입 확인"); // 로그 추가
        spotVectorService.storeAllToVector();
        return ResponseEntity.ok("✅ Vector store initialized successfully.");
    }
}
