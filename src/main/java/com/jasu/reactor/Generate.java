package com.jasu.reactor;

import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicLong;

public class Generate {
    public static void main(String[] args) {

        Flux<String> flux = Flux.generate(() -> 0, (state, sink) -> {
            sink.next("3 x " + state + " = " + 3 * state);
            if (state == 10) {
                sink.complete();
            }
            return state + 1;
        });
        flux.doOnNext(s -> System.out.println(s)).subscribe();


        Flux<String> flux1 = Flux.generate(AtomicLong::new, (state, sink) -> {
            long i = state.getAndIncrement();
            sink.next("3 x " + i + " = " + 3 * i);
            if (i == 10) {
                sink.complete();
            }
            return state;
        });

        flux1.doOnNext(System.out::println).subscribe();


        Flux<String> flux2 = Flux.generate(AtomicLong::new, (state, sink) -> {
            long i = state.getAndIncrement();
            sink.next("3 x " + i + " = " + 3 * i);
            if (i == 10) sink.complete();
            return state;
        }, (state) -> System.out.println("state: " + state));

        flux2.doOnNext(System.out::println).subscribe();
    }
}
