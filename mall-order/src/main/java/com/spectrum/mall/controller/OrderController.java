package com.spectrum.mall.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.spectrum.mall.entity.DataRequest;
import com.spectrum.mall.entity.DataResponse;
import com.spectrum.mall.feign.UserFeign;
import com.spectrum.mall.request.order.OrderAddRequest;
import com.spectrum.mall.request.user.UserAddRequest;
import com.spectrum.mall.response.order.OrderUserAddResponse;
import com.spectrum.mall.response.user.UserAddResponse;
import com.spectrum.mall.service.OrderService;
import com.spectrum.mall.sync.produce.send.MessageSend;
import com.spectrum.mall.utils.bean.BeanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author oe_qinzuopu
 */
@Slf4j
@Api(tags = "客户管理")
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserFeign userFeign;

    @Autowired
    private MessageSend messageSend;

//    @HystrixCommand(commandKey = "orderAdd", fallbackMethod = "userAddFallback")
    @RequestMapping(value = "/api/order/add", method = RequestMethod.POST)
    @ApiOperation(value = "新增客户详情", notes = "新增客户详情")
    public DataResponse<OrderUserAddResponse> orderAdd(@RequestBody @ApiParam(name = "data", value = "新增客户请求实体",
            required = true) @Validated DataRequest<OrderAddRequest> data) {
        OrderAddRequest orderAddRequest = data.getData();
        UserAddRequest userAddRequest = new UserAddRequest();
        DataRequest<UserAddRequest> dataRequest = new DataRequest<UserAddRequest>(userAddRequest, data.getFlowId());
        userAddRequest.setName(orderAddRequest.getUserName());
        dataRequest.setSysId("11");
        long t2 = System.currentTimeMillis();
        DataResponse<UserAddResponse> response = userFeign.userAdd(dataRequest);
        messageSend.msgSend("执行完成");
        log.info( "执行时间:" + (System.currentTimeMillis() - t2 + "ms"));
//        if (response) {}
        UserAddResponse userAddResponse = response.getData();
        OrderUserAddResponse orderUserAddResponse = BeanUtils.copyProperties(OrderUserAddResponse.class, userAddResponse);
        return DataResponse.succeed(orderUserAddResponse);
    }

    public DataResponse<OrderUserAddResponse> userAddFallback(DataRequest<UserAddRequest> userAddRequest) {
        log.info("fallback降级处理");
        return DataResponse.failed();
    }

}
