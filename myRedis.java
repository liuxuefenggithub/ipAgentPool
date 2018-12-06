package com.springmvc.util.Agentpool;

import redis.clients.jedis.Jedis;

import java.util.List;

public class myRedis {
    Jedis jedis = RedisFunction.getJedis();
    //把数据存放到redis数据库中
    public  void add(List<seriaJava> seriaJavaList){
        for (seriaJava seriaJavaList1 : seriaJavaList) {
            //首先将ipMessage进行序列化
            byte[] bytes = seriaUtil.serialize(seriaJavaList1);

            jedis.rpush("IPPool".getBytes(), bytes);
        }
    }

    //将Redis中保存的对象进行反序列化
    public seriaJava getIPByList() {
        int rand = (int)(Math.random()*jedis.llen("IPPool"));

        Object o = seriaUtil.unserialize(jedis.lindex("IPPool".getBytes(), 0));
        if (o instanceof seriaJava) {
            return (seriaJava) o;
        } else {
            System.out.println("不是seriajava的一个实例~");
            return null;
        }
    }

    public void deleteKey(String key) {
        jedis.del(key);
    }

    public void close() {
        RedisFunction.close(jedis);
    }
}
