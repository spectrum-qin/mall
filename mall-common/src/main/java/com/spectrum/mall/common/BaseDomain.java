//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.spectrum.mall.common;

import com.spectrum.mall.utils.time.DateUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author oe_qinzuopu
 */
@Data
@EqualsAndHashCode
@ToString
@ApiModel
public abstract class BaseDomain<Pk extends Serializable> implements Domain {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("id")
    Pk id;
    @ApiModelProperty(
            value = "创建人",
            accessMode =ApiModelProperty.AccessMode.READ_ONLY
    )
    String createUser;
    @ApiModelProperty(
            value = "修改人",
            accessMode =ApiModelProperty.AccessMode.READ_ONLY
    )
    String updateUser;
    @ApiModelProperty(
            value = "创建时间",
            accessMode =ApiModelProperty.AccessMode.READ_ONLY
    )
    Date createTime;
    @ApiModelProperty(
            value = "修改时间",
            accessMode =ApiModelProperty.AccessMode.READ_ONLY
    )
    Date updateTime;
    @ApiModelProperty("删除标志")
    String delFlag;

    public BaseDomain() {
    }

    public BaseDomain(Pk id) {
        this.createTime = DateUtils.getCurTimestamp();
        this.updateTime = DateUtils.getCurTimestamp();
        this.delFlag = "0";
    }

    public BaseDomain(Pk id, String createUser, String updateUser) {
        this.createTime = DateUtils.getCurTimestamp();
        this.updateTime = DateUtils.getCurTimestamp();
        this.createUser = createUser;
        this.updateUser = updateUser;
        this.delFlag = "0";
    }
}
