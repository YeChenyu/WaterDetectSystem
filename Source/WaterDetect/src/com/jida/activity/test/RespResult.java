package com.jida.activity.test;

import java.util.Arrays;

/**
 * Created by cuixj on 15/3/12.
 */
public class RespResult {

    private int executeId = -9999;//解析结果
    private String respCode = "";//响应码
    private byte[] params = null;
    private byte respCmd;// 返回数据的指令（PBOC接口需要通过返回的指令做下一次执行判断）

    public int getExecuteId() {
        return executeId;
    }

    public RespResult setExecuteId(int executeId) {
        this.executeId = executeId;
        return  this;
    }

    public String getRespCode() {
        return respCode;
    }

    public RespResult setRespCode(String respCode) {
        this.respCode = respCode;
        return  this;
    }

    public byte[] getParams() {
        return params;
    }

    public void setParams(byte[] params) {
        this.params = params;
    }

    public byte getRespCmd() {
        return respCmd;
    }

    public void setRespCmd(byte respCmd) {
        this.respCmd = respCmd;
    }

    @Override
    public String toString() {
        return "RespResult [execute_id=" + executeId + ", respCode=" + respCode + ", respCmd=" + respCmd + ", params="
                + Arrays.toString(params) + "]";
    }

}
