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

import io.shulie.amdb.BaseTest;
import io.shulie.amdb.request.submit.AppRelationSubmitRequest;
import io.shulie.amdb.service.AppRelationService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

@Slf4j
public class AppRelationServiceTest extends BaseTest {
    @Autowired
    AppRelationService appRelationService;

    @Test
    public void test(){
        AppRelationSubmitRequest appRelationSubmitRequest = new AppRelationSubmitRequest();
        appRelationSubmitRequest.setFromAppName("a");
        appRelationSubmitRequest.setToAppName("b");
        log.info(Thread.currentThread().getName());
        appRelationService.addRelation(appRelationSubmitRequest);
    }
}