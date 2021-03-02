package com.jasu.resttemplate;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class IRestTemplate {

    @Bean("httpRestTemplate")
    public RestTemplate httpRestTemplate(){

        // requestConfig
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(30000)
                .setSocketTimeout(60000)
                .setConnectTimeout(5000).build();
        // clientConnectionManager
        PoolingHttpClientConnectionManager clientConnectionManager = new PoolingHttpClientConnectionManager();
        clientConnectionManager.setDefaultMaxPerRoute(50);
        clientConnectionManager.setMaxTotal(200);
        // closeableHttpClient
        CloseableHttpClient closeableHttpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(clientConnectionManager).build();
        // httpRequestFactory
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory(closeableHttpClient);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(httpRequestFactory);
        return restTemplate;
    }
}
