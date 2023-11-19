package com.unimuenster.govlearnapi.webCrawler.controller;

import com.unimuenster.govlearnapi.user.entity.UserEntity;
import com.unimuenster.govlearnapi.user.service.AuthenticationService;
import com.unimuenster.govlearnapi.webCrawler.controller.wsto.CrawlerWsTo;
import com.unimuenster.govlearnapi.webCrawler.service.WebCrawler;
import com.unimuenster.govlearnapi.webCrawler.service.WebCrawlerStrategySelector;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/crawler")
@Slf4j
public class WebCrawlerController {

    private final WebCrawlerStrategySelector webCrawlerStrategySelector;
    private final AuthenticationService authenticationService;

    @Operation(
            description = "Post once to initialize the application database."
    )
    @PreAuthorize("hasAuthority('user')")
    @PostMapping()
    public ResponseEntity crawl(@RequestBody CrawlerWsTo crawlerWsTo){
        UserEntity currentUser = authenticationService.getCurrentUser();

        WebCrawler webCrawler = webCrawlerStrategySelector.getWebCrawler(crawlerWsTo);

        webCrawler.crawl(currentUser);

        return ResponseEntity.ok().build();
    }
}
