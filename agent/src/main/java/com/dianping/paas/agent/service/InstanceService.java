package com.dianping.paas.agent.service;

import com.dianping.paas.core.dto.request.InstanceRestartRequest;
import com.dianping.paas.core.dto.request.InstanceStartRequest;
import com.dianping.paas.core.dto.request.UpgradeInstanceRequest;
import com.dianping.paas.core.dto.response.InstanceRestartResponse;
import com.dianping.paas.core.dto.response.InstanceStartResponse;

import java.io.IOException;

/**
 * chao.yu@dianping.com
 * Created by yuchao on 2015/12/02 14:57.
 */
public interface InstanceService {
    InstanceStartResponse pullImageAndRun(InstanceStartRequest instanceStartRequest);

    InstanceRestartResponse restartInstance(InstanceRestartRequest instanceRestartRequest);

    void upgrade(UpgradeInstanceRequest upgradeInstanceRequest) throws IOException;
}