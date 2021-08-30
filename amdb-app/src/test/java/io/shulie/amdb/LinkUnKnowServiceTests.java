/*
package io.shulie.amdb;

import io.shulie.amdb.entity.TAMDBPradarLinkConfigDO;
import io.shulie.amdb.mapper.PradarLinkConfigMapper;
import io.shulie.amdb.service.LinkUnKnowService;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

*/
/**
 * @Author: xingchen
 * @ClassName: LinkUnKnowService
 * @Package: io.shulie.amdb
 * @Date: 2020/12/910:26
 * @Description:
 *//*

public class LinkUnKnowServiceTests extends BaseTest {
    @Autowired
    private LinkUnKnowService linkUnKnowService;

    @Autowired
    private PradarLinkConfigMapper pradarLinkConfigMapper;

    @Test
    public void processUnknowNode() {
        String linkId = "2c3663501b01a1709fec5e15c6a3ab8a";
        List<TAMDBPradarLinkConfigDO> configList = pradarLinkConfigMapper.selectAll();
        if (CollectionUtils.isNotEmpty(configList)) {
            for (int i = 0; i < configList.size(); i++) {
                if (configList.get(i).getLinkId().equals(linkId)) {
                    linkUnKnowService.processUnKnowNodeCommon(configList.get(i));
                }
            }
        }
    }

    @Test
    public void processUnknowMq() {
        String linkId = "2c3663501b01a1709fec5e15c6a3ab8a";
        List<TAMDBPradarLinkConfigDO> configList = pradarLinkConfigMapper.selectAll();
        if (CollectionUtils.isNotEmpty(configList)) {
            for (int i = 0; i < configList.size(); i++) {
                if (configList.get(i).getLinkId().equals(linkId)) {
                    linkUnKnowService.processUnKnowNodeMQ(configList.get(i));
                }
            }
        }
    }

    @Test
    public void clearUnknown(){
        linkUnKnowService.clearUnknownNode();
    }
}
*/
