package com.spectrum.mall.service;

import com.spectrum.mall.domain.User;
import org.springframework.stereotype.Service;

/**
 * @author oe_qinzuopu
 */
@Service
public interface UserService {

    /**
     * @Description：<123>
     * @param user
     * @author qinzp
     * @creed: Talk is cheap,show me the code
     * @return
     * @date 2020/8/11 15:35
     **/
    public void addCustManage(User user);
}
