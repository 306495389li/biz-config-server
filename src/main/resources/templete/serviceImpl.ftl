package ${packageName}.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ${packageName}.dao.${className}Dao;
import ${packageName}.entity.${className};
import ${packageName}.service.${className}Service;

import com.flaginfo.lightning.common.BaseServiceImpl;

/**
 * 
 * @desc ${entityNameCN}service接口实现类
 *
 * @author ${author}
 * @${date}
 */
@Service
@SuppressWarnings("all")
public class ${className}ServiceImpl extends BaseServiceImpl<${className}> implements ${className}Service {
	
	private static final Logger logger = LoggerFactory.getLogger(${className}ServiceImpl.class);
	
    @Override
	@Resource(name = "${springName}Dao")
	public void setJpaRepository(JpaRepository<${className}, Long> jpaRepository){
	    super.setJpaRepository(jpaRepository);
	}

	@Override
    @Resource(name = "${springName}Dao")
	public void setJpaSpecificationExecutor(JpaSpecificationExecutor<${className}> jpaSpecificationExecutor){
	    super.setJpaSpecificationExecutor(jpaSpecificationExecutor);
	}
}
