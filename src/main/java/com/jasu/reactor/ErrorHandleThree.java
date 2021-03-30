package com.jasu.reactor;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;


@Slf4j
public class ErrorHandleThree {
    public static void main(String[] args) throws InterruptedException {
        LongAdder failureStat = new LongAdder();
        Flux<String> flux =
                Flux.just("unknown")
                        .flatMap(k -> callExternalService(k)
                                .doOnError(e -> {
                                    failureStat.increment();
                                    log.info("uh oh, falling back, service failed for key " + k);
                                })

                        );

        flux.subscribe(System.out::println);
        log.info("stat=" + failureStat);
    }

    private static Flux<String> callExternalService(String k) {
        return Flux.just(1, 2, 3)
                .doOnNext(integer -> System.out.println(integer / 0))
                .map(String::valueOf);
    }


}
