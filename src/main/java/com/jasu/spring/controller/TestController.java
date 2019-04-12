package com.jasu.spring.controller;

import com.jasu.spring.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author @Jasu
 * @date 2018-07-11 17:59
 */
@Controller
public class TestController {
    @Autowired
    private TestService testService;

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        testService.businessService();
        return "hello";
    }
}
