package com.example.toshiba.youtubedemo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

public class Permission {
    final static int ACCESS_CODE = 111;
    Context context;
    private String[] permission;

    public Permission(Context context, String[] permission) {
        this.context = context;
        this.permission = permission;
    }

    public void requestPermission(){
        if (!(checkPermission(context,permission))) {
            ActivityCompat.requestPermissions((Activity) context,permission,ACCESS_CODE);
        } else {

        }
    }

    public boolean checkPermission(Context context, String[] Permission){
        for(String permission : Permission){
            if (ActivityCompat.checkSelfPermission(context,permission) != PackageManager.PERMISSION_GRANTED) { return false; }
        }
        return true;
    }


}
