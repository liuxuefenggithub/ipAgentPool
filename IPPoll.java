package com.springmvc.util.Agentpool;

import java.util.ArrayList;
import java.util.List;

public class IPPoll {
    private List<seriaJava> seriaJavaList;
    public IPPoll(List<seriaJava> seriaJavas){
        this.seriaJavaList = seriaJavas;
    }
    public  void getAbleIP(List<String> urls){
        String Address;
        String port;
        for(int i=0;i<urls.size();i++) {
            List<seriaJava> seriaJavas = new ArrayList<>();
            String url = urls.get(i);
            //随机选取一个已经存在的ip作为爬取的ip(线程安全，考虑到有可能取得过程中，还有线程在合并得问题)
            synchronized (seriaJavaList) {
                int random = (int) Math.random() * seriaJavaList.size();
                Address = seriaJavaList.get(random).getIPAdress();
                port = seriaJavaList.get(random).getIPPort();

            }

            List<seriaJava> status = agentPool.parseUrl(url, seriaJavaList);
            if(status==null){
                i--;
                continue;
            }else{
                System.out.println("此ip有效，正在抓取网页....");
            }
            seriaJavas = agentPool.ipFilter(status);//对ip进行过滤
            agentPool.IPCeshi(seriaJavas);//用这些ip进行访问百度
            synchronized (seriaJavas){
                seriaJavaList.addAll(seriaJavas);
            }
        }
    }
}
