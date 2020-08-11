package com.spectrum.mall.domain;

import com.spectrum.mall.common.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author oe_qinzuopu
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Order extends BaseDomain<String> {

    private String orderSeq;
}
