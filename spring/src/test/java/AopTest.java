
import com.example.aop.TargetInterface;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml") // 这里加载的是 xml 配置文件！
public class AopTest {

    // 注入目标接口（Spring 会自动把生成的代理对象注入进来）
    @Autowired
    private TargetInterface target;

    @Test
    public void testAop() {
        target.save(); // 调用的是代理对象的方法
    }
}
