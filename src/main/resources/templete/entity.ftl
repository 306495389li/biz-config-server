package ${packageName}.entity;

<#if dates>
import java.util.Date;
</#if>
<#if decimal>
import java.math.BigDecimal;
</#if>
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import org.hibernate.annotations.GenericGenerator;
<#if dates>
import org.springframework.format.annotation.DateTimeFormat;
</#if>

import com.flaginfo.lightning.common.BaseEntity;

/**
 * @desc ${entityNameCN}实体
 * @author ${author}
 * @${date}
 */
@Entity
@Table(name = "${tableName}")
public class ${className} extends BaseEntity {
    
    private static final long serialVersionUID = 1L;
    
	<#list properties as prop>
	/**
	 * ${(prop.comment)!""}
	 */
	<#if prop.type == "Date">
	@Column(name = "${prop.columnName}")
	<#elseif prop.pri>
	@Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "com.flaginfo.lightning.common.IdUtil")
	@Column(name = "${prop.columnName}", nullable = ${prop.nullable}, length = ${prop.length})
	<#else>
	@Column(name = "${prop.columnName}", nullable = ${prop.nullable}, length = ${prop.length})
	</#if> 
	private ${prop.type} ${prop.name};
	</#list>
   <#list properties as prop>
   
    public ${prop.type} get${prop.name?cap_first}() {
       return ${prop.name};
    }
    
    <#if prop.type =="Date">
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public void set${prop.name?cap_first}(${prop.type} ${prop.name}) {
       this.${prop.name} = ${prop.name};
    }
    <#else>
	public void set${prop.name?cap_first}(${prop.type} ${prop.name}) {
       this.${prop.name} = ${prop.name};
    }
    </#if>
  </#list>
  
  <#list properties as prop>
  	<#if prop.pri>
	@Override
	public Long getPrimaryKey() {
		return ${prop.name};
	}
	</#if>
  </#list>
}
