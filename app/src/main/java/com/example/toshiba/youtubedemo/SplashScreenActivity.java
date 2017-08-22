package com.example.toshiba.youtubedemo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class SplashScreenActivity extends AppCompatActivity {
    final static int ACCESS_CODE = 111;
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this,MenuActivity.class);
                startActivity(intent);
                finish();
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            String permission[] = {Manifest.permission.INTERNET};

            if(!(checkPermission(this,permission))){
                ActivityCompat.requestPermissions(this,permission,ACCESS_CODE);
            }
            else{
                Toast.makeText(SplashScreenActivity.this,"1:1",Toast.LENGTH_LONG).show();
                handler.postDelayed(runnable,3000);
            }
        }
        else{
            Toast.makeText(SplashScreenActivity.this,"1:2",Toast.LENGTH_LONG).show();
            handler.postDelayed(runnable,3000);
        }
    }

    public boolean checkPermission(Context context, String[] Permission){
        for(String permission : Permission){
            if(ActivityCompat.checkSelfPermission(context,permission) != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case ACCESS_CODE : {
                int count = 0;
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults.length > 0 && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        count++;
                    }
                }
                if (count == grantResults.length) {
                    handler.postDelayed(runnable,3000);
                }
                else {
                    Toast.makeText(SplashScreenActivity.this,"Some Permission has Denied",Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
