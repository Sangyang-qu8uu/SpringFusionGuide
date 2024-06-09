package org.example.spring.data.redis.user.service;


import jakarta.annotation.Resource;
import org.example.spring.data.redis.utils.RedisUtils;
import org.example.spring.data.redis.user.domian.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    private static final String USER_KEY_PREFIX = "User:";

    @Resource
    private RedisUtils redisUtils;

    public void saveUser(User user) {
        redisUtils.setCacheObject(USER_KEY_PREFIX + user.getId(), user);
    }

    public void saveUserWithExpiration(User user, Long timeout, TimeUnit timeUnit) {
        redisUtils.setCacheObject(USER_KEY_PREFIX + user.getId(), user, timeout, timeUnit);
    }

    public User getUserById(String id) {
        return redisUtils.getCacheObject(USER_KEY_PREFIX + id);
    }

    public void deleteUserById(String id) {
        redisUtils.deleteObject(USER_KEY_PREFIX + id);
    }

    public <T> void updateUser(User user) {
        redisUtils.setCacheObject(USER_KEY_PREFIX + user.getId(), user);
    }

    public List<User> getAllUsers() {
        // 这里假设用户ID是整数并且我们通过范围查询所有用户
        // 可以根据实际需求调整实现方式
        return redisUtils.getCacheList(USER_KEY_PREFIX + "*");
    }
}
