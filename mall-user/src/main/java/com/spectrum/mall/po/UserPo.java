/**  
 * @author: qinzp
 * @date: 2020-08-11 14:28:33  
 */
package com.spectrum.mall.po;

import com.spectrum.mall.common.BaseDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * TODO
 *
 * @author qinzp
 * @date: 2020-08-11 14:28:33
 * @version: V1.0
 * @review: qinzp/2020-08-11 14:28:33
 */

@ApiModel
@Data
@EqualsAndHashCode(callSuper=true)
@Accessors(chain = true)
public class UserPo extends BaseDomain<String>{

    private static final long serialVersionUID = 1L;

	
	 /**
	 * name
	 */
	 @ApiModelProperty(value = "name")
	 private	String	name;

}