import com.example.DclSingle;
import com.example.EagerSingle;
import org.junit.Test;

/**
 * Copyright (c) 2025 Your Company, Inc. All rights reserved.
 *
 * @author sang
 * @version 1.0
 * @description singleTest
 * @since JDK 11
 */
public class SingleTest {


    @Test
    public void test() {
        EagerSingle eagerSingle = EagerSingle.getSingleTon();
        EagerSingle eagerSingle2 = EagerSingle.getSingleTon();
        System.out.println(eagerSingle.equals(eagerSingle2));
        System.out.println(eagerSingle == eagerSingle2);


        DclSingle singleTon3 = DclSingle.getSingleTon();
        DclSingle singleTon4 = DclSingle.getSingleTon();
        System.out.println(singleTon3.equals(singleTon4));
        System.out.println(singleTon3==singleTon4);
    }
}
