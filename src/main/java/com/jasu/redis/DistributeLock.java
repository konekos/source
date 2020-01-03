package com.jasu.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Collections;

/**
 * @author huangjiashu
 * @date 2019/12/24
 **/
public class DistributeLock {
    private static String luaScript = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
    private static final Long lockReleaseOK = 1L;
    private static JedisPool jedis = new JedisPool("192.168.0.73", 6379);
    public static void main(String[] args) {
        getJedisPool().getResource().setnx("1","2");
    }

    public static boolean releaseLock(String key ,String lockValue){
        if(key == null || lockValue == null) {
            return false;
        }
        try {
            Jedis jedis = getJedisPool().getResource();
            Object res =jedis.eval(luaScript,Collections.singletonList(key), Collections.singletonList(lockValue));
            jedis.close();
            return res!=null && res.equals(lockReleaseOK);
        } catch (Exception e) {
            return false;
        }
    }

    private static JedisPool getJedisPool() {
        return jedis;
    }

}
