package com.flaginfo.lightning.common.config;

import org.apache.commons.httpclient.HttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;

@Configuration
public class HttpClientConfig {
    
    @Bean
    public HttpClient httpClient() {
        // HttpClient初始化
        MultiThreadedHttpConnectionManager manager = new MultiThreadedHttpConnectionManager();
        HttpConnectionManagerParams params = manager.getParams();
        params.setDefaultMaxConnectionsPerHost(100);
        params.setMaxTotalConnections(500);
        params.setConnectionTimeout(30000);
        params.setSoTimeout(600000);
        HttpClient httpClient = new HttpClient(manager);
        
        return httpClient;
    }
    
}
