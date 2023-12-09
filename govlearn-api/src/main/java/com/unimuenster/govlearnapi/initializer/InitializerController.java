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
    private final RealInitializerService realInitializerService;

    @Operation(
            description = "Post once to initialize the application database."
    )
    @PostMapping("/instatiate")
    public ResponseEntity instatiate(){
        initializerService.init();

        return ResponseEntity.ok().build();
    }

    @Operation(
            description = "Post once to initialize a mock application database."
    )
    @PostMapping("/instatiate-realistic")
    public ResponseEntity instatiateRealisitc(){
        realInitializerService.init();

        return ResponseEntity.ok().build();
    }

    @Operation(
        description = """
            ## WARNING - WHEN EXECUTING ALL DATA WILL BE DELETED

            Post once to kill the application database.

            -> When using Instantiate afterwards -> Restart the application!
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
