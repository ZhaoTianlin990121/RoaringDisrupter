package com.example.firstactivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    List<AppInfo> appInfos = null;
    static ArrayList<String> blackList = BlackList.blackList;
//    ArrayList<String> goingToBeBlack = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_layout);
        appInfos = getAppInfos();
        final AppAdapter adapter = new AppAdapter(SecondActivity.this,R.layout.app_item,appInfos);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AppInfo appInfo = appInfos.get(i);
                Toast.makeText(SecondActivity.this, appInfo.getAppName()+"从现在起被监视，可以在白名单中撤销操作", Toast.LENGTH_SHORT).show();
                AppInfo k = adapter.getItem(i);
                BlackList.blackList.add(k.getProcessName());
                adapter.remove(k);
            }
        });
    }

    public List<AppInfo> getAppInfos(){
        PackageManager pm = getApplication().getPackageManager();
        List<PackageInfo>  packgeInfos = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
        appInfos = new ArrayList<AppInfo>();
        for(PackageInfo packgeInfo : packgeInfos){
            String packageName = packgeInfo.packageName;
            String appName = packgeInfo.applicationInfo.loadLabel(pm).toString();
            Drawable drawable = packgeInfo.applicationInfo.loadIcon(pm);
            AppInfo appInfo = new AppInfo(appName, packageName,drawable);
            appInfo.setProcessName(packgeInfo.applicationInfo.processName);
            if(!BlackList.blackList.contains(appInfo.getProcessName())){
                appInfos.add(appInfo);
            }
        }
        return appInfos;
    }
}
