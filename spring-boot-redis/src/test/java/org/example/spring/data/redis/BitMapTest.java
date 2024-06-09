package org.example.spring.data.redis;

import jakarta.annotation.Resource;
import org.example.spring.data.redis.component.BitmapService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Bitmap相关测试
 *
 * @Description
 * @Author SangY
 * @Date 2024/6/7 13:08
 **/
@SpringBootTest
public class BitMapTest {

    @Resource
    private BitmapService bitmapService;

    private final String date = "2024-06-01";

    @BeforeEach
    public void setUp() {
        // 清除之前的测试数据
        bitmapService.setUserActive("1", date);
        bitmapService.setUserActive("2", date);
        bitmapService.setUserActive("3", date);
    }

    //testSetUserActive：测试setUserActive方法，验证用户的活跃状态是否正确设置。
    @Test
    public void testSetUserActive() {
        bitmapService.setUserActive("100", date);
        assertTrue(bitmapService.isUserActive("100", date));
    }

    //测试isUserActive方法，验证某用户在某天是否活跃。
    @Test
    public void testIsUserActive() {
        bitmapService.setUserActive("101", date);
        assertTrue(bitmapService.isUserActive("101", date));
        assertFalse(bitmapService.isUserActive("102", date));
    }

    //测试countActiveUsers方法，验证统计活跃用户数是否正确。
    @Test
    public void testCountActiveUsers() {
        bitmapService.setUserActive("200", date);
        bitmapService.setUserActive("201", date);
        bitmapService.setUserActive("202", date);
        long activeUsers = bitmapService.countActiveUsers(date);
        assertEquals(3, activeUsers);
    }

    //验证批量设置用户活跃状态是否正确。
    @Test
    public void testSetUsersActive() {
        String[] userIds = {"301", "302", "303"};
        bitmapService.setUsersActive(date, userIds);
        for (String userId : userIds) {
            assertTrue(bitmapService.isUserActive(userId, date));
        }
    }

    //验证批量检查用户活跃状态是否正确
    @Test
    public void testAreUsersActive() {
        String[] userIds = {"401", "402", "403"};
        bitmapService.setUsersActive(date, userIds);
        boolean[] activeStatus = bitmapService.areUsersActive(date, userIds);
        for (boolean status : activeStatus) {
            assertTrue(status);
        }
    }
}
