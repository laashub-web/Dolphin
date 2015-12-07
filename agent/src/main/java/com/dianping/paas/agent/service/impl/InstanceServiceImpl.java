package com.dianping.paas.agent.service.impl;

import com.dianping.paas.agent.service.DockerContainerService;
import com.dianping.paas.agent.service.DockerImageService;
import com.dianping.paas.agent.service.InstanceService;
import com.dianping.paas.core.dto.request.InstanceRestartRequest;
import com.dianping.paas.core.dto.request.InstanceStartRequest;
import com.dianping.paas.core.dto.request.UpgradeInstanceRequest;
import com.dianping.paas.core.dto.response.InstanceRestartResponse;
import com.dianping.paas.core.dto.response.InstanceStartResponse;
import com.dianping.paas.core.util.FileUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URL;
import java.util.zip.ZipInputStream;

/**
 * chao.yu@dianping.com
 * Created by yuchao on 2015/12/02 14:59.
 */
@Component
public class InstanceServiceImpl implements InstanceService {
    private static final Logger logger = LogManager.getLogger(InstanceServiceImpl.class);

    @Resource
    private DockerImageService dockerImageService;

    @Resource
    private DockerContainerService dockerContainerService;


    public InstanceStartResponse pullImageAndRun(final InstanceStartRequest request) {
        InstanceStartResponse response = new InstanceStartResponse();

        if (pullImage(request, response)) {
            runImage(request, response);
        }

        return response;
    }

    private boolean pullImage(InstanceStartRequest request, InstanceStartResponse response) {
        logger.info(String.format("begin pullImage: %s", request));

        dockerImageService.pull(request, response);

        logger.info(String.format("end pullImage: %s", response));

        return response.isSuccess();
    }

    private void runImage(InstanceStartRequest request, InstanceStartResponse response) {
        logger.info(String.format("begin runImage: %s", request));

        dockerImageService.run(request, response);

        logger.info(String.format("end runImage: %s", response));
    }

    public InstanceRestartResponse restartInstance(InstanceRestartRequest request) {
        logger.info(String.format("begin restartInstance: %s", request));

        InstanceRestartResponse response = new InstanceRestartResponse();

        dockerContainerService.restartContainer(request, response);

        logger.info(String.format("end restartInstance: %s", response));

        return response;
    }

    public void upgrade(UpgradeInstanceRequest request) throws IOException {
        // 0. find dir of web package
        String webPackageRootDir = locateWebPackageRootDir(request);

        // 1. downLoad web package
        URL url = new URL(request.getWebPackageUrl());
        ZipInputStream zipInputStream = new ZipInputStream(url.openStream());
        FileUtil.unZip(zipInputStream, webPackageRootDir);

        // 2. restart container

        restartInstance(buildInstanceRestartRequest(request));

    }

    private InstanceRestartRequest buildInstanceRestartRequest(UpgradeInstanceRequest request) {
        InstanceRestartRequest instanceRestartRequest = new InstanceRestartRequest();

        instanceRestartRequest.setContainerId(request.getInstance_id());

        return instanceRestartRequest;
    }

    // TODO
    private String locateWebPackageRootDir(UpgradeInstanceRequest request) {

        return String.format("/data/paas/webapps/%s", request.getApp_id());
    }
}

