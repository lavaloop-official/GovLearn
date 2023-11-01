package com.unimuenster.govlearnapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class BrokenTest extends AbstractIntegrationTest{

    @Test
    void contextLoads() {
        assertTrue(false);
    }
}
