<#import "function.ftl" as func>
<#assign class=model.variables.class>
<#assign module=vars.module>
<#assign parentDir=vars.parentDir>
<#assign type=parentDir+"."+module+".domain."+class>
<#assign tableName=model.tableName>
<#assign colList=model.columnList>
<#assign commonList=model.commonList>
<#assign pk=func.getPk(model) >
<#assign pkType=func.getPkType(model) >
<#assign pkVar=func.getPkVar(model) >

<#-- mpper_xml -->
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd"> 

<mapper namespace="${vars.parentDir}.${vars.module}.mapper.${class}Mapper">

	<resultMap id="Result" type="${type}">
		<#list colList as col>
			<#if (col.isPK) >
				<id property="${col.humpColumnName?uncap_first}" column="${col.columnName}" />
			</#if>
		</#list>
		<#list colList as col>
			<#if (!col.isPK) >
				<result property="${col.humpColumnName?uncap_first}" column="${col.columnName}" />
			</#if>
		</#list>
	</resultMap>
	
	
	<sql id="table">
		${tableName}
	</sql>
	
	<sql id="demoSet">
	  <set>
	  <#list commonList as col>
		<#if (!col.isPK && (col.columnName)??) >
	    ${col.columnName}=<#noparse>#{</#noparse>${col.humpColumnName?uncap_first}}<#if col_has_next>, </#if>
		</#if>
	  </#list>
	  </set>
	</sql>
	
	<sql id="columns">
		<#list colList as col>${col.columnName}<#if col_has_next>,</#if></#list>
	</sql>

   <!-- 单条插入记录  -->
	<insert id="add" parameterType="${type}">
	   <!--
	     <selectKey keyProperty="id" resultType="long">
            select LAST_INSERT_ID()
        </selectKey>  -->
		INSERT INTO <include refid="table" />
		(<include refid="columns"/>)
		VALUES
		(<#list colList as col><#if (col.columnName?upper_case=="DEL_FLAG")> '0' <#else><#noparse>#{</#noparse>${col.humpColumnName?uncap_first}<#noparse>}</#noparse></#if><#if col_has_next>, </#if></#list>)
	</insert>
	
	<!-- 批量插入  -->
	<insert id="addBatch" parameterType="java.util.List">
        INSERT INTO    <include refid="table" />
		   ( <include refid="columns" />)
		VALUES
        <foreach collection="list"  item="item" separator="," index="index">
           (<#list colList as col><#if (col.columnName?upper_case=="DEL_FLAG")> '0' <#else><#noparse>#{</#noparse>item.${col.humpColumnName?uncap_first}<#noparse>}</#noparse></#if><#if col_has_next>, </#if></#list>)
        </foreach>
    </insert>
	
	<!-- 根据主键ID进行物理删除  -->
	<delete id="deleteById" parameterType="${pkType}">
		DELETE FROM  <include refid="table" />
		WHERE ${pk}=<#noparse>#{</#noparse>${pk?lower_case}<#noparse>}</#noparse>
	</delete>
	
	<!-- 根据主键ID进行逻辑删除  -->
	<delete id="delLogicById" parameterType="${pkType}">
		UPDATE   <include refid="table" />
		SET DEL_FLAG='1'
		WHERE ${pk}=<#noparse>#{</#noparse>${pk?lower_case}<#noparse>}</#noparse>
	</delete>
	
	<!-- 批量物理删除  -->
	<delete id="deleteBatchIds"  parameterType = "java.util.List">
       DELETE FROM <include refid="table" />
       WHERE id IN
       <foreach collection="list"  item="item" open="(" separator="," close=")"  >
       <#noparse>#{</#noparse>item<#noparse>}</#noparse>
       </foreach>
   </delete>

	<!-- 根据ID修改  -->
	<update id="updateById" parameterType="${type}">
		UPDATE  <include refid="table" />
		<set>
		    update_time=<#noparse>#{</#noparse> updateTime<#noparse>}</#noparse> 
			<if test="@Ognl.Ognl@isNotEmpty(updateUser)"> update_user=<#noparse>#{</#noparse> updateUser<#noparse>}</#noparse>, </if>
			 
		</set>
		WHERE ${pk}=<#noparse>#{</#noparse>${pk?lower_case}}
	</update>
		
    <!-- 根据ID查询单笔记录  -->
	<select id="findOneById" parameterType="${pkType}" resultMap="Result">
		SELECT <include refid="columns"/>
		FROM  <include refid="table" />
		WHERE ${pk}=<#noparse>#{</#noparse>${pk?lower_case}}
	</select>
	
	<!-- 根据ID列表 查询相关记录  -->
	<select id="findAll" parameterType="${pkType}" resultMap="Result">
		SELECT <include refid="columns"/>
		FROM  <include refid="table" />
		<if test="@Ognl.Ognl@isNotEmpty(list)">
		WHERE ${pk} IN
		<foreach item="item" index="index" collection="list" open="(" separator="," close=")">  
				<#noparse>#{</#noparse>item}
		</foreach>
		</if>
	</select>
	
	<select id="find" parameterType="${type}" resultMap="Result">
		SELECT <include refid="columns"/>
		FROM  <include refid="table" />
		WHERE DEL_FLAG='0'
	</select>
</mapper>
