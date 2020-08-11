<#include "javadoc-copyright.ftl">
<#import "function.ftl" as func>
<#assign class=model.variables.class>
package com.${vars.company}.${vars.project}${vars.javaSubProject}.orm;

import java.io.Serializable;

<#include "javadoc-file.ftl">


public interface Domain extends Serializable {
}