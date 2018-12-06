package com.springmvc.util.Agentpool;

import redis.clients.jedis.Jedis;

import java.util.List;

public class RedisFunction {
    private static String addr="127.0.0.1";
    private static int port=6379;
    private static String passwd="123456";

    //获取Jedis实例
    public synchronized static Jedis getJedis() {
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis(addr, port);
        //权限认证
        jedis.auth(passwd);

        return jedis;
    }

    //释放Jedis资源
    public static void close(final Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
}
