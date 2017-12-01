package com.flaginfo.lightning.config.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.flaginfo.lightning.config.dao.CfConfigAccessnumDao;
import com.flaginfo.lightning.config.entity.CfConfigAccessnum;
import com.flaginfo.lightning.config.service.CfConfigAccessnumService;

import com.flaginfo.lightning.common.BaseServiceImpl;

/**
 * 
 * @desc 接入号配置service接口实现类
 *
 * @author zyong
 * @Tue May 16 11:21:05 CST 2017
 */
@Service
@SuppressWarnings("all")
public class CfConfigAccessnumServiceImpl extends BaseServiceImpl<CfConfigAccessnum> implements CfConfigAccessnumService {
	
	private static final Logger logger = LoggerFactory.getLogger(CfConfigAccessnumServiceImpl.class);
	
    @Override
	@Resource(name = "cfConfigAccessnumDao")
	public void setJpaRepository(JpaRepository<CfConfigAccessnum, Long> jpaRepository){
	    super.setJpaRepository(jpaRepository);
	}

	@Override
    @Resource(name = "cfConfigAccessnumDao")
	public void setJpaSpecificationExecutor(JpaSpecificationExecutor<CfConfigAccessnum> jpaSpecificationExecutor){
	    super.setJpaSpecificationExecutor(jpaSpecificationExecutor);
	}
}
