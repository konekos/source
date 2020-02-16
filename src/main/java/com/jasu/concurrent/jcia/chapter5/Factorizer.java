package com.jasu.concurrent.jcia.chapter5;

import javax.annotation.concurrent.ThreadSafe;
import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.math.BigInteger;

/*****************************************
 * @author: Jasu Wong
 * @Date: 2020-02-16 3:40
 *****************************************/
@ThreadSafe
public class Factorizer {
    private final Computable<BigInteger, BigInteger[]> c =
            new Computable<BigInteger, BigInteger[]>() {
                @Override
                public BigInteger[] compute(BigInteger arg) throws InterruptedException {
                    return factor(arg);
                }
            };
    private final Computable<BigInteger, BigInteger[]> cache = new Memoizer4<>(c);

    public void service(ServletRequest req, ServletResponse resp) {
        try {
            BigInteger i = getFromReq(req);
            encodeIntoResponse(resp, cache.compute(i));
        } catch (InterruptedException e) {
            encodeError(resp, "interrupted");
        }
    }

    private void encodeError(ServletResponse resp, String interrupted) {
        // log
    }

    private void encodeIntoResponse(ServletResponse resp, BigInteger[] compute) {
        // resp
    }

    private BigInteger getFromReq(ServletRequest req) {
        return new BigInteger(req.getAttribute("num").toString());
    }


    private BigInteger[] factor(BigInteger arg) {
        // 计算
        return new BigInteger[0];
    }
}
