/*
 * Copyright 2021 Shulie Technology, Co.Ltd
 * Email: shulie@shulie.io
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.shulie.amdb.response.instance;

import io.shulie.amdb.entity.TAmdbAppInstanceStatusDO;
import lombok.Data;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.Serializable;

@Data
public class AmdbAppInstanceStautsResponse implements Serializable {
    // 应用名称
    String appName;
    // 用来判断实例的唯一性
    String agentId;
    // agent 版本
    String agentVersion;
    // 更新时间
    String agentUpdateTime;
    // 进程号 id
    String progressId;
    // ip 地址
    String ipAddress;
    // agent 支持的语言
    String agentLanguage;
    // hostname
    String hostname;
    // probeVersion
    String probeVersion;
    // probeStatus
    String probeStatus;

    public AmdbAppInstanceStautsResponse() {

    }

    public AmdbAppInstanceStautsResponse(TAmdbAppInstanceStatusDO amdbAppInstanceStatusDO) {
        this.setAppName(amdbAppInstanceStatusDO.getAppName());
        this.setAgentId(amdbAppInstanceStatusDO.getAgentId());
        this.setAgentVersion(amdbAppInstanceStatusDO.getAgentVersion());
        this.setAgentUpdateTime(DateFormatUtils.format(amdbAppInstanceStatusDO.getGmtModify(), "yyyy-MM-dd HH:mm:ss"));
        this.setProgressId(amdbAppInstanceStatusDO.getPid());
        this.setIpAddress(amdbAppInstanceStatusDO.getIp());
        this.setAgentLanguage(amdbAppInstanceStatusDO.getAgentLanguage());
        this.setHostname(amdbAppInstanceStatusDO.getHostname());
        //2021-05-28
        this.setProbeVersion(amdbAppInstanceStatusDO.getProbeVersion());
        this.setProbeStatus(amdbAppInstanceStatusDO.getProbeStatus());
    }
}