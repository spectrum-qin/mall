package com.spectrum.mall.controller;

import com.spectrum.mall.domain.User;
import com.spectrum.mall.entity.DataRequest;
import com.spectrum.mall.entity.DataResponse;
import com.spectrum.mall.request.user.UserAddRequest;
import com.spectrum.mall.response.user.UserAddResponse;
import com.spectrum.mall.service.UserService;
import com.spectrum.mall.service.impl.UserServiceImpl;
import com.spectrum.mall.utils.bean.BeanUtils;
import com.spectrum.mall.utils.json.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author oe_qinzuopu
 */
@Slf4j
@Api(tags = "客户管理")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/api/custom/manage/addCustManage",method = RequestMethod.POST)
    @ApiOperation(value = "新增客户详情", notes = "新增客户详情")
    @ResponseBody
    public DataResponse<UserAddResponse> userAdd(@RequestBody @ApiParam(name = "data", value = "新增客户请求实体",
            required = true) @Validated DataRequest<UserAddRequest> data) {
        UserAddRequest userAddRequest = data.getData();
        User user1 = BeanUtils.copyProperties(User.class, userAddRequest);
        userService.addCustManage(user1);
        UserAddResponse response = new UserAddResponse();
        response.setId(user1.getId());
        return DataResponse.succeed(response);
    }
}
