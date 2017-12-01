package ${packageName}.entity;

<#if dates>
import java.util.Date;
</#if>
<#if decimal>
import java.math.BigDecimal;
</#if>
import java.io.Serializable;

/**
 * @desc ${entityNameCN}实体
 * @author ${author}
 * @${date}
 */
public class ${className} implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
	<#list properties as prop>
	/**
	 * ${(prop.comment)!""}
	 */
	private ${prop.type} ${prop.name};
	</#list>
    <#list properties as prop>
    
    public ${prop.type} get${prop.name?cap_first}() {
       return ${prop.name};
    }
    
    public void set${prop.name?cap_first}(${prop.type} ${prop.name}) {
       this.${prop.name} = ${prop.name};
    }
    </#list>
    
}
