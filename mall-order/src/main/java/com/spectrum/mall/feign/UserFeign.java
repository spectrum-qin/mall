package com.spectrum.mall.feign;

import com.spectrum.mall.entity.DataRequest;
import com.spectrum.mall.entity.DataResponse;
import com.spectrum.mall.request.user.UserAddRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "mall-user", fallback = UserFeignFallback.class)
@Component
public interface UserFeign {

    @PostMapping(value = "/api/custom/manage/addCustManage")
    DataResponse<?> userAdd(@RequestBody DataRequest<UserAddRequest> userAddRequest);
}

/**
 * @author oe_qinzuopu
 */
@Slf4j
@Component
class UserFeignFallback implements UserFeign{

    @Override
    public DataResponse<?> userAdd(DataRequest<UserAddRequest> userAddRequest) {
        log.info("fallback降级处理");
        return DataResponse.failed();
    }
}
