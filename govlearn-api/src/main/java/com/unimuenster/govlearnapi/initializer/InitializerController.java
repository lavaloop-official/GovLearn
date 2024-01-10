package com.unimuenster.govlearnapi.initializer;

import com.unimuenster.govlearnapi.tags.entity.Tag;
import com.unimuenster.govlearnapi.tags.repository.TagRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/initialization")
@Slf4j
public class InitializerController {

    private final InitializerService initializerService;
    private final RealInitializerService realInitializerService;
    private final TagRepository tagRepository;

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

    class Role {
        String name;
        String[] tags;

        public String toString() {
            return "\nRole: " + name + " Tags: " + Arrays.toString(tags);
        }
    }
}
