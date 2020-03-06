package com.jasu.dubbo.demo;

import org.apache.dubbo.config.annotation.Service;

/*****************************************
 * @author hjs
 * @date 2020-03-05 23:10
 *****************************************/
@Service
public class TestService {
    public void test() {
        System.out.println("aaa");
    }
}
