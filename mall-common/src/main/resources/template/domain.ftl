<#include "javadoc-copyright.ftl">
<#import "function.ftl" as func>
<#assign class=model.variables.class>
<#assign colList=model.columnList>
package ${vars.parentDir}.${vars.module}.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import ${vars.parentDir}.${vars.module}.po.${class}Po;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

<#include "javadoc-file.ftl">
@ApiModel
@Data
@EqualsAndHashCode(callSuper=true)
@Accessors(chain = true)
public class ${class} extends ${class}Po  {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * toString()
     * 
     * @return
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
	 
	
}