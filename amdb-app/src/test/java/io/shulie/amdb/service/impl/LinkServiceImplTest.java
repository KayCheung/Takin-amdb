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

package io.shulie.amdb.service.impl;

import com.google.common.collect.Sets;
import io.shulie.amdb.BasePowerMock;
import io.shulie.amdb.common.Response;
import io.shulie.amdb.common.dto.link.topology.LinkTopologyDTO;
import io.shulie.amdb.common.request.link.TopologyQueryParam;
import io.shulie.amdb.entity.TAmdbPradarLinkEdgeDO;
import io.shulie.amdb.entity.TAmdbPradarLinkNodeDO;
import io.shulie.amdb.mapper.PradarLinkEdgeMapper;
import io.shulie.amdb.mapper.PradarLinkNodeMapper;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;

@PrepareForTest({LinkServiceImpl.class})
public class LinkServiceImplTest extends BasePowerMock {
    @InjectMocks
    private LinkServiceImpl linkService;
    @Mock
    private PradarLinkNodeMapper pradarLinkNodeMapper;
    @Mock
    private PradarLinkEdgeMapper pradarLinkEdgeMapper;
    TopologyQueryParam param = new TopologyQueryParam();

    @Before
    public void init() throws Exception {
        MockitoAnnotations.initMocks(this);
        param.setAppName("mockName");
        param.setServiceName("/hello");
        param.setRpcType("2");
        param.setIsTrace(true);
        // mock掉Example
        Example example = new Example(TAmdbPradarLinkNodeDO.class);
        PowerMockito.whenNew(Example.class).withAnyArguments().thenReturn(example);

        PowerMockito.when(pradarLinkNodeMapper, "selectByExample", any()).thenReturn(selectTAmdbPradarLinkNodeDO());
        PowerMockito.when(pradarLinkEdgeMapper, "selectByExample", any()).thenReturn(selectTAmdbPradarEdgeNodeDO());
    }

    @Test
    public void getLinkTopology() throws Exception {
        Response<LinkTopologyDTO> data = linkService.getLinkTopology(param);
        Set<String> nodes = data.getData().getNodes().stream()
                .map(node -> node.getNodeName()).collect(Collectors.toSet());
        Set<String> edges = linkService.getLinkTopology(param).getData().getEdges().stream()
                .map(edge -> edge.getMiddlewareName()).collect(Collectors.toSet());
        Assert.assertEquals(nodes, Sets.newHashSet("mockName2", "mockName1-Virtual"));
        Assert.assertEquals(edges, Sets.newHashSet("http"));
    }

    public List<TAmdbPradarLinkNodeDO> selectTAmdbPradarLinkNodeDO() {
        List<TAmdbPradarLinkNodeDO> nodeDOS = Lists.newArrayList();
        TAmdbPradarLinkNodeDO node1 = new TAmdbPradarLinkNodeDO();
        node1.setAppId("1");
        node1.setAppName("mockName1-Virtual");
        node1.setMiddlewareName("http");
        node1.setLinkId("1");
        node1.setTraceAppName("mockName1");

        TAmdbPradarLinkNodeDO node2 = new TAmdbPradarLinkNodeDO();
        node2.setAppId("0");
        node2.setAppName("mockName2");
        node2.setMiddlewareName("http");
        node2.setLinkId("1");
        node2.setTraceAppName("mockName2");
        nodeDOS.add(node1);
        nodeDOS.add(node2);
        return nodeDOS;
    }

    /**
     * 从数据库中获取数据
     *
     * @return
     */
    public List<TAmdbPradarLinkEdgeDO> selectTAmdbPradarEdgeNodeDO() {
        List<TAmdbPradarLinkEdgeDO> edges = Lists.newArrayList();
        TAmdbPradarLinkEdgeDO edge = new TAmdbPradarLinkEdgeDO();
        edge.setAppName("mockName1-Virtual");
        edge.setEdgeId("11111");
        edge.setFromAppId("0");
        edge.setToAppId("1");
        edge.setRpcType("2");
        edge.setMiddlewareName("http");
        edges.add(edge);
        return edges;
    }
}