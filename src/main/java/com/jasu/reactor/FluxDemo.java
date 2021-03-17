package com.jasu.reactor;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;

public class FluxDemo {
    public static void main(String[] args) {
//        Flux<String> flux = Flux.just("1", "2", "3");
//        Disposable subscribe = flux.limitRate(3).subscribe(s -> System.out.println(Thread.currentThread().getName() + " " + s));
//
//        //
//        Flux<Integer> ints = Flux.range(1, 4)
//                .map(i -> {
//                    if (i <= 3) return i;
//                    throw new RuntimeException("Got to 4");
//                });
//        ints.subscribe(i -> System.out.println(i),
//                error -> System.err.println("Error: " + error));
//
//
//        //
//        Flux<Integer> ints1 = Flux.range(1, 4);
//        ints1.subscribe(i -> System.out.println(i),
//                error -> System.err.println("Error " + error),
//                () -> System.out.println("Done"));

//        Flux<Integer> ints2 = Flux.range(1, 4).map(i -> {
//            if (i <= 3) return i;
//            throw new RuntimeException("Got to 4");
//        });;
//        ints2.subscribe(i -> System.out.println(i),
//                error -> System.err.println("Error " + error),
//                () -> System.out.println("Done"));


        Flux<Integer> ints3 = Flux.range(2, 4);
        ints3.subscribe(i -> System.out.println(i),
                error -> System.err.println("Error " + error),
                () -> System.out.println("Done"),
                subscription -> subscription.request(3));



    }
}
