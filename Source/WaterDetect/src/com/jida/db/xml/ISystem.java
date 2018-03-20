package com.jida.db.xml;

import com.jida.db.bean.ServerSite;

/**
 * Created by chenyuye on 17/12/5.
 */

public interface ISystem {

    public ServerSite getServerSiteInfo();
    public boolean updateServerSite(ServerSite info);
}
