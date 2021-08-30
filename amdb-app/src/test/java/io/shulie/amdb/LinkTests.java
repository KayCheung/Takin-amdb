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

package io.shulie.amdb;

import io.shulie.amdb.common.request.link.TopologyQueryParam;
import io.shulie.amdb.entity.TAMDBPradarLinkConfigDO;
import io.shulie.amdb.mapper.PradarLinkConfigMapper;
import io.shulie.amdb.service.LinkProcessService;
import io.shulie.amdb.service.LinkService;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: xingchen
 * @ClassName: LinkTests
 * @Package: io.shulie.amdb
 * @Date: 2021/1/2610:09
 * @Description:
 */
public class LinkTests extends BaseTest {
    @Autowired
    LinkProcessService linkProcessService;

    @Autowired
    LinkService linkService;

    @Autowired
    private PradarLinkConfigMapper pradarLinkConfigMapper;

    @Test
    public void testList() {
        TopologyQueryParam param = new TopologyQueryParam();
        TAMDBPradarLinkConfigDO config = new TAMDBPradarLinkConfigDO();
        config.setLinkId("83bf2cb883e14294481339f0698829f3");
        TAMDBPradarLinkConfigDO linkConfigDO = pradarLinkConfigMapper.selectOne(config);
        if (linkConfigDO != null && StringUtils.isNotBlank(linkConfigDO.getLinkId())) {
            param.setLinkId(linkConfigDO.getLinkId());
            param.setAppName(linkConfigDO.getAppName());
            param.setExtend(linkConfigDO.getExtend());
            param.setMethod(linkConfigDO.getMethod());
            param.setRpcType(linkConfigDO.getRpcType());
            param.setServiceName(linkConfigDO.getService());
            linkService.getLinkTopology(param);
        }
    }
}
