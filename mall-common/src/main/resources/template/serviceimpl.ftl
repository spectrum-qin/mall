<#import "function.ftl" as func>
<#assign class=model.variables.class>
<#assign pkType=func.getPkType(model) >

package  ${vars.parentDir}.${vars.module}.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ${vars.parentDir}.${vars.module}.domain.${class?cap_first};
import ${vars.parentDir}.${vars.module}.service.${class?cap_first}Service;
import  ${vars.parentDir}.${vars.module}.mapper.${class?cap_first}Mapper;

/**
* Description: ${model.tabComment} Service
<#if vars.company??>
 * Copyright: ©${date?string("yyyy")} ${vars.company}. All rights reserved.
</#if>
<#if vars.developer??>
 * @author ${vars.developer}
</#if>
* Created on: ${date?string("yyyy-MM-dd HH:mm:ss")}
*/
@Service
public class ${class}ServiceImpl implements ${class}Service{

	@Autowired
	private ${class}Mapper ${class?uncap_first}Mapper;
	
}