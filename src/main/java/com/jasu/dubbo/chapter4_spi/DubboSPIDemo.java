package com.jasu.dubbo.chapter4_spi;

import org.apache.dubbo.common.extension.ExtensionLoader;

/*****************************************
 * @author hjs
 * @date 2020-02-26 21:18
 *****************************************/
public class DubboSPIDemo {
    public static void main(String[] args) {
        PrintService printService = ExtensionLoader.getExtensionLoader(PrintService.class).getDefaultExtension();
        printService.print("I print.");
    }
}
