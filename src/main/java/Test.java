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

        ExecutorService service = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10000; i++) {
            service.execute(()->{
                try {
                    Request.Builder builder = new Request.Builder();
                    builder.addHeader("cookie", "loginToken=; userLoginType=60; userLanguage=ZHS; HRX_SSO_COOKIE=3c006dd7-e1c4-44f1-890b-312268c6b53b; HRX-WEB-SESSION=Se822d84c91ca42e4afb8da6797b1398f0; SSOCLIENTSESSIONID=f70592def91f2d93bb1d94190cdb76dfb50e5f000c66e9e3");
                    builder.addHeader("content-type", "application/json;charset=UTF-8");
                    builder.url("https://hrx-sit.ctg.cn/tm/portrait/tm/personal/getPersonalInfo.do");
                    builder.post(RequestBody.create(MediaType.get("application/json"), "{\"userLanguage\":\"ZHS\",\"empId\":\"10050\",\"requestOrigin\":\"emp_portrait\",\"fromMatch\":false}"));
                    Request request = builder.build();

                    Call call = client.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            // do nothing
                            System.out.println("error");
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            System.out.println(response.body().string());
                        }
                    });

                }catch (Exception e){
                    log.error("error!!!");
                }

                try {
                    TimeUnit.SECONDS.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

}
