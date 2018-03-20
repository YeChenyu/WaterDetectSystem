package com.jida.db.xml;

import com.jida.db.bean.ServerSite;

/**
 * Created by chenyuye on 17/12/5.
 */

public class SystemDao implements ISystem {

    private static final String TAG = "XmlDao";

    private static SystemDao mInstance = null;

    public static SystemDao getInstance(){
        if(mInstance == null)
            mInstance = new SystemDao();
        return mInstance;
    }


    @Override
    public ServerSite getServerSiteInfo() {

        return null;
    }

    @Override
    public boolean updateServerSite(ServerSite info) {

        return false;
    }
}
