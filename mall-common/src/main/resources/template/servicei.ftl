<#include "javadoc-copyright.ftl">
<#import "function.ftl" as func>
<#assign class=model.variables.class>
package ${vars.parentDir}.${vars.module}.service;

import org.springframework.stereotype.Service;

<#include "javadoc-file.ftl">
@Service
public interface ${class}Service {
}