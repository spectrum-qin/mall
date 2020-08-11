package com.spectrum.mall.service.impl;

import com.spectrum.mall.common.ServiceException;
import com.spectrum.mall.common.user.UserExceptionCode;
import com.spectrum.mall.domain.User;
import com.spectrum.mall.mapper.UserMapper;
import com.spectrum.mall.mybatis.service.AbstractService;
import com.spectrum.mall.service.UserService;
import com.spectrum.mall.utils.text.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author oe_qinzuopu
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public void addCustManage(User user) {
        if (StringUtils.isEmpty(user)) {
            throw new ServiceException(UserExceptionCode.USER_UPDATE);
        }
        userMapper.insert(user);
    }
}
