package com.flaginfo.lightning.config.service.impl;

import java.util.Map;
import org.slf4j.Logger;
import java.util.HashMap;
import org.slf4j.LoggerFactory;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.flaginfo.lightning.common.BaseServiceImpl;
import com.flaginfo.lightning.config.entity.CfConfigChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.flaginfo.lightning.config.service.CfConfigChannelService;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.flaginfo.lightning.config.mapper.QueryChannelAccessnumMapper;

/**
 * 
 * @desc 通道配置service接口实现类
 *
 * @author zyong
 * @Tue May 16 11:21:04 CST 2017
 */
@Service
@SuppressWarnings("all")
public class CfConfigChannelServiceImpl extends BaseServiceImpl<CfConfigChannel> implements CfConfigChannelService {
	
	private static final Logger logger = LoggerFactory.getLogger(CfConfigChannelServiceImpl.class);
	
	@Resource
	private QueryChannelAccessnumMapper queryChannelAccessnumMapper;
	
    @Override
	@Resource(name = "cfConfigChannelDao")
	public void setJpaRepository(JpaRepository<CfConfigChannel, Long> jpaRepository){
	    super.setJpaRepository(jpaRepository);
	}

	@Override
    @Resource(name = "cfConfigChannelDao")
	public void setJpaSpecificationExecutor(JpaSpecificationExecutor<CfConfigChannel> jpaSpecificationExecutor){
	    super.setJpaSpecificationExecutor(jpaSpecificationExecutor);
	}

	/**
	 * {@inheritDoc}
	 */
    @Override
    public Map<String, Object> queryChannelAccessnum(Map<String, Object> dataMap) {
        Map<String, Object> resultPageMap = new HashMap<String, Object>();
        
        Map conditions = new HashMap();
        if (dataMap.containsKey("adClientId")) {
            conditions.put("adClientId", dataMap.get("adClientId").toString());
        }
        if (dataMap.containsKey("carrier")) {
            conditions.put("carrier", dataMap.get("carrier").toString());
        }
        if (dataMap.containsKey("provinceDivision")) {
            conditions.put("provinceDivision", dataMap.get("provinceDivision").toString());
        }
        if (dataMap.containsKey("channelDivision")) {
            conditions.put("channelDivision", dataMap.get("channelDivision").toString());
        }
        
        // 设置分页信息
        int pageIndex = Integer.parseInt(dataMap.get("pageIndex").toString());
        int pageSize = Integer.parseInt(dataMap.get("pageSize").toString());
        PageBounds pageBounds = new PageBounds(pageIndex + 1, pageSize, true);
        
        PageList pageList = queryChannelAccessnumMapper.query(conditions, pageBounds);
        
        resultPageMap.put("totalElements", pageList.getPaginator().getTotalCount());
        resultPageMap.put("content", pageList);
        
        return resultPageMap;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CfConfigChannel bindChannelAccessnum(Map<String, Object> dataMap) throws Exception {
        Long cfConfigChannelId = Long.valueOf(dataMap.get("cfConfigChannelId").toString());
        Long cfConfigAccessnumId = Long.valueOf(dataMap.get("cfConfigAccessnumId").toString());
        
        CfConfigChannel cfConfigChannel = get(cfConfigChannelId);
        
        cfConfigChannel.setCfConfigAccessnumId(cfConfigAccessnumId);
        
        return save(cfConfigChannel);
    }
}
