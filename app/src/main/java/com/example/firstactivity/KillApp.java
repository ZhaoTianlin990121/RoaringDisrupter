package com.example.firstactivity;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class KillApp extends Service {

    private Runnable runnable;
    private Handler handler;
    private int Time = 1000 * 3;//周期时间
    private int anHour = 8 * 60 * 60 * 1000;// 这是8小时的毫秒数 为了少消耗流量和电量，8小时自动更新一次
    private Timer timer = new Timer();
    ArrayList<String> blackList = null;

    public KillApp() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    public void onCreate() {
        super.onCreate();
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                Context context = KillApp.this;
                ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
                List<ActivityManager.RunningAppProcessInfo> infos = am.getRunningAppProcesses();
                Log.e("MyServive", "app stopped1");

                for (ActivityManager.RunningAppProcessInfo info : infos) {
                    for (String elementOfBlackList:blackList) {
                       // Log.e("MyServive",info.processName);
                        if (info.processName.equals(elementOfBlackList)) {
                            Log.e("MyServive", "app stopped2");
                            am.killBackgroundProcesses(info.processName);
                            //android.os.Process.killProcess(info.pid);
//                            if(info.pid == android.os.Process.myPid()){
//                                Log.e("MyServive", "app stopped3");
//                                android.os.Process.killProcess(android.os.Process.myPid());
//                                Log.e("MyServive", "app stopped4");
//                            }
                        }
                    }
                }
                // handler自带方法实现定时器
                handler.postDelayed(this, 1000 * 3);//每隔3s执行
            }
        };
        handler.postDelayed(runnable,1000*5);//延时多长时间启动定时器
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Log.e("MyServive", "init");
        blackList = intent.getStringArrayListExtra("blacked");
        return super.onStartCommand(intent,flags,startId);
    }
}
