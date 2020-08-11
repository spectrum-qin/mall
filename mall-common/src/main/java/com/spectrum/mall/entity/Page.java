//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.spectrum.mall.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author oe_qinzuopu
 */
@ApiModel(
        value = "Page",
        description = "返回分页数据类型"
)
@Data
@EqualsAndHashCode
@ToString
public class Page implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("当前页码")
    private int current;
    @ApiModelProperty("每页条数")
    private int pageSize;
    @ApiModelProperty("总条数")
    private long total;
    @ApiModelProperty("总页数")
    private long pages;

    public Page() {
    }

    public Page(int current, int pageSize) {
        this.current = current;
        this.pageSize = pageSize;
    }

    public Page(int current, int pageSize, long total, long pages) {
        this.current = current;
        this.pageSize = pageSize;
        this.pages = pages;
        this.total = total;
    }
}
