package com.dianping.paas.core.dto.response;

import lombok.Data;

/**
 * Created by yuchao on 11/23/15.
 */

@Data
public class DockerfileResponse extends Response {
    /**
     * 基于dockerfile模板和参数build出来的镜像id
     */
    private String imageId;

    /**
     * 基于dockerfile模板和参数生成的dockerfile完整内容
     */
    private String dockerfileContent;

    /**
     * docker pull 的URI
     */
    private String repository;

    @Override
    public String toString() {
        return "DockerfileResponse{" +
                "imageId='" + imageId + '\'' +
                ", dockerfileContent='" + dockerfileContent + '\'' +
                ", repository='" + repository + '\'' +
                "} " + super.toString();
    }
}
