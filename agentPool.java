package com.springmvc.util.Agentpool;

import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class agentPool {
    //得到代理的网页
    public static String getHtml(String url){
        String entity = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();//创建一个HttpClient对象
        RequestConfig config = RequestConfig.custom().setConnectTimeout(3000).setSocketTimeout(3000).build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(config);
        //一些配置
        httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;" +
                "q=0.9,image/webp,*/*;q=0.8");
        httpGet.setHeader("Accept-Encoding", "gzip, deflate, sdch");
        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
        httpGet.setHeader("Cache-Control", "no-cache");
        httpGet.setHeader("Connection", "keep-alive");
        httpGet.setHeader("Host", "www.xicidaili.com");
        httpGet.setHeader("Pragma", "no-cache");
        httpGet.setHeader("Upgrade-Insecure-Requests", "1");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 " +
                "(KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
        try{
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            if(httpResponse.getStatusLine().getStatusCode()==200){
                entity = EntityUtils.toString(httpResponse.getEntity(),"utf-8");

            }
            httpResponse.close();
            httpClient.close();
        }catch(ClientProtocolException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        return entity;
    }
    //对上一个函数进行重载，目的是仅使用本机ip爬取一次
    public static String getHtml( String url, String ip, String port) {
        String entity = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //设置代理访问和超时处理
        System.out.println("此时线程: " + Thread.currentThread().getName() + " 爬取所使用的代理为: "
                + ip + ":" + port);
        HttpHost proxy = new HttpHost(ip, Integer.parseInt(port));
        RequestConfig config = RequestConfig.custom().setProxy(proxy).setConnectTimeout(3000).
                setSocketTimeout(3000).build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(config);

        httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;" +
                "q=0.9,image/webp,*/*;q=0.8");
        httpGet.setHeader("Accept-Encoding", "gzip, deflate, sdch");
        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
        httpGet.setHeader("Cache-Control", "no-cache");
        httpGet.setHeader("Connection", "keep-alive");
        httpGet.setHeader("Pragma", "no-cache");
        httpGet.setHeader("Upgrade-Insecure-Requests", "1");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 " +
                "(KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");

        try {
            //客户端执行httpGet方法，返回响应
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);

            //得到服务响应状态码
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                entity = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
            }

            httpResponse.close();
            httpClient.close();
        } catch (ClientProtocolException e) {
            entity = null;
        } catch (IOException e) {
            entity = null;
        }

        return entity;
    }
    //使用本机IP爬取网站中的高匿代理(让别人找不到你的真正的IP)使用的是www.xicidaili.com/wn/
    public static List<seriaJava> parseUrl(String url,List<seriaJava> seriaJavas){
            String html = getHtml(url);
            if(html!=null) {
                Document document = Jsoup.parse(html);
                Elements element = document.select("table[id=ip_list]").select("tbody").select("tr");
                for (int i = 1; i <element.size(); i++) {
                    seriaJava seriaJava1 = new seriaJava();

                    String IPAddress = element.get(i).select("td").get(1).text();

                    String IPPort = element.get(i).select("td").get(2).text();
                    String IPType = element.get(i).select("td").get(5).text();
                    String IPSpeed = element.get(i).select("td").get(6).select("div[class=bar]").attr("title");
                    seriaJava1.setIPAdress(IPAddress);
                    seriaJava1.setIPPort(IPPort);
                    seriaJava1.setIPType(IPType);
                    seriaJava1.setIPSpeed(IPSpeed);
                    seriaJavas.add(seriaJava1);

                }
                return seriaJavas;
            }else{
                return null;
            }

    }
    //使用本机IP爬取网站中的高匿代理(让别人找不到你的真正的IP)使用的是view-source:https://www.kuaidaili.com/free/inha/1/
    public static List<seriaJava> parseUrl_1(String url,List<seriaJava> seriaJavas){
        String html = getHtml(url);

        if(html!=null) {
            Document document = Jsoup.parse(html);
            Elements element = document.select("table[class=table table-bordered table-striped]").select("tbody").select("tr");

            for (int i = 1; i <element.size(); i++) {
                seriaJava seriaJava1 = new seriaJava();

                String IPAddress = element.get(i).select("td").get(0).text();

                String IPPort = element.get(i).select("td").get(1).text();
                String IPType = element.get(i).select("td").get(3).text();
                String IPSpeed = element.get(i).select("td").get(5).text();
                seriaJava1.setIPAdress(IPAddress);
                seriaJava1.setIPPort(IPPort);
                seriaJava1.setIPType(IPType);
                seriaJava1.setIPSpeed(IPSpeed);
                seriaJavas.add(seriaJava1);

            }
            return seriaJavas;
        }else{
            return null;
        }

    }
    //过滤一些你不需要的ip(去除掉http和速度大于2秒的)
    public static List<seriaJava> ipFilter(List<seriaJava> seriaJavas)
    {
        List<seriaJava>  newseria = new ArrayList<>();
        for(int i=0;i<seriaJavas.size();i++){
            String IPSpeed = seriaJavas.get(i).getIPSpeed();
            String IPType = seriaJavas.get(i).getIPType();
            IPSpeed = IPSpeed.substring(0,IPSpeed.indexOf("秒"));

            double speed = Double.parseDouble(IPSpeed);
            if(speed<=2.0){
                newseria.add(seriaJavas.get(i));
            }
        }
        return newseria;
    }
    //测试代理是否有效，尝试爬取百度首页，看是否成功爬取(移除无效的IP)
    public static void IPCeshi(List<seriaJava> seriaJavas){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;

        for(int i = 0; i < seriaJavas.size(); i++) {
            String ip =seriaJavas.get(i).getIPAdress();
            String port = seriaJavas.get(i).getIPPort();
            System.out.println(ip);
            System.out.println(port);
            //不知道原因，当遇到118.190.94.224:9001的时候会在execute卡死，也不报异常
            if (ip.equals("118.190.94.224") && port.equals("9001")){
                seriaJavas.remove(seriaJavas.get(i));
                i--;
                continue;
            }
            HttpHost proxy = new HttpHost(ip, Integer.parseInt(port));
            RequestConfig config = RequestConfig.custom().setProxy(proxy).setConnectTimeout(5000).
                    setSocketTimeout(5000).build();
            HttpGet httpGet = new HttpGet("http://www.baidu.com");
            httpGet.setConfig(config);

            httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;" +
                    "q=0.9,image/webp,*/*;q=0.8");
            httpGet.setHeader("Accept-Encoding", "gzip, deflate, sdch");
            httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
            httpGet.setHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit" +
                    "/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");

            try {
                response = httpClient.execute(httpGet);
                System.out.println("ip可用"+seriaJavas.get(i).getIPAdress()+':'+seriaJavas.get(i).getIPPort());
            } catch (IOException e) {
                System.out.println("不可用代理已删除" + seriaJavas.get(i).getIPAdress()
                        + ": " + seriaJavas.get(i).getIPPort());
                seriaJavas.remove(seriaJavas.get(i));

                i--;
            }
        }
        try {
            if (httpClient != null) {
                httpClient.close();
            }
            if (response != null) {
                response.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
