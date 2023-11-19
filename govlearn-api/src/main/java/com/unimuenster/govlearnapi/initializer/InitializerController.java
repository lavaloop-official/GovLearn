package com.unimuenster.govlearnapi.initializer;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/initialization")
@Slf4j
public class InitializerController {

    private final InitializerService initializerService;

    @Operation(
            description = "Post once to initialize the application database."
    )
    @PostMapping()
    public ResponseEntity createCourse(){
        initializerService.init();

        return ResponseEntity.ok().build();
    }
}
