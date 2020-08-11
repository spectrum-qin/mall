/**  
 * @author: qinzp
 * @date: 2020-08-11 14:28:33  
 */
package com.spectrum.mall.domain;

import com.spectrum.mall.po.UserPo;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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
public class User extends UserPo  {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * toString()
     * 
     * @return
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
	 
	
}