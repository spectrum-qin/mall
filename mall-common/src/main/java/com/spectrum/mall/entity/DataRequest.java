package com.spectrum.mall.entity;

import com.spectrum.mall.utils.time.DateUtils;
import com.spectrum.mall.utils.text.UuidUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @author oe_qinzuopu
 */
@ApiModel(
        value = "DataRequest",
        description = "请求数据类型"
)
@Data
@EqualsAndHashCode
@ToString
public class DataRequest<T> implements Serializable{
    private static final long serialVersionUID = 1L;
    @Valid
    @ApiModelProperty("业务参数对象")
    private T data;
    @ApiModelProperty("页码")
    private Integer current;
    @ApiModelProperty("每页条数")
    private Integer pageSize;
    @ApiModelProperty("请求唯一流水号")
    private String flowId;
    @ApiModelProperty("版本号")
    private String version;
    @ApiModelProperty("交易码")
    private String apiCd;
    @NotBlank(
            message = "系统标识sysId"
    )
    @ApiModelProperty("系统标识")
    private String sysId;
    @ApiModelProperty("时间戳,yyyyMMddHHmmss格式")
    private String timestamp;
    @ApiModelProperty("渠道编号")
    private String channelNo;

    public DataRequest() {
        this.flowId = UuidUtils.getUuid();
        this.version = "1.0.0";
    }

    public DataRequest(T data) {
        this.version = "1.0.0";
        this.flowId = UuidUtils.getUuid();
        this.timestamp = DateUtils.getCurDT() + DateUtils.getCurTM();
        this.data = data;
    }

    public DataRequest(T data, String flowId) {
        this(data);
        this.version = "1.0.0";
        this.flowId = flowId;
        this.timestamp = DateUtils.getCurDT() + DateUtils.getCurTM();
    }

    public DataRequest(T data, Integer current, Integer pageSize) {
        this(data);
        this.current = current;
        this.pageSize = pageSize;
    }

}
