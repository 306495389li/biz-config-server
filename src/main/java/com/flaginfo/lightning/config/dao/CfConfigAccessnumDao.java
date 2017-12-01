package com.flaginfo.lightning.config.dao;

import com.flaginfo.lightning.config.entity.CfConfigAccessnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 
 * @desc 接入号配置dao接口
 *
 * @author zyong
 * @Tue May 16 11:21:05 CST 2017
 */
public interface CfConfigAccessnumDao extends JpaRepository<CfConfigAccessnum, Long>, JpaSpecificationExecutor<CfConfigAccessnum> {
	
}
