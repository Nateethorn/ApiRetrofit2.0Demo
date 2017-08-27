package com.example.toshiba.youtubedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuActivity extends AppCompatActivity {
    @BindView(R.id.button_recycleview) Button recycleViewWithOkHttpBtn;
    @BindView(R.id.button_cardview) Button cardViewWithOkHttpBtn;
    @BindView(R.id.button_retrofit_recycleview) Button recycleViewWithRetrofit;
    @BindView(R.id.button_retrofit_cardview) Button cardViewWithRetrofit;
    @BindView(R.id.button_volley_recycleview) Button recycleViewWithVolleyBtn;
    @BindView(R.id.button_volley_cardview) Button cardViewWithVolleyBtn;
    @BindView(R.id.button_staggered_grid_retrofit) Button StaggeredGridWithRetrofitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);

//        recycleViewWithOkHttpBtn = (Button) findViewById(R.id.button_recycleview);
//        cardViewWithOkHttpBtn = (Button) findViewById(R.id.button_cardview);
//        recycleViewWithRetrofit = (Button) findViewById(R.id.button_retrofit_recycleview);
//        cardViewWithRetrofit = (Button) findViewById(R.id.button_retrofit_cardview);
//        recycleViewWithVolleyBtn = (Button) findViewById(R.id.button_volley_recycleview);
//        cardViewWithVolleyBtn = (Button) findViewById(R.id.button_volley_cardview);
//        StaggeredGridWithRetrofitBtn = (Button) findViewById(R.id.button_staggered_grid_retrofit);
    }

    @Override
    protected void onResume() {
        super.onResume();

        recycleViewWithOkHttpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),YoutubeApiRecycleViewActivity.class);
                startActivity(i);
            }
        });

        cardViewWithOkHttpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),YoutubeApiCardViewActivity.class);
                startActivity(i);
            }
        });

        recycleViewWithRetrofit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),YoutubeApiRecycleViewRetrofitActivity.class);
                startActivity(i);
            }
        });

        cardViewWithRetrofit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),YoutubeApiCardViewRetrofitActivity.class);
                startActivity(i);
            }
        });

        recycleViewWithVolleyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),YoutubeApiRecycleViewVolleyActivity.class);
                startActivity(i);
            }
        });

        cardViewWithVolleyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),YoutubeApiCardViewVolleyActivity.class);
                startActivity(i);
            }
        });

        StaggeredGridWithRetrofitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),TreeHouseApiStaggeredGridActivity.class);
                startActivity(i);
            }
        });
    }
}
