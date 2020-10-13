package com.spectrum.mall.service.impl;

import com.spectrum.mall.common.ServiceException;
import com.spectrum.mall.common.user.UserExceptionCode;
import com.spectrum.mall.domain.User;
import com.spectrum.mall.mapper.UserMapper;
import com.spectrum.mall.service.UserService;
import com.spectrum.mall.utils.text.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author oe_qinzuopu
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public void addCustManage(User user) {
        long t2 = System.currentTimeMillis();
        if (StringUtils.isEmpty(user)) {
            throw new ServiceException(UserExceptionCode.USER_UPDATE);
        }
//        try {
//            TimeUnit.SECONDS.sleep(8);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        userMapper.insert(user);
        log.info( "执行时间:" + (System.currentTimeMillis() - t2 + "ms"));
    }
}
