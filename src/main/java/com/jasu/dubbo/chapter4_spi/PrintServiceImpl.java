package com.jasu.dubbo.chapter4_spi;

/*****************************************
 * @author hjs
 * @date 2020-02-26 21:17
 *****************************************/
public class PrintServiceImpl implements PrintService {
    @Override
    public void print(String s) {
        System.out.println(s);
    }
}
