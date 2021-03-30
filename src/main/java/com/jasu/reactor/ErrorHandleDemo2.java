package com.jasu.reactor;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

import java.util.Random;
import java.util.concurrent.TimeoutException;

public class ErrorHandleDemo2 {
    static Random random = new Random();
    public static void main(String[] args) {
        Flux<String> s = Flux.range(1, 10)
                .map(v -> doSomethingDangerous(v))
                .map(v -> doSecondTransform(v));
        s.subscribe(value -> System.out.println("RECEIVED " + value),
                error -> System.err.println("CAUGHT " + error)
        );

        Flux<Object> flux = Flux.just("timeout1", "unknown", "key2")
                .flatMap(k -> callExternalService(k)
                        .onErrorResume(error -> {
                            if (error instanceof TimeoutException)
                                return getFromCache(k);
                            else if (error instanceof UnknownKeyException)
                                return registerNewEntry(k, "DEFAULT");
                            else
                                return Flux.error(error);
                        })
                );
    }

    private static Publisher<?> registerNewEntry(String k, String aDefault) {
        return null;
    }

    private static Publisher<?> getFromCache(String k) {
        return null;
    }

    private static Flux<Object> callExternalService(String k) {
        return null;
    }

    private static String doSecondTransform(Object v) {
        return null;
    }

    private static Object doSomethingDangerous(Integer v) {
        int i = random.nextInt(2);
        if (i == 1) {
            throw new RuntimeException("111");
        }
        return null;
    }


    private static class UnknownKeyException extends RuntimeException{

    }
}
