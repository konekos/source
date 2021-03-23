package com.jasu.reactor;

import com.google.common.collect.Lists;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

public class CreateDemo {
    static MyEventProcessor<String> myEventProcessor = new MyEventProcessor();

    public static void main(String[] args) {
        Flux<String> flux = Flux.create(fluxSink -> {
            myEventProcessor.register(new MyEventListener<String>() {
                @Override
                public void onDataChunk(List<String> chunk) {
                    for (String s : chunk) {
                        fluxSink.next(s);
                    }
                }

                @Override
                public void processComplete() {
                    fluxSink.complete();
                }
            });
        });

//        flux.doOnNext(s-> {
//            System.out.println(Thread.currentThread().getName() +" " + s);
//        }).subscribe();




        Flux<String> bridge = Flux.push(sink -> {
            myEventProcessor.register(
                    new SingleThreadEventListener<String>() {

                        public void onDataChunk(List<String> chunk) {
                            for(String s : chunk) {
                                sink.next(s);
                            }
                        }

                        public void processComplete() {
                            sink.complete();
                        }

                        public void processError(Throwable e) {
                            sink.error(e);
                        }
                    });
        });

        bridge.doOnNext(System.out::println).subscribe();
    }

    interface MyEventListener<T> {
        void onDataChunk(List<T> chunk);
        void processComplete();
    }

    interface SingleThreadEventListener<T>{
        void onDataChunk(List<T> chunk);
        void processComplete();
        void processError(Throwable e);
    }

    private static class MyEventProcessor<T> {
        void register(MyEventListener<T> listener) {
            List list = Lists.newArrayList("1", "2", "3");
            listener.onDataChunk(list);
            System.out.println("......");
        }
        void register(SingleThreadEventListener<T> listener){
            listener.onDataChunk((List)Lists.newArrayList("1", "2", "3"));
        }
    }
}
