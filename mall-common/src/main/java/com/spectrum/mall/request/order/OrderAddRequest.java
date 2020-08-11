package com.spectrum.mall.request.order;

import com.spectrum.mall.entity.RpcBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author oe_qinzuopu
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "UserAddRequest", description = "用户新增实例")
public class OrderAddRequest extends RpcBean {

    private static final long serialVersionUID = 1L;

    /**
     * 身份证号
     */
    @ApiModelProperty(value = "证件号")
    private String idNo;

    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    private String userName;
}
