package org.example.mockito;

import org.example.mockito.service.UserInfoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class InitMockitoOrSpyMethod1Tests {

    @Mock
    private UserInfoService mockUserInfoService;

    @Spy
    private UserInfoService spyUserInfoService;

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
