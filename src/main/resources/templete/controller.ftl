package ${packageName}.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ${packageName}.entity.${className};
import ${packageName}.service.${className}Service;

import com.flaginfo.lightning.common.BaseService;
import com.flaginfo.lightning.common.BaseController;

/**
 * 
 * @desc ${entityNameCN}action控制类
 *
 * @author ${author}
 * @${date}
 */
@RestController
@SuppressWarnings("all")
@RequestMapping(value = "${moduleName}/${springName}")
public class ${className}Controller extends BaseController<${className}> {

	private static final Logger logger = LoggerFactory.getLogger(${className}Controller.class);
	
	@Override
	@Resource(name = "${springName}ServiceImpl")
	public void setBaseService(BaseService<${className}> baseService) {
		this.baseService = baseService;
	}

}
