package com.jasu.dubbo.chapter4_spi;

import org.apache.dubbo.common.extension.SPI;

/*****************************************
 * @author hjs
 * @date 2020-02-26 20:52
 *****************************************/
@SPI("impl")
public interface PrintService {
    void print(String s);
}
