package com.flaginfo.lightning.config.dao;

import com.flaginfo.lightning.config.entity.CfConfigChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 
 * @desc 通道配置dao接口
 *
 * @author zyong
 * @Tue May 16 11:21:04 CST 2017
 */
public interface CfConfigChannelDao extends JpaRepository<CfConfigChannel, Long>, JpaSpecificationExecutor<CfConfigChannel> {
	
}
