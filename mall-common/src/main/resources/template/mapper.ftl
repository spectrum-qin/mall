<#include "javadoc-copyright.ftl">
<#import "function.ftl" as func>
<#assign class=model.variables.class>
<#assign pkType=func.getPkType(model) >
package ${vars.parentDir}.${vars.module}.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
 
import ${vars.parentDir}.${vars.module}.domain.${class?cap_first};

<#include "javadoc-file.ftl">
@Mapper
public interface ${class?cap_first}Mapper extends BaseMapper<${class}> {

	
}