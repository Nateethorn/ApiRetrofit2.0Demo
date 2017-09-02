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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class YoutubeApiRecycleViewVolleyActivity extends AppCompatActivity implements ApiViewVolleyInterface{
    @BindView(R.id.recycleView_youtube_volley) RecyclerView youtubeRecycleView;
    @BindView(R.id.swipe_youtube_volley_layout) SwipeRefreshLayout swipeRefreshLayout;
    YoutubeApiVolleyAdapter adapter;
    ApiVolleyModel apiVolleyModel;
    ApiVolleyPresenter apiVolleyPresenter;
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
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        apiVolleyPresenter.unbind();
    }

    @Override
    public void updateUi(List<VideoClip> clips) {
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
            holder.title.setText(videoClip.getTitle());
            holder.subtitle.setText(videoClip.getSubtitle());
        }

        @Override
        public int getItemCount() {
            return clips.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.image_youtube) ImageView youtube_img;
        @BindView(R.id.image_avatar) ImageView avatar_img;
        @BindView(R.id.text_title) TextView title;
        @BindView(R.id.text_subtitle) TextView subtitle;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
