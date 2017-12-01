package com.flaginfo.lightning.config.service;

import java.util.Map;
import com.flaginfo.lightning.common.BaseService;
import com.flaginfo.lightning.config.entity.CfConfigChannel;

/**
 * 
 * @desc 通道配置service接口
 *
 * @author zyong
 * @Tue May 16 11:21:04 CST 2017
 */
public interface CfConfigChannelService extends BaseService<CfConfigChannel> {
    /**
     * 查询通道关联的接入号信息
     * @param dataMap
     * @return
     */
    public Map<String, Object> queryChannelAccessnum(Map<String, Object> dataMap);
    
    /**
     * 绑定通道与接入号的对应关系
     *      即将接入号数据主键ID存入通道数据中
     * @param dataMap
     * @return
     * @throws Exception
     */
    public CfConfigChannel bindChannelAccessnum(Map<String, Object> dataMap) throws Exception;
    
}
