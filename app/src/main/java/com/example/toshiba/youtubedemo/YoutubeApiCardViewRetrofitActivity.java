package com.example.toshiba.youtubedemo;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

public class YoutubeApiCardViewRetrofitActivity extends AppCompatActivity implements ApiViewRetrofitInterface {
    @BindView(R.id.cardview_youtube_retrofit) RecyclerView mCardViewYoutubeRetrofit;
    @BindView(R.id.swipe_youtube_cardview_retrofit) SwipeRefreshLayout mSwipeRefreshLayout;
    List<VideoClip> clips;
    ApiRetrofitModel apiModel;
    ApiRetrofitPresenter apiRetrofitPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_api_card_view_retrofit);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        apiModel = new ApiRetrofitModel();
        apiRetrofitPresenter = new ApiRetrofitPresenter(apiModel);
        apiRetrofitPresenter.bindView(this);
        apiRetrofitPresenter.displayResult();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                apiRetrofitPresenter.bindView((ApiViewRetrofitInterface) getApplicationContext());
                apiRetrofitPresenter.displayResult();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        apiRetrofitPresenter.unbindView();
    }

    @Override
    public void updateUi(Youtube youtube) {
        clips = youtube.getClips();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mCardViewYoutubeRetrofit.setLayoutManager(layoutManager);

        YoutubeApiCardViewAdapter adapter = new YoutubeApiCardViewAdapter(getApplicationContext(),clips);
        mCardViewYoutubeRetrofit.setAdapter(adapter);
    }

    @Override
    public void updateUnSuccessResponse(ResponseBody responseBody) {
        Toast.makeText(getApplicationContext(),responseBody.toString(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateFalseResponse(Throwable t) {
        Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_LONG).show();
    }

    private class YoutubeApiCardViewAdapter extends RecyclerView.Adapter<ViewHolder>{
        Context ctx;
        List<VideoClip> clips;

        YoutubeApiCardViewAdapter(Context ctx, List<VideoClip> clips) {
            this.ctx = ctx;
            this.clips = clips;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(ctx).inflate(R.layout.item_cardview_clips,parent,false);
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
        @BindView(R.id.image_youtube_cardview) ImageView youtube_img;
        @BindView(R.id.image_avatar_cardview) ImageView avatar_img;
        @BindView(R.id.text_title_cardview) TextView Title;
        @BindView(R.id.text_subtiltle_cardview) TextView Subtitle;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
