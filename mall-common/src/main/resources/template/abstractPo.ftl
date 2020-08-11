<#include "javadoc-copyright.ftl">
<#import "function.ftl" as func>
<#assign class=model.variables.class>
package com.${vars.company}.${vars.project}${vars.javaSubProject}.orm;

import java.util.Date;
import com.${vars.company}.${vars.project}${vars.javaSubProject}.orm.Domain;

<#include "javadoc-file.ftl">
public class AbstractPo implements Domain {

	private static final long serialVersionUID = 1L;

	private Long id;

	private Date createTime;

	private Date updateTime;

	
	public AbstractPo(Integer id) {
		super();
		this.id = id;
	}

	public AbstractPo() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	