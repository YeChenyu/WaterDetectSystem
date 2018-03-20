package com.jida.db.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by chenyuye on 17/12/5.
 */
@XStreamAlias("serialport")
public class ComPort {

    private String name ;//串口名
    private long baudrate;//波特率
    private int databit;//数据位
    private int checkbit;//校验位
    private int stopbit;//停止位

    public ComPort(String name, long baudrate, int databit, int checkbit, int stopbit) {
        this.name = name;
        this.baudrate = baudrate;
        this.databit = databit;
        this.checkbit = checkbit;
        this.stopbit = stopbit;
    }

    @Override
    public String toString() {
        return "SerialPort: name"+ getName() + " baudrate="+ getBaudrate() + " databit="+ getDatabit() + " checkbit="+
                getCheckbit() + " stopbit="+ getStopbit();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getBaudrate() {
        return baudrate;
    }

    public void setBaudrate(long baudrate) {
        this.baudrate = baudrate;
    }

    public int getDatabit() {
        return databit;
    }

    public void setDatabit(int databit) {
        this.databit = databit;
    }

    public int getCheckbit() {
        return checkbit;
    }

    public void setCheckbit(int checkbit) {
        this.checkbit = checkbit;
    }

    public int getStopbit() {
        return stopbit;
    }

    public void setStopbit(int stopbit) {
        this.stopbit = stopbit;
    }
}
