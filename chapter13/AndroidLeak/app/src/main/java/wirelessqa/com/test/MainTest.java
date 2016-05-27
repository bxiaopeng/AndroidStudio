package wirelessqa.com.test;

/**
 * Created by bixiaopeng on 15/6/18.
 */
public class MainTest {

    TestA testA = new TestA();
    TestB testB = new TestB();

    public static void initMethod(TestA testA, TestB testB){
        System.out.println("testA = " + testA);
        System.out.println("testB = " + testB);
    }

    public void test(){
        MainTest.initMethod(testA, testB);
    }
}
