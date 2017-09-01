package com.example.toshiba.youtubedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuActivity extends AppCompatActivity {
    @BindView(R.id.button_recycleview) private Button mRecycleViewWithOkHttpBtn;
    @BindView(R.id.button_cardview) private Button mCardViewWithOkHttpBtn;
    @BindView(R.id.button_retrofit_recycleview) private Button mRecycleViewWithRetrofit;
    @BindView(R.id.button_retrofit_cardview) private Button mCardViewWithRetrofit;
    @BindView(R.id.button_volley_recycleview) private Button mRecycleViewWithVolleyBtn;
    @BindView(R.id.button_volley_cardview) private Button mCardViewWithVolleyBtn;
    @BindView(R.id.button_staggered_grid_retrofit) private Button mStaggeredGridWithRetrofitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mRecycleViewWithOkHttpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),YoutubeApiRecycleViewActivity.class);
                startActivity(i);
            }
        });

        mCardViewWithOkHttpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),YoutubeApiCardViewActivity.class);
                startActivity(i);
            }
        });

        mRecycleViewWithRetrofit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),YoutubeApiRecycleViewRetrofitActivity.class);
                startActivity(i);
            }
        });

        mCardViewWithRetrofit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),YoutubeApiCardViewRetrofitActivity.class);
                startActivity(i);
            }
        });

        mRecycleViewWithVolleyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),YoutubeApiRecycleViewVolleyActivity.class);
                startActivity(i);
            }
        });

        mCardViewWithVolleyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),YoutubeApiCardViewVolleyActivity.class);
                startActivity(i);
            }
        });

        mStaggeredGridWithRetrofitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),TreeHouseApiStaggeredGridActivity.class);
                startActivity(i);
            }
        });
    }
}
