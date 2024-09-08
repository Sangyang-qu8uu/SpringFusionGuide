package org.example.mockito;

import org.junit.Before;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MockitoApplicationTests {


    @Before
    public void init() {
        // 手动初始化 Mockito
        MockitoAnnotations.openMocks(this);
    }

}
