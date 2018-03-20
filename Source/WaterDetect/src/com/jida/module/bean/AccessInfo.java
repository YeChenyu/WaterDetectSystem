package com.jida.module.bean;

public class AccessInfo
{

    //userId
    private String userID;//第三方用户唯一凭证

    //accessToken
    private String accessToken;//获取到的凭证

    //accessSecret
    private String accessSecret;//第三方用户唯一凭证密钥，既appsecret

    private long expiresIn;//凭证有效时间，单位：秒

    public String getUserID() {

        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessSecret() {
        return accessSecret;
    }

    public void setAccessSecret(String accessSecret) {
        this.accessSecret = accessSecret;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

}

