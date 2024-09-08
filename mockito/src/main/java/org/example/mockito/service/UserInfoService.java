package org.example.mockito.service;

import org.example.mockito.pojo.UserInfo;

/**
 * The interface User info service.
 *
 * @Description
 * @Author SangY
 * @Date 2024 /9/8 19:58
 */
public interface UserInfoService {

    /**
     * Gets user info.
     *
     * @param id the id
     * @return the user info
     */
    public UserInfo getUserInfo(String id);

    /**
     * Add user info
     *
     * @param userInfo the user info
     * @return the boolean
     */
    public Integer addUserInfo(UserInfo userInfo);
}
