package org.example.mockito.service.impl;

import org.example.mockito.pojo.UserInfo;
import org.example.mockito.service.UserInfoService;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

/**
 * @Description
 * @Author SangY
 * @Date 2024/9/8 19:58
 **/
@Service
public class UserInfoServiceImpl implements UserInfoService {


    @Override
    public UserInfo getUserInfo(String id) {
        //只编写了相当于Impl的这部分代码，方便mockito单测使用
        if (Objects.nonNull(id)) {
            UserInfo userInfo = new UserInfo();
            userInfo.setId(id);
            userInfo.setName("张三");
            userInfo.setAge(18);
            userInfo.setPhone("13723458888");
            return userInfo;
        } else {
            return new UserInfo();
        }

    }

    @Override
    public Integer addUserInfo(UserInfo userInfo) {
        userInfo.setId(UUID.randomUUID().toString());
        userInfo.setName("李四");
        userInfo.setAge(25);
        userInfo.setPhone("18888888888");
        return Integer.parseInt(userInfo.getId());
    }
}
