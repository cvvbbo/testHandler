package com.t.testhandler;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Bactivity extends AppCompatActivity {


    private Handler wordhandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_view);
        haha();
    }


    public void haha() {
        HandlerThread mhandlerThread = new HandlerThread("mythread");
        mhandlerThread.start();
        wordhandler = new Handler(mhandlerThread.getLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

               if(Looper.getMainLooper() == Looper.myLooper()) {
                   Log.e("haha","main");
               }
            }
        };

        wordhandler.sendEmptyMessage(0);

    }
}
