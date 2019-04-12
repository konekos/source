package com.jasu.pika;

import com.google.common.base.Stopwatch;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author @Jasu
 * @date 2019-01-28 15:36
 */
public class Test {
    public static void main(String[] args) {
        JedisPool jedisPool = new JedisPool("192.168.80.129",9221);
        Map<Integer, Response<String>> map = new HashMap<>();
        Stopwatch stopwatch = Stopwatch.createStarted();
//        pipeline(jedisPool, map);
        System.out.println(jedisPool.getResource().info("keyspace"));
        jedisPool.getResource().mget("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        System.out.println(stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

    private static void pipeline(JedisPool jedisPool, Map<Integer, Response<String>> map) {
        Pipeline pipelined = jedisPool.getResource().pipelined();
        for (int i = 0; i < 10; i++) {
            map.put(i, pipelined.get(String.valueOf(i)));
        }
        pipelined.sync();
        for (int i = 0; i < 7; i++) {
            pipelined.hget("h", String.valueOf(i));
        }

    }
}
