package com.example.firstactivity;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.AlarmClock;
import android.provider.MediaStore;
import android.util.Log;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.VIBRATOR_SERVICE;

class NewAppKill extends AsyncTask<Void,Integer,Boolean> {
    static ArrayList<String> blackList = BlackList.blackList;
    static boolean judge = BlackList.judge;
    Context context;

    public NewAppKill(Context context){
        this.context=context;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try{
            Class.forName("com.example.firstactivity.BlackList");
        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(BlackList.judge);
        if (!BlackList.judge){
            this.cancel(true);
        }
        if (isCancelled()) return null;
        while (BlackList.judge) {
            boolean j = false;
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> infos = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo info : infos) {
                for (String elementOfBlackList : BlackList.blackList) {
                    j = j ||info.processName.equals(elementOfBlackList);
                    if (info.processName.equals(elementOfBlackList)) {
                        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                        audioManager.setSpeakerphoneOn(true);
                        audioManager.setMode(AudioManager.MODE_NORMAL);
                        audioManager.setStreamVolume(AudioManager.STREAM_ALARM, 100, 0);
                        Vibrator vibrator = (Vibrator) context.getSystemService(VIBRATOR_SERVICE);
                        long[] pattern = {400, 50, 400, 50};
                        vibrator.vibrate(pattern, 1);
                        MediaPlayer mp = new MediaPlayer();
                        AssetFileDescriptor file = context.getResources().openRawResourceFd(R.raw.roar);
                        try {
                            mp.setDataSource(file.getFileDescriptor(), file.getStartOffset(),
                                    file.getLength());
                            mp.prepare();
                                file.close();
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                        mp.setVolume(2.5f, 2.5f);
                        mp.setLooping(true);
                        mp.start();
                        try {
                            Thread.sleep(19000);
                            vibrator.cancel();
                            mp.stop();
                            System.out.println("audio next stage");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (BlackList.judge==false){
                            System.out.println("audio stop");
                            this.cancel(true);
                            if (isCancelled()) return null;
                        }
                    }
                }
            }
            BlackList.judge = BlackList.judge;
        }
        return true;
    }
}
