//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.spectrum.mall.entity;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;

/**
 * @author oe_qinzuopu
 */
@ApiModel(
        value = "RpcBean",
        description = "接口返回数据类型"
)
public class RpcBean implements Serializable {
    private static final long serialVersionUID = -5308408847182503821L;

    public RpcBean() {
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof RpcBean)) {
            return false;
        } else {
            RpcBean other = (RpcBean)o;
            return other.canEqual(this);
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof RpcBean;
    }

    @Override
    public int hashCode() {
        int result = 1;
        return result;
    }
    @Override
    public String toString() {
        return "RpcBean()";
    }
}
