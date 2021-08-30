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

package io.shulie.amdb.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import io.shulie.amdb.AMDBAPIBootstrap;
import io.shulie.amdb.common.dto.link.entrance.ServiceInfoDTO;
import io.shulie.amdb.exception.AmdbExceptionEnums;
import io.shulie.amdb.model.AmdbResult;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

@RunWith(SpringRunner.class)
@WebAppConfiguration("src/main/resources")
@SpringBootTest(classes = AMDBAPIBootstrap.class)
public class LinkControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    private String baseUrl = "http://localhost:10032/amdb/link";

    @Before
    public void setUp() throws Exception {
        //使用上下文构建mockMvc
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /**
     * 获取服务入口列表--空参
     */
    @Test
    public void getServiceList_errorParam() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get(baseUrl + "/getServiceList")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        //得到返回代码
        int status = mvcResult.getResponse().getStatus();
        //得到返回结果
        String content = mvcResult.getResponse().getContentAsString();
        AmdbResult result = JSON.parseObject(content, AmdbResult.class);
        //断言，判断返回代码是否正确
        Assert.assertEquals("空参-判断返回代码是否正确", 200, status);
        //断言，返回结果状态失败
        Assert.assertEquals("空参-返回结果状态失败", false, result.getSuccess());
        //断言，error是不是有内容
        Assert.assertNotNull("空参-error是不是有内容", result.getError());
        //断言，判断返回错误码是不是400
        Assert.assertEquals("空参-判断返回错误码是不是100", AmdbExceptionEnums.COMMON_EMPTY_PARAM.getCode(), result.getError().getCode());


    }

    /**
     * 关键字段--APPName 合法校验
     *
     * @throws Exception
     */
    @Test
    public void getServiceList_emptyAppName() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get(baseUrl + "/getServiceList")
                        .param("fieldNames", "appName,serviceName,methodName,middlewareName,rpcType")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        //得到返回代码
        int status = mvcResult.getResponse().getStatus();
        //得到返回结果
        String content = mvcResult.getResponse().getContentAsString();
        AmdbResult result = JSON.parseObject(content, AmdbResult.class);
        //断言，判断返回代码是否正确
        Assert.assertEquals("关键字段--APPName 合法校验-判断返回代码是否正确", 200, status);
        //断言，返回结果状态失败
        Assert.assertEquals("关键字段--APPName 合法校验-返回结果状态失败", false, result.getSuccess());
        //断言，error是不是有内容
        Assert.assertNotNull("关键字段--APPName 合法校验-error是不是有内容", result.getError());
        //断言，判断返回错误码是不是400
        Assert.assertEquals("关键字段--APPName 合法校验-判断返回错误码是不是100",

                AmdbExceptionEnums.COMMON_EMPTY_PARAM.getCode(), result.getError().getCode());
    }

    /**
     * 关键字段--rpcType支持多选
     *
     * @throws Exception
     */
    @Test
    public void getServiceList_multipleRpcType() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get(baseUrl + "/getServiceList")
                        .param("rpcType", "0,1,2,3,4,5,6,7,8,9,10")
                        .param("fieldNames", "appName,serviceName,methodName,middlewareName,rpcType").param("appName", "test-app")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        //得到返回代码
        int status = mvcResult.getResponse().getStatus();
        //得到返回结果
        String content = mvcResult.getResponse().getContentAsString();
        AmdbResult<List<ServiceInfoDTO>> result = JSON.parseObject(content, new TypeReference<AmdbResult<List<ServiceInfoDTO>>>() {
        });
        //断言，判断返回代码是否正确
        Assert.assertEquals("关键字段--rpcType支持多选-判断返回代码是否正确", 200, status);
        //断言，返回结果状态成功
        Assert.assertEquals("关键字段--rpcType支持多选-返回结果状态成功", true, result.getSuccess());
        //断言，返回数据列表是不是大于0
        Assert.assertNotNull("关键字段--rpcType支持多选-返回数据列表是不是大于0", result.getData().size() > 0);
    }

    /**
     * 关键字段--rpcType过滤是否精确
     *
     * @throws Exception
     */
    @Test
    public void getServiceList_rpcTypeAccurate() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get(baseUrl + "/getServiceList")
                        .param("rpcType", "0")
                        .param("fieldNames", "appName,serviceName,methodName,middlewareName,rpcType").param("appName", "test-app")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        //得到返回代码
        int status = mvcResult.getResponse().getStatus();
        //得到返回结果
        String content = mvcResult.getResponse().getContentAsString();
        AmdbResult<List<ServiceInfoDTO>> result = JSON.parseObject(content, new TypeReference<AmdbResult<List<ServiceInfoDTO>>>() {
        });
        //断言，判断返回代码是否正确
        Assert.assertEquals("关键字段--rpcType过滤是否精确-判断返回代码是否正确", 200, status);
        //断言，返回结果状态成功
        Assert.assertEquals("关键字段--rpcType过滤是否精确-返回结果状态成功", true, result.getSuccess());
        //断言，如果返回结果有值，那么结果集中rpcType都是0
        if (CollectionUtils.isNotEmpty(result.getData())) {
            Assert.assertEquals("关键字段--rpcType过滤是否精确-如果返回结果有值，那么结果集中rpcType都是0", 0, result.getData().stream().filter(serviceInfoDTO -> !serviceInfoDTO.getRpcType().equals("0")).count());
        }
    }
}
