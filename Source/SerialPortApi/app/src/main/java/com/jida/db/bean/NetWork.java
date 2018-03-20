package com.jida.db.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by chenyuye on 17/12/5.
 */
@XStreamAlias("network")
public class NetWork {

    private int nettype;//网络类型
    private String ip;//IP
    private String submask;//子网掩码
    private String defgateway;//默认网关
    private String dns1;//
    private String dns2;//
    private int netstate;//网络状态
    private int bluetooth;//蓝牙开关
    private int discovery;//蓝牙可见性
    private int wifi;//wifi开关
    private Server servera;//服务器A
    private Server serverb;//
    private Server serverc;//

    public NetWork(){

    }

    public NetWork(int nettype, String ip, String submask, String defgateway, String dns1,
                   String dns2, int netstate, int bluetooth, int discovery, int wifi,
                   Server servera, Server serverb, Server serverc) {
        this.nettype = nettype;
        this.ip = ip;
        this.submask = submask;
        this.defgateway = defgateway;
        this.dns1 = dns1;
        this.dns2 = dns2;
        this.netstate = netstate;
        this.bluetooth = bluetooth;
        this.discovery = discovery;
        this.wifi = wifi;
        this.servera = servera;
        this.serverb = serverb;
        this.serverc = serverc;
    }

    public int getNettype() {
        return nettype;
    }

    public void setNettype(int nettype) {
        this.nettype = nettype;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSubmask() {
        return submask;
    }

    public void setSubmask(String submask) {
        this.submask = submask;
    }

    public String getDefgateway() {
        return defgateway;
    }

    public void setDefgateway(String defgateway) {
        this.defgateway = defgateway;
    }

    public String getDns1() {
        return dns1;
    }

    public void setDns1(String dns1) {
        this.dns1 = dns1;
    }

    public String getDns2() {
        return dns2;
    }

    public void setDns2(String dns2) {
        this.dns2 = dns2;
    }

    public int getNetstate() {
        return netstate;
    }

    public void setNetstate(int netstate) {
        this.netstate = netstate;
    }

    public int getBluetooth() {
        return bluetooth;
    }

    public void setBluetooth(int bluetooth) {
        this.bluetooth = bluetooth;
    }

    public int getDiscovery() {
        return discovery;
    }

    public void setDiscovery(int discovery) {
        this.discovery = discovery;
    }

    public int getWifi() {
        return wifi;
    }

    public void setWifi(int wifi) {
        this.wifi = wifi;
    }

    public Server getServera() {
        return servera;
    }

    public void setServera(Server servera) {
        this.servera = servera;
    }

    public Server getServerb() {
        return serverb;
    }

    public void setServerb(Server serverb) {
        this.serverb = serverb;
    }

    public Server getServerc() {
        return serverc;
    }

    public void setServerc(Server serverc) {
        this.serverc = serverc;
    }

    @Override
    public String toString() {
        return "NetWork: nettype="+ nettype+ " ip";
    }
}
