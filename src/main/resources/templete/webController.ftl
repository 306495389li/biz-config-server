package ${packageName}.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.Resource;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import ${packageName}.service.${className}Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.flaginfo.lightning.common.BaseService;
import com.flaginfo.lightning.common.BaseController;

/**
 * 
 * @desc ${entityNameCN}Controller控制类
 *
 * @author ${author}
 * @${date}
 */
@Controller
@SuppressWarnings("all")
@RequestMapping(value = "${moduleName}/${springName}")
public class ${className}Controller extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(${className}Controller.class);
	
	private ${className}Service ${className?uncap_first}Service;

    @Override
    @Resource(name = "${className?uncap_first}ServiceImpl")
    public void setBaseService(BaseService baseService) {
        this.baseService = baseService;
        this.${className?uncap_first}Service = (${className}Service) baseService;
    }
	
	/*
	 * edit跳转页面
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView edit(ModelAndView model) {
		RequestMapping rm = this.getClass().getAnnotation(RequestMapping.class);
		String moduleName = "";
		if (rm != null) {
			String[] values = rm.value();
			if (ArrayUtils.isNotEmpty(values)) {
				moduleName = values[0];
			}
		}
		if (moduleName.endsWith("/")) {
			moduleName = moduleName.substring(0, moduleName.length() - 1);
		}
		model.setViewName(moduleName + "/edit");
		model.addObject("moduleName", moduleName);
		return model;
	}
}
