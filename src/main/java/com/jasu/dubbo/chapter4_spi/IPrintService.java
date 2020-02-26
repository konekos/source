package com.jasu.dubbo.chapter4_spi;

/*****************************************
 * @author hjs
 * @date 2020-02-26 20:52
 *****************************************/
public class IPrintService implements PrintService {

    @Override
    public void print(String s) {
        System.out.println(s);
    }
}
