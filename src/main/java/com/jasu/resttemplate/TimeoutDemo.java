package com.jasu.resttemplate;


import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TimeoutDemo {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>(10000);
        for (int i = 0; i < 10000; i++) {
            Object o = new Object();
            list.add("o");
        }
        System.out.println();

        for (int i = 0; i < 10000; i++) {
            String o = list.get(i);
            o = null;
            list.set(i, null);
        }

        System.out.println(list);


    }
}
