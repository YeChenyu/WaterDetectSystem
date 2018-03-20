package com.jida.db.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by chenyuye on 17/12/5.
 */
@XStreamAlias("serversite")
public class ServerSite {

    private String name;//站点名称
    private String id;//站点ID
    private String longitude;//经度
    private String latitude;//纬度
    private String address;//地址
    private int datasave;//数据保存时间（单位：月）
    private int dataupload;//数据上传时间（单位：整点）

    public ServerSite(){

    }

    public ServerSite(String name, String id, String longitude, String latitude, String address,
                      int datasave, int dataupload) {
        this.name = name;
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.datasave = datasave;
        this.dataupload = dataupload;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getDatasave() {
        return datasave;
    }

    public void setDatasave(int datasave) {
        this.datasave = datasave;
    }

    public int getDataupload() {
        return dataupload;
    }

    public void setDataupload(int dataupload) {
        this.dataupload = dataupload;
    }

    @Override
    public String toString() {
        return "ServerSite: name="+ name+ " id="+ id+ " longitude="+ longitude+ " latutude="+ latitude+
                " address="+ address+ " datesave="+ datasave+ " dataupload="+ dataupload;
    }
}
