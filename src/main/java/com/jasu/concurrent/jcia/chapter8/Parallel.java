package com.jasu.concurrent.jcia.chapter8;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executor;

/*****************************************
 * @author hjs
 * @date 2020-02-20 0:07
 *****************************************/
public class Parallel{
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


    public void sequentialRecursive(List<Node> nodes, Collection result) {
        for (Node node : nodes) {
            result.add(node.compute());
            sequentialRecursive(node.getChildren(),result);
        }
    }

    public void parallelRecursive(Executor exec, List<Node> nodes, Collection result) {
        for (Node node : nodes) {
            exec.execute(()->result.add(node.compute()));
            parallelRecursive(exec, node.getChildren(), result);
        }
    }

    private class Element {
    }
    private class Node {
        public Object compute() {
            return null;
        }

        public List<Node> getChildren() {
            return null;
        }
    }
    private void process(Element e) {
    }


}
