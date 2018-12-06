package com.springmvc.util.Agentpool;

import java.util.List;

public class threadClass extends Thread{
    private List<String> urls;
    private IPPoll ipPoll;
    public threadClass(List<String> urls,IPPoll ipPoll){
        this.urls = urls;
        this.ipPoll = ipPoll;
    }
    @Override
    public void run(){
        for(String url:urls){
            System.out.println("当前线程的名字:"+Thread.currentThread().getName()+"爬取的地址为:"+url);
        }
        ipPoll.getAbleIP(urls);
    }
}
