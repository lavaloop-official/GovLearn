package com.unimuenster.govlearnapi.webCrawler.service;

import com.unimuenster.govlearnapi.user.entity.UserEntity;

public abstract class WebCrawler {
    public abstract void crawl(UserEntity user);
}
