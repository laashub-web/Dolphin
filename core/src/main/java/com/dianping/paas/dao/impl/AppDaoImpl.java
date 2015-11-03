package com.dianping.paas.dao.impl;

import com.dianping.paas.dao.AppDao;
import com.dianping.paas.dao.support.DefaultCrudDao;
import com.dianping.paas.entity.AppEntity;
import org.springframework.stereotype.Repository;

/**
 * chao.yu@dianping.com
 * Created by yuchao on 11/3/15.
 */
@Repository
public class AppDaoImpl extends DefaultCrudDao<AppEntity> implements AppDao {

    public String getNamespace() {
        return AppEntity.class.getCanonicalName();
    }

    public AppEntity getByAppId(String appId) {
        String statementName = getNamespace() + "." + "getByAppId";
        return getGenericDaoSupport().get(statementName, appId);
    }
}
