package com.jida.db.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by chenyuye on 17/12/5.
 */
@XStreamAlias("system")
public class System {

    private String password;
    private ServerSite serversite;
    private NetWork network;
    private ComPort serialport;

    public System(){

    }

    public System(String password, ServerSite serversite, NetWork network, ComPort serialport) {
        this.password = password;
        this.serversite = serversite;
        this.network = network;
        this.serialport = serialport;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ServerSite getServersite() {
        return serversite;
    }

    public void setServersite(ServerSite serversite) {
        this.serversite = serversite;
    }

    public NetWork getNetwork() {
        return network;
    }

    public void setNetwork(NetWork network) {
        this.network = network;
    }

    public ComPort getSerialport() {
        return serialport;
    }

    public void setSerialport(ComPort serialport) {
        this.serialport = serialport;
    }

    @Override
    public String toString() {
        return "System: password=" + password + "\n"+ serversite.toString() + "\n"+
                network.toString() + "\n"+ serialport.toString();
    }
}


