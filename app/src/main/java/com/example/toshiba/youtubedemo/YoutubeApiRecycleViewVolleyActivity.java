package com.example.toshiba.youtubedemo;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class YoutubeApiRecycleViewVolleyActivity extends AppCompatActivity implements ApiViewVolleyInterface{
    @BindView(R.id.recycleview_youtube_volley) RecyclerView youtubeRecycleView;
    @BindView(R.id.swipe_youtube_volley_layout) SwipeRefreshLayout swipeRefreshLayout;
    YoutubeApiVolleyAdapter adapter;
    ApiVolleyModel apiVolleyModel;
    ApiVolleyPresenter apiVolleyPresenter;
    List<VideoClip> clips;
    final String jsonUrl = "http://codemobiles.com/adhoc/youtubes/index_new.php?username=admin&password=password&type=foods";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_api_recycle_view_volley);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        apiVolleyModel = new ApiVolleyModel(getApplicationContext());
        apiVolleyPresenter = new ApiVolleyPresenter(apiVolleyModel,jsonUrl);
        apiVolleyPresenter.bind(this);
        apiVolleyPresenter.displayAllResult();
//        feedDataWithVolley();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                feedDataWithVolley();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        apiVolleyPresenter.unbind();
    }

//    private void feedDataWithVolley(){
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, jsonUrl, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                Gson gson = new Gson();
//                Youtube youtube = gson.fromJson(String.valueOf(response),Youtube.class);
//                clips = youtube.getClips();
//                Log.i("JsonClips",String.valueOf(clips));
//
//                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
//                youtubeRecycleView.setLayoutManager(layoutManager);
//
//                adapter = new YoutubeApiVolleyAdapter(getApplicationContext(),clips);
//                youtubeRecycleView.setAdapter(adapter);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
//            }
//        });
//        requestQueue.add(jsonObjectRequest);
//    }

    @Override
    public void updateUi(JSONObject response) {
        Gson gson = new Gson();
        Youtube youtube = gson.fromJson(String.valueOf(response),Youtube.class);
        clips = youtube.getClips();
        Log.i("JsonClips",String.valueOf(clips));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        youtubeRecycleView.setLayoutManager(layoutManager);

        adapter = new YoutubeApiVolleyAdapter(getApplicationContext(),clips);
        youtubeRecycleView.setAdapter(adapter);
    }

    @Override
    public void updateErrorResponse(VolleyError volleyError) {
        Toast.makeText(getApplicationContext(),volleyError.toString(),Toast.LENGTH_LONG).show();
    }


    private class YoutubeApiVolleyAdapter extends RecyclerView.Adapter<ViewHolder>{
        Context ctx;
        List<VideoClip> clips;

        YoutubeApiVolleyAdapter(Context ctx, List<VideoClip> clips) {
            this.ctx = ctx;
            this.clips = clips;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(ctx).inflate(R.layout.item_clips,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            VideoClip videoClip = clips.get(position);

            Glide.with(ctx).load(videoClip.getYoutube_image()).fitCenter().into(holder.youtube_img);
            Glide.with(ctx).load(videoClip.getAvatar_image()).fitCenter().into(holder.avatar_img);
            holder.Title.setText(videoClip.getTitle());
            holder.Subtitle.setText(videoClip.getSubtitle());
        }

        @Override
        public int getItemCount() {
            return clips.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.Youtube_Image) ImageView youtube_img;
        @BindView(R.id.Avatar_Image) ImageView avatar_img;
        @BindView(R.id.Title) TextView Title;
        @BindView(R.id.SubTitle) TextView Subtitle;


        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
