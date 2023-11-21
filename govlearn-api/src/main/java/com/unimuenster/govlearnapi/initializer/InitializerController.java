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
    @PostMapping("/instatiate")
    public ResponseEntity createCourse(){
        initializerService.init();

        return ResponseEntity.ok().build();
    }

    @Operation(
        description = """
            ## WARNING - WHEN EXECUTING ALL DATA WILL BE DELETED

            Post once to kill the application database.
            """
    )
    @PostMapping("/dropAll")
    public ResponseEntity DropAllTables(){
        try {
            initializerService.dropAllTables();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}
