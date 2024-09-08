package org.example.mockito;

import org.example.mockito.pojo.UserInfo;
import org.example.mockito.service.UserInfoService;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

/**
 * @Description 参数匹配：通过方法签名（参数）来指定哪些方法调用需要被处理
 * @Author SangY
 * @Date 2024/9/8 21:55
 **/
@ExtendWith(MockitoExtension.class)
public class ParamMatherTests {
    @Mock
    private UserInfoService mockUserInfoService;

    @Spy
    private UserInfoService spyUserInfoService;

    /**
     * 对于mock对象不会调用真实方法，直接返回mock对象的默认值：
     * 默认值(int)、nulL(UserVo)、空集合(List)
     */
    @Test
    public void test1() {
        //null
        UserInfo userInfo = mockUserInfoService.getUserInfo("1");
        System.out.println(userInfo);

        //0
        UserInfo userInfo1 = new UserInfo();
        Integer s = mockUserInfoService.addUserInfo(userInfo1);
        System.out.println(s);
    }

    /**
     * 测试插桩时候参数匹配
     */
    @Test
    public void test2() {
        //构造插桩参数
        UserInfo userInfo1 = new UserInfo();
        userInfo1.setId(UUID.randomUUID().toString());
        userInfo1.setAge(34);
        userInfo1.setName("王大锤");
        userInfo1.setPhone("18888888888");

        //指定返回
        Mockito.doReturn(userInfo1).when(mockUserInfoService).getUserInfo("1");

        //断言判断
        Assertions.assertEquals(userInfo1, mockUserInfoService.getUserInfo("1"));

//        //构造插桩参数
//        UserInfo userInfo2 = new UserInfo();
//        userInfo2.setId("2");
//        userInfo2.setAge(34);
//        userInfo2.setName("王大锤");
//        userInfo2.setPhone("18888888888");
//        //断言判断
//        Expected :UserInfo{id='2', name='王大锤', age=34, phone='18888888888'}
//        Actual   :null
//        Assertions.assertEquals(userInfo2, mockUserInfoService.getUserInfo("2"));
    }

    /**
     * 拦截任意类型参数
     */
    @Test
    public void test3() {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(UUID.randomUUID().toString());
        userInfo.setAge(34);
        userInfo.setName("王大锤");
        userInfo.setPhone("18888888888");
        Mockito.doReturn(userInfo).when(mockUserInfoService).getUserInfo(ArgumentMatchers.any(String.class));

        UserInfo userInfo1 = mockUserInfoService.getUserInfo("1");
        UserInfo userInfo2 = mockUserInfoService.getUserInfo("1");

        Assertions.assertEquals(userInfo1, userInfo2);
    }

    /**
     * ArgumentMatchers拦截任意UserInfo对象
     * 除了any,还有anyXXX（anyString。anyInt，anyLong....），注意他们不包括null
     */
    @Test
    public void test4() {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(UUID.randomUUID().toString());
        userInfo.setAge(34);
        userInfo.setName("王大锤");
        userInfo.setPhone("18888888888");
        mockUserInfoService.addUserInfo(userInfo);
        Mockito.verify(mockUserInfoService, Mockito.times(1)).addUserInfo(userInfo);

        //
        Mockito.verify(mockUserInfoService, Mockito.times(1)).addUserInfo(ArgumentMatchers.any(UserInfo.class));

        //Wanted 2 times:
        //-> at org.example.mockito.ParamMatherTests.test4(ParamMatherTests.java:105)
        //But was 1 time:
        //-> at org.example.mockito.ParamMatherTests.test4(ParamMatherTests.java:104)
        //Mockito.verify(mockUserInfoService,Mockito.times(2)).addUserInfo(userInfo);

        mockUserInfoService.getUserInfo("1");
        Mockito.verify(mockUserInfoService, Mockito.times(1)).getUserInfo("1");


        Mockito.verify(mockUserInfoService, Mockito.times(1)).getUserInfo(ArgumentMatchers.any(String.class));
        Mockito.verify(mockUserInfoService, Mockito.times(1)).getUserInfo(ArgumentMatchers.anyString());
    }
}
