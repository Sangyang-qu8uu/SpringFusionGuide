import com.google.common.base.Stopwatch;
import org.apache.logging.log4j.util.Strings;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Description 对比两种List容器的add方法的性能
 * @Author SangY
 * @Date 2024/7/19 9:30
 **/
public class ArrayListVSLinkedList {
    private final static String DATA = "DUMMY DATA";
    private final static int MAX_CAPACITY = 1_000_000;
    private final static int MAX_ITERATIONS = 10;
    private static void test(List<String> list)
    {
        for (int i = 0; i < MAX_CAPACITY; i++)
        {
            list.add(DATA);
        }
    }

    private static void arrayListPerfTest()
    {
        for (int i = 0; i < ArrayListVSLinkedList.MAX_ITERATIONS; i++)
        {
            final List<String> list = new ArrayList<>();
            final Stopwatch stopwatch = Stopwatch.createStarted();
            test(list);
            System.out.println(stopwatch.stop()
                    .elapsed(TimeUnit.MILLISECONDS));
        }
    }

    private static void linkedListPerfTest()
    {
        for (int i = 0; i < ArrayListVSLinkedList.MAX_ITERATIONS; i++)
        {
            final List<String> list = new LinkedList<>();
            final Stopwatch stopwatch = Stopwatch.createStarted();
            test(list);
            System.out.println(stopwatch.stop()
                    .elapsed(TimeUnit.MILLISECONDS));
        }
    }

    public static void main(String[] args)
    {
        arrayListPerfTest();
        System.out.println(Strings.repeat("#", 100));
        linkedListPerfTest();

    }
}
