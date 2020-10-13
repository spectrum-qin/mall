package com.spectrum.mall.response.order;

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
@ApiModel(value = "OrderUserAddResponse", description = "用户新增实例")
public class OrderUserAddResponse extends RpcBean {

    /**
     * 注册id
     */
    @ApiModelProperty(value = "注册id")
    private String id;
}
