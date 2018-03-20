package com.jida.db.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by chenyuye on 17/12/5.
 */
@XStreamAlias("server")
public class Server {

    private String name;//服务器名称
    private String ip;//ip地址
    private String pwd;//通讯密码
    private String port;//端口

    public Server(){

    }

    public Server(String name, String ip, String pwd, String port) {
        this.name = name;
        this.ip = ip;
        this.pwd = pwd;
        this.port = port;
    }

    @Override
    public String toString() {
        return "Server: name="+ getName() + " ip="+ getIp() + " pwd="+ getPwd() + " port="+ getPort();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
