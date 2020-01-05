package work;


import java.util.concurrent.Executors;

public class YBThread {
    public void startYBThread(){
        Executors.newCachedThreadPool().submit(new Runnable() {
            @Override
            public void run() {
                //异步线程执的代码逻辑
            }
        });
    }
}
