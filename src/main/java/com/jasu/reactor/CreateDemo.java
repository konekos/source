package com.jasu.reactor;

import com.google.common.collect.Lists;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

public class CreateDemo {
    static MyEventProcessor<String> myEventProcessor = new MyEventProcessor<>();
    static MyMessageProcessor<String> myMessageProcessor = new MyMessageProcessor<>();
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

//        bridge.doOnNext(System.out::println).subscribe();


        Flux<String> bridge1 = Flux.create(sink -> {
            myMessageProcessor.register(
                    new MyMessageListener<String>() {

                        public void onMessage(List<String> messages) {
                            for(String s : messages) {
                                sink.next(s);
                            }
                        }
                    });
            sink.onRequest(n -> {
                List<String> messages = myMessageProcessor.getHistory(n);
                for(String s : messages) {
                    sink.next(s);
                }
            });
        });

        bridge1.doOnNext(System.out::println).subscribe();
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

    interface MyMessageListener<T> {
        void onMessage(List<T> messages);
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

    private static class MyMessageProcessor<T>{
        void register(MyMessageListener<T> listener){
            listener.onMessage((List) Lists.newArrayList("1", "2", "4"));

        }

        List<T> getHistory(long n){
            return (List) Lists.newArrayList("2", "7", "8");
        }
    }


}
