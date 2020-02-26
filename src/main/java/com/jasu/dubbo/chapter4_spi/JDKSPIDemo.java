package com.jasu.dubbo.chapter4_spi;

import java.util.ServiceLoader;

/*****************************************
 * @author hjs
 * @date 2020-02-26 20:54
 *****************************************/
public class JDKSPIDemo {
    public static void main(String[] args) {
        ServiceLoader<PrintService> serviceLoader = ServiceLoader.load(PrintService.class);
        for (PrintService printService : serviceLoader) {
            printService.print("I print");
        }
    }
}
