package com.jida.db;

/**
 * Created by chenyuye on 17/12/5.
 */

public class XmlManager {

    private static final String TAG = "DataBase";

    private static XmlManager mInstance = null;

    public static XmlManager getInstance(){
        if(mInstance == null)
            mInstance = new XmlManager();
        return mInstance;
    }


}
