<#include "javadoc-copyright.ftl">
<#import "function.ftl" as func>
<#assign class=model.variables.class>
<#assign pkType=func.getPkType(model) >
package ${vars.parentDir}.${vars.module}.po;

import com.spectrum.mall.common.BaseDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

<#include "javadoc-file.ftl">

@ApiModel
@Data
@EqualsAndHashCode(callSuper=true)
@Accessors(chain = true)
public class ${class}Po  extends BaseDomain<${pkType}>{

    private static final long serialVersionUID = 1L;

<#list model.columnList as columnModel>
	 <#if (columnModel.columnName?upper_case=="ID") > 
	<#elseif (columnModel.columnName?upper_case=="CREATE_TIME")>
	<#elseif (columnModel.columnName?upper_case=="UPDATE_TIME")>
	<#elseif (columnModel.columnName?upper_case=="CREATE_USER")>
	<#elseif (columnModel.columnName?upper_case=="UPDATE_USER")>	
	<#elseif (columnModel.columnName?upper_case=="DEL_FLAG")>	
	<#else>
	
	 /**
	 * ${columnModel.comment}
	 */
	 @ApiModelProperty(value = "${columnModel.comment}")
	 <#if (columnModel.colType=="BigDecimal") > 
	 private	java.math.BigDecimal	${columnModel.humpColumnName?uncap_first};
	 <#else>
	 private	${columnModel.colType}	${columnModel.humpColumnName?uncap_first};
	 </#if>
	 </#if>
</#list>

<#--
<#list model.columnList as columnModel>
	<#if (columnModel.columnName?upper_case=="ID") > 
	<#elseif (columnModel.columnName?upper_case=="CREATE_TIME")>
	<#elseif (columnModel.columnName?upper_case=="UPDATE_TIME")>
	<#elseif (columnModel.columnName?upper_case=="CREATE_USER")>
	<#elseif (columnModel.columnName?upper_case=="UPDATE_USER")>
	<#elseif (columnModel.columnName?upper_case=="DEL_FLAG")>
	<#else>
	
	/**
     * @param ${columnModel.humpColumnName?uncap_first} the ${columnModel.humpColumnName?uncap_first} to set
     */
	public void set${columnModel.humpColumnName}(${columnModel.colType} ${columnModel.humpColumnName?uncap_first}) {
		this.${columnModel.humpColumnName?uncap_first} = ${columnModel.humpColumnName?uncap_first};
	}
	/**
     * @return the ${columnModel.humpColumnName?uncap_first}
     */
	public ${columnModel.colType} get${columnModel.humpColumnName}() {
		return ${columnModel.humpColumnName?uncap_first};
	}
	</#if>
</#list>  -->
}