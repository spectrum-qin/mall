<#include "javadoc-copyright.ftl">
<#import "function.ftl" as func>
<#assign class=model.variables.class>
package com.${vars.company}.${vars.project}${vars.javaSubProject}.orm;


import java.util.Date;
import java.util.List;
import java.io.Serializable;
import com.${vars.company}.${vars.project}${vars.javaSubProject}.orm.Domain;

<#include "javadoc-file.ftl">
public interface GenericDao<T extends Domain, ID extends Serializable> {
	void save(T arg0);

	int update(T arg0);

	void delete(ID arg0);

	void deleteAll(List<ID> arg0);

}