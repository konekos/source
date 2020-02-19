package com.jasu.concurrent.jcia.chapter8;

import java.util.List;
import java.util.concurrent.Executor;

/*****************************************
 * @author hjs
 * @date 2020-02-20 0:07
 *****************************************/
public class Parallel {
    void processSequentially(List<Element> elements) {
        for (Element e: elements) {
            process(e);
        }
    }



    void processInParallel(Executor exec, List<Element> elements) {
        for (final Element e : elements) {
            exec.execute(()->process(e));
        }
    }

    private class Element {
    }

    private void process(Element e) {
    }
}
