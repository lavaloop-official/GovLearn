package com.unimuenster.govlearnapi.core.controller;

import com.unimuenster.govlearnapi.category.controller.wsto.CategoryWsTo;
import com.unimuenster.govlearnapi.category.service.dto.CategoryDTO;
import com.unimuenster.govlearnapi.common.responsewrapper.Message;
import com.unimuenster.govlearnapi.common.responsewrapper.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@Slf4j
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity testHealth() {

        log.info("Health check successful.");

        return ResponseEntity.ok().build();
    }
}
