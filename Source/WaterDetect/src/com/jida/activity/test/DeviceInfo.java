package com.jida.activity.test;

public class DeviceInfo {
    /**
     * <i class="dis_ch">硬件版本号</i><i class="dis_en">Hardware version number</i>
     */
    private String hardwareVersion;

    /**
     * Device hardware number（SN code），len：12
     */
    private String termSN;
    /**
     * Device personalization， len：1。0xFF：default initial state；0x00：personalized completion
     */
    private String privatize;
    /**
     * App version,len:16,ASCII
     */
    private String appVersion;
    /**
     * Retain,len:10,production rules，default 0x00
     */
    private String retain;
    // 设备状态    1   见MPOS设备硬件能力
    private byte deviceStatus;
    /**
     * Firmware version,len:24,ASCII
     */
    private String firmwareVersion;
    //客户序列号（CSN）  LL..24  由客户定义的设备序列号，一般为根据接入环境要求而定义的设备硬件编号，也可以称为设备ID
    private String CSN;
    //密钥序列号（KSN）  LL..40  由客户定义的密钥序列号
    private String KSN;

    private String productID;


    private String manufacturerID;
    //生产SN号   LL..40  根据生产要求而自定义的SN号
    /**
     * Production SN number   LL..40  Customized SN number according to production requirements
     */
    private String produceSN;
    //Boot版本  LL..40  ASCII
    private String bootVersion;

    private String spliceVersion;


    private String DeviceBlueName;
    private String DeviceBlueAddress;

//    /**
//     * 获取硬件版本号
//     *
//     * @return
//     */
    public String getHardwareVersion() {
        return hardwareVersion;
    }

    public void setHardwareVersion(String hardwareVersion) {
        this.hardwareVersion = hardwareVersion;
    }

//    /**
//     * Get terminal number
//     * 获取终端号 (作废)
//     *
//     * @return
//     * @deprecated
//     */
    public String getTermNo() {
        return KSN;
    }

//    /**
//     * Get merchant number
//     *  获取商户号
//     *
//     * @return
//     */
    public String getMerchantNo() {
        return CSN;
    }

    /**
     *<i class="dis_ch">获取终端序列号</i><i class="dis_en">get sn number</i>
     * @return
     */
    public String getTermSN() {
        return termSN;
    }

    public void setTermSN(String termSN) {
        this.termSN = termSN;
    }

//    /**
//     * 获取个人化状态
//     *
//     * @return
//     */
    public String getPrivatize() {
        return privatize;
    }

    public void setPrivatize(String privatize) {
        this.privatize = privatize;
    }

    /**
     *<i class="dis_ch">获取应用版本号</i><i class="dis_en">get app version</i>
     * @return
     */
    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

//    /**
//     * 获取保留字段
//     *
//     * @return
//     */
    public String getRetain() {
        return retain;
    }

    public void setRetain(String retain) {
        this.retain = retain;
    }

//    /**
//     * 获取设备状态
//     *
//     * @return
//     */
    public byte getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(byte deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    /**
     *<i class="dis_ch">获取固件版本号(K21系统版本号)</i><i class="dis_en">gets the firmware version number (K21 system version number)</i>
     * @return
     */
    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    /**
     * get CSN
     *
     * @return
     */
    public String getCSN() {
        return CSN;
    }

    public void setCSN(String cSN) {
        CSN = cSN;
    }

    /**
     * get KSN
     *
     * @return
     */
    public String getKSN() {
        return KSN;
    }

    public void setKSN(String kSN) {
        KSN = kSN;
    }

    /**
     * <i class="dis_ch">获取产品编号</i><i class="dis_en">get product number</i>
     * @return
     */
    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    /**
     * <i class="dis_ch">获取厂商编号</i><i class="dis_en">get manufacturer number</i>
     * @return
     */
    public String getManufacturerID() {
        return manufacturerID;
    }

    public void setManufacturerID(String manufacturerID) {
        this.manufacturerID = manufacturerID;
    }

    /**
     *<i class="dis_ch">获取产品序列号</i><i class="dis_en">get product serial number</i>
     * @return
     */
    public String getProduceSN() {
        return produceSN;
    }

    public void setProduceSN(String produceSN) {
        this.produceSN = produceSN;
    }

    /**
     *<i class="dis_ch">获取boot版本号(用于编译时间)</i><i class="dis_en">get boot number</i>
     * @return
     */
    public String getBootVersion() {
        return bootVersion;
    }

    public void setBootVersion(String bootVersion) {
        this.bootVersion = bootVersion;
    }

//    /**
//     * 获取设备蓝牙名称(无效)
//     *
//     * @return
//     * @deprecated
//     */
    public String getDeviceBlueName() {
        return DeviceBlueName;
    }

    public void setDeviceBlueName(String deviceBlueName) {
        DeviceBlueName = deviceBlueName;
    }

//    /**
//     * 获取设备蓝牙地址(无效)
//     *
//     * @return
//     * @deprecated
//     */
    public String getDeviceBlueAddress() {
        return DeviceBlueAddress;
    }

    public void setDeviceBlueAddress(String deviceBlueAddress) {
        DeviceBlueAddress = deviceBlueAddress;
    }

    /**
     *<i class="dis_ch">获取版本号(X990惠尔丰规则)</i><i class="dis_en">Gets the version number (X990 verifone rules)</i>
     * @return
     */
    public String getSpliceVersion() {
        return spliceVersion;
    }

    public void setSpliceVersion(String spliceVersion) {
        this.spliceVersion = spliceVersion;
    }

    @Override
    public String toString() {
        return "DeviceInfo{" +
                "termSN='" + termSN + '\'' +
                ", privatize='" + privatize + '\'' +
                ", appVersion='" + appVersion + '\'' +
                ", retain='" + retain + '\'' +
                ", deviceStatus=" + deviceStatus +
                ", firmwareVersion='" + firmwareVersion + '\'' +
                ", CSN='" + CSN + '\'' +
                ", KSN='" + KSN + '\'' +
                ", productID='" + productID + '\'' +
                ", manufacturerID='" + manufacturerID + '\'' +
                ", produceSN='" + produceSN + '\'' +
                ", bootVersion='" + bootVersion + '\'' +
                ", spliceVersion='" + spliceVersion + '\'' +
                ", DeviceBlueName='" + DeviceBlueName + '\'' +
                ", DeviceBlueAddress='" + DeviceBlueAddress + '\'' +
                '}';
    }
}
