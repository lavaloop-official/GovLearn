package com.unimuenster.govlearnapi.webCrawler.service;

import com.unimuenster.govlearnapi.AbstractIntegrationTest;
import com.unimuenster.govlearnapi.initializer.InitializerService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class FindigShWebCrawlerTest extends AbstractIntegrationTest {

    @Autowired
    private FindigShWebCrawler findigShWebCrawler;
    @Autowired
    private InitializerService initializerService;

    @Disabled
    @Test
    void crawl() {
        findigShWebCrawler.crawl(initializerService.getUser1());
    }
}