package com.flaginfo.lightning.config.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flaginfo.lightning.config.entity.CfConfigAccessnum;
import com.flaginfo.lightning.config.service.CfConfigAccessnumService;

import com.flaginfo.lightning.common.BaseService;
import com.flaginfo.lightning.common.BaseController;

/**
 * 
 * @desc 接入号配置action控制类
 *
 * @author zyong
 * @Tue May 16 11:21:05 CST 2017
 */
@RestController
@SuppressWarnings("all")
@RequestMapping(value = "config/cfConfigAccessnum")
public class CfConfigAccessnumController extends BaseController<CfConfigAccessnum> {

	private static final Logger logger = LoggerFactory.getLogger(CfConfigAccessnumController.class);
	
	@Override
	@Resource(name = "cfConfigAccessnumServiceImpl")
	public void setBaseService(BaseService<CfConfigAccessnum> baseService) {
		this.baseService = baseService;
	}

}
