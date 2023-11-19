package com.unimuenster.govlearnapi.webCrawler.service;

import com.unimuenster.govlearnapi.webCrawler.controller.wsto.CrawlerWsTo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebCrawlerStrategySelector {

    public WebCrawler getWebCrawler(CrawlerWsTo crawlerWsTo){

        if (crawlerWsTo.getCrawlerType().equals("findig.sh")){
            return new FindigShWebCrawler();
        }

        return null;
    }

}
