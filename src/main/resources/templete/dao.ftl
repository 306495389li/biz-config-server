package ${packageName}.dao;

import ${packageName}.entity.${className};
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 
 * @desc ${entityNameCN}dao接口
 *
 * @author ${author}
 * @${date}
 */
public interface ${className}Dao extends JpaRepository<${className}, Long>, JpaSpecificationExecutor<${className}> {
	
}
