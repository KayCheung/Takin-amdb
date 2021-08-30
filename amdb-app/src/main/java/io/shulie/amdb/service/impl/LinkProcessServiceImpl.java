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

import com.google.inject.Injector;
import io.shulie.amdb.service.LinkProcessService;
import io.shulie.surge.data.deploy.pradar.config.PradarSupplierConfiguration;
import io.shulie.surge.data.deploy.pradar.link.processor.LinkProcessor;
import io.shulie.surge.data.runtime.common.utils.ApiProcessor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LinkProcessServiceImpl implements InitializingBean, LinkProcessService {

    @Value("${datasource.traceAll}")
    private String datasource;
    private LinkProcessor linkProcessor;
    private ApiProcessor apiProcessor;

    @Override
    public void afterPropertiesSet() throws Exception {
        Injector injector = new PradarSupplierConfiguration().initDataRuntime().getInstance(Injector.class);

        linkProcessor = injector.getInstance(LinkProcessor.class);
        linkProcessor.setDataSourceType(datasource);

        apiProcessor = injector.getInstance(ApiProcessor.class);
        apiProcessor.init();
    }

    @Override
    public LinkProcessor getLinkProcessor() {
        return linkProcessor;
    }
}
