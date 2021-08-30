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

package io.shulie.amdb.request;

import io.shulie.amdb.common.request.PagingRequest;
import lombok.Data;

import javax.persistence.Column;

/**
 * @Author: xingchen
 * @ClassName: LinkRequest
 * @Package: io.shulie.amdb.request
 * @Date: 2020/10/1914:39
 * @Description:
 */
@Data
public class LinkRequest extends PagingRequest {
    @Column(name = "链路ID")
    private Long linkId;
}
