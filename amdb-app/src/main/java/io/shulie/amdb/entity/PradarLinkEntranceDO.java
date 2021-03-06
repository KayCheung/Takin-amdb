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

package io.shulie.amdb.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@ApiModel("")
@Table(name = "`t_amdb_pradar_link_entrance`")
public class PradarLinkEntranceDO implements Serializable {
    /**
     * ID
     */
    @Id
    @Column(name = "`id`")
    @ApiModelProperty("ID")
    private Long id;

    /**
     * 入口ID
     */
    @Column(name = "`entrance_id`")
    @ApiModelProperty("入口ID")
    private String entranceId;

    /**
     * 应用名称
     */
    @Column(name = "`app_name`")
    @ApiModelProperty("应用名称")
    private String appName;

    /**
     * 服务名
     */
    @Column(name = "`service_name`")
    @ApiModelProperty("服务名")
    private String serviceName;

    /**
     * 方法名
     */
    @Column(name = "`method_name`")
    @ApiModelProperty("方法名")
    private String methodName;

    /**
     * 中间件名称
     */
    @Column(name = "`middleware_name`")
    @ApiModelProperty("中间件名称")
    private String middlewareName;

    /**
     * rpcType
     */
    @Column(name = "`rpc_type`")
    @ApiModelProperty("rpcType")
    private Integer rpcType;

    /**
     * extend
     */
    @Column(name = "`extend`")
    @ApiModelProperty("extend")
    private String extend;

    /**
     * linkType
     */
    @Column(name = "`link_type`")
    @ApiModelProperty("linkType")
    private String linkType;

    /**
     * linkType
     */
    @Column(name = "`up_app_name`")
    @ApiModelProperty("upAppName")
    private String upAppName;

    /**
     * 创建时间
     */
    @Column(name = "`gmt_create`")
    @ApiModelProperty("创建时间")
    private Date gmtCreate;

    /**
     * 更新时间
     */
    @Column(name = "`gmt_modify`")
    @ApiModelProperty("更新时间")
    private Date gmtModify;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", entranceId=").append(entranceId);
        sb.append(", appName=").append(appName);
        sb.append(", serviceName=").append(serviceName);
        sb.append(", methodName=").append(methodName);
        sb.append(", middlewareName=").append(middlewareName);
        sb.append(", rpcType=").append(rpcType);
        sb.append(", extend=").append(extend);
        sb.append(", linkType=").append(linkType);
        sb.append(", upAppName=").append(upAppName);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtModify=").append(gmtModify);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}