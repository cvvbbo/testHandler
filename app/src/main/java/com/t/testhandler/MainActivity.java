package com.t.testhandler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Handler mhandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tv);
        testHandler3();

    }

    // 在子线程中使用handler改变ui的方法。
    // 关键在于looper.prepare,looper.prepare,
    // 以及handler中的looper.getmainlooper 方法。
    public void testHandler() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    Looper.prepare();
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText("99999");
                        }
                    });

                    Looper.loop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    // 在子线程中创建handler，然后重写handlermessage
    public void testHandler1() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                mhandler = new Handler() {
                    @Override
                    public void handleMessage(@NonNull Message msg) {
                        super.handleMessage(msg);
                        switch (msg.what) {
                            case 1:
                                Log.e("111", "to do");
                                break;
                        }
                    }
                };
                Looper.loop();
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    Message message = Message.obtain();
                    message.what = 1;
                    mhandler.sendMessage(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    public void testHandler3() {
        // 创建一个普通handler
        final Handler mainhandler = new Handler();
        // 创建一个handlerthread
        final HandlerThread mhandlerThread = new HandlerThread("mythread");
        mhandlerThread.start();
        new Thread(new Runnable() {
            @Override
            public void run() {

                Looper.prepare();
                 mhandler = new Handler(mhandlerThread.getLooper()) {
                    @Override
                    public void handleMessage(@NonNull Message msg) {
                        super.handleMessage(msg);
                        switch (msg.what) {
                            case 1:
                                // 在handlerthread里面依旧不能进行改变ui的操作!
                                // 如果想要改变ui还是要使用looper.getMainLooper方法,
                                // 但是如果使用了这个方法，基本和handlerthread无关了

                                try {
                                    Thread.sleep(4000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                               // textView.setText("66663");
                                Log.e("22211", "to do");

                                break;
                        }
                    }
                };
                Looper.loop();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Message message = Message.obtain();
                message.what = 1;
                mhandler.sendMessage(message);

            }
        }).start();

    }
}
