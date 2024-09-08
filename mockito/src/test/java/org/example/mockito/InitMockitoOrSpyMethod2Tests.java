package org.example.mockito;

import org.example.mockito.service.UserInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class InitMockitoOrSpyMethod2Tests {

    private UserInfoService mockUserInfoService;

    private UserInfoService spyUserInfoService;


    @BeforeEach
    public void init() {
        mockUserInfoService = Mockito.mock(UserInfoService.class);
        spyUserInfoService = Mockito.spy(UserInfoService.class);
    }

    @Test
    public void test1() {
        //true 判断mockUserInfoService是否是Mock对象
        System.out.println("Mockito.mockingDetails(mockUserInfoService).isMock() ="+Mockito.mockingDetails(mockUserInfoService).isMock());
        //false 判断mockUserInfoService是否是Spy对象
        System.out.println("Mockito.mockingDetails(mockUserInfoService).isSpy() ="+Mockito.mockingDetails(mockUserInfoService).isSpy());
        //true 判断spyUserInfoService是否是Spy对象
        System.out.println("Mockito.mockingDetails(spyUserInfoService).isMock() ="+Mockito.mockingDetails(spyUserInfoService).isMock());
        //true 判断spyUserInfoService是否是Spy对象
        System.out.println("Mockito.mockingDetails(spyUserInfoService).isSpy() ="+Mockito.mockingDetails(spyUserInfoService).isSpy());

    }
}
