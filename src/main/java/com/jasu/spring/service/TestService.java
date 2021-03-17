package com.jasu.spring.service;

import com.jasu.spring.dao.TestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @author @Jasu
 * @date 2018-07-11 15:41
 */
@Service
public class TestService {
    @Autowired
    private TestDao testDao;
    public void setTestDao(TestDao testDao) {
        this.testDao = testDao;
    }
    private String serviceName;
    private final Random random = new Random(System.currentTimeMillis());



    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }


    public void businessService() {
        //
        System.out.println("执行service");
        boolean r = random.nextBoolean();
        if (r) {
            System.out.println("发生异常");
            throw new PessimisticLockingFailureException("失败了");
        }else {
            System.out.println("正常运行");
        }
    }
}
