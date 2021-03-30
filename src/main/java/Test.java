import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.dubbo.remoting.exchange.support.DefaultFuture;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author @Jasu
 * @date 2018-07-11 15:45
 */
@Slf4j
public class Test {
    static ExecutorService service = Executors.newFixedThreadPool(3);

    static  OkHttpClient client = new OkHttpClient();

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
        Flux.just(1, 2, 3).flatMap(integer -> add(integer).onErrorResume(
                throwable -> Flux.error(new RuntimeException("e"))
        ));
    }


    private static Flux<Integer>   add(int i) {
        return Flux.just(i + 2);
    }
}
