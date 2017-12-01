package com.flaginfo.lightning.config.controller;

import java.util.Map;
import org.slf4j.Logger;
import java.io.IOException;
import org.slf4j.LoggerFactory;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.flaginfo.lightning.common.ResultData;
import com.flaginfo.lightning.common.BaseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flaginfo.lightning.common.BaseController;
import org.springframework.web.bind.annotation.RequestBody;
import com.flaginfo.lightning.config.entity.CfConfigChannel;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.flaginfo.lightning.config.service.CfConfigChannelService;

/**
 * 
 * @desc 通道配置action控制类
 *
 * @author zyong
 * @Tue May 16 11:21:04 CST 2017
 */
@RestController
@SuppressWarnings("all")
@RequestMapping(value = "config/cfConfigChannel")
public class CfConfigChannelController extends BaseController<CfConfigChannel> {

	private static final Logger logger = LoggerFactory.getLogger(CfConfigChannelController.class);
	
	private CfConfigChannelService cfConfigChannelService;
	
	@Override
	@Resource(name = "cfConfigChannelServiceImpl")
	public void setBaseService(BaseService<CfConfigChannel> baseService) {
		this.baseService = baseService;
		this.cfConfigChannelService = (CfConfigChannelService) baseService;
	}
	
	@RequestMapping(value = "queryChannelAccessnum", method = RequestMethod.POST)
    public ResultData queryChannelAccessnum(HttpServletRequest request,
                                            HttpServletResponse response, 
                                            @RequestBody String body) {
        logger.info("body:={}", body);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> bodyMap = null;
        try {
            bodyMap = objectMapper.readValue(body, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResultData(e);
        }
        Map<String, Object> resultPageMap = cfConfigChannelService.queryChannelAccessnum(bodyMap);
        return new ResultData(resultPageMap);
    }
	
	/**
     * 绑定通道与接入号
     * 
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "bindChannelAccessnum", method = RequestMethod.POST)
    public ResultData bindChannelAccessnum(HttpServletRequest request, HttpServletResponse response, @RequestBody String body) {
        logger.info("involved method:=bindChannelAccessnum  &&  request para : body:={}", body);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> bodyMap = null;
        try {
            bodyMap = objectMapper.readValue(body, Map.class);
            
            CfConfigChannel entity = cfConfigChannelService.bindChannelAccessnum(bodyMap);
            
            logger.info("involved method:=bindChannelAccessnum  && excute success ");
            return new ResultData(entity);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("bindChannelAccessnum method excute error {}", body, e);
            return new ResultData(e);
        }
    }
	
}
