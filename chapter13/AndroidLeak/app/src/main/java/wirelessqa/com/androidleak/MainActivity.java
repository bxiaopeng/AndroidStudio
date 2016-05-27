package wirelessqa.com.androidleak;

import android.app.Activity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * 这个例子中存在内存泄漏.
 * 在Activity中写一些内部类，并且这些内部类具有生命周期过长的现象.
 */
public class MainActivity extends Activity {

    private List<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //模拟Activity一些其他的对象
        for(int i=0; i<10000;i++){
            list.add("漏了漏了");
        }

        //开启线程
        new MyTestThread().start();
    }

    public class MyTestThread extends Thread{

        @Override
        public void run() {
            super.run();

            //模拟耗时操作
            try {
                Thread.sleep(10 * 60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


    }

}
