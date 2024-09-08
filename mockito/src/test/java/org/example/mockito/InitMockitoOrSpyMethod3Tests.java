package org.example.mockito;

import org.example.mockito.service.UserInfoService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

class InitMockitoOrSpyMethod3Tests {
    @Mock
    private UserInfoService mockUserInfoService;
    @Spy
    private UserInfoService spyUserInfoService;

    private  AutoCloseable autoCloseable;

    @BeforeEach
    public void init() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }
    @AfterEach
    public void destroy() throws Exception {
        if (autoCloseable != null) {
            autoCloseable.close();  // 关闭 mock
        }
    }


    @Test
    public void test1() {
        //true 判断mockUserInfoService是否是Mock对象
        System.out.println("Mockito.mockingDetails(mockUserInfoService).isMock() =" + Mockito.mockingDetails(mockUserInfoService).isMock());
        //false 判断mockUserInfoService是否是Spy对象
        System.out.println("Mockito.mockingDetails(mockUserInfoService).isSpy() =" + Mockito.mockingDetails(mockUserInfoService).isSpy());
        //true 判断spyUserInfoService是否是Spy对象
        System.out.println("Mockito.mockingDetails(spyUserInfoService).isMock() =" + Mockito.mockingDetails(spyUserInfoService).isMock());
        //true 判断spyUserInfoService是否是Spy对象
        System.out.println("Mockito.mockingDetails(spyUserInfoService).isSpy() =" + Mockito.mockingDetails(spyUserInfoService).isSpy());

    }
}
