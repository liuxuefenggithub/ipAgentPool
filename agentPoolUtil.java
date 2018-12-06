package com.springmvc.util.Agentpool;

import java.util.ArrayList;
import java.util.List;

public class agentPoolUtil {
    List<seriaJava> seriaJavas = new ArrayList<>();
    //使用本机ip爬取下来的ip信息
    public  void run(){
        //获取首页可用得ip（使用的是自己的ip爬取）
        List<seriaJava> seriaJavas = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();
        List<String> urls = new ArrayList<>();
        seriaJava seriaJava1 = new seriaJava();
        seriaJavas.add(seriaJava1);
        seriaJavas = agentPool.parseUrl("https://www.kuaidaili.com/free/inha/1",seriaJavas);
        seriaJavas = agentPool.ipFilter(seriaJavas);
        System.out.println(seriaJavas);
        agentPool.IPCeshi(seriaJavas);
        //使用线程爬取后面页数的ip此时用的是第一页已经获取到的ip值
        IPPoll ipPool = new IPPoll(seriaJavas);
        
        for (int i = 2; i <= 41; i++) {
            urls.add("http://www.xicidaili.com/nn/" + i);
        }
        for (int i = 0; i < 20; i++) {
            //给每个线程进行任务的分配
            Thread IPThread = new threadClass(urls.subList(i*2, i*2+2), ipPool);
            threads.add(IPThread);
            IPThread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(seriaJavas);
        //把获取到的数据存放到数据库中
        myRedis redis = new myRedis();
        redis.add(seriaJavas);

        //从redis数据库中随机拿出一个IP
        seriaJava ipMessage = redis.getIPByList();
        System.out.println(ipMessage.getIPAdress());
        System.out.println(ipMessage.getIPPort());
        redis.close();
    }
}
