package com.springmvc.util.Agentpool;

import java.io.Serializable;
public class seriaJava implements Serializable{
    private static final long serialVersionUID = 1L;
    private String IPAdress;
    private String IPPort;
    private String IPType;
    private String IPSpeed;
    public String getIPAdress(){
        return IPAdress;
    }
    public void setIPAdress(String IPAdress){
        this.IPAdress = IPAdress;
    }
    public String getIPPort() {
        return IPPort;
    }

    public void setIPPort(String IPPort) {
        this.IPPort = IPPort;
    }

    public String getIPType() {
        return IPType;
    }

    public void setIPType(String IPType) {
        this.IPType = IPType;
    }

    public String getIPSpeed() {
        return IPSpeed;
    }

    public void setIPSpeed(String IPSpeed) {
        this.IPSpeed = IPSpeed;
    }
    @Override //下面这个方法是从父类/接口 继承过来的
    public String toString(){
        return IPAdress + ":" + IPPort;
    }

}
