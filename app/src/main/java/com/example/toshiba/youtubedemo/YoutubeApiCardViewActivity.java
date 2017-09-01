package com.example.toshiba.youtubedemo;

import android.content.Context;
import android.os.AsyncTask;
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

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class YoutubeApiCardViewActivity extends AppCompatActivity {
    final String JsonURL = "http://codemobiles.com/adhoc/youtubes/index_new.php?username=admin&password=password&type=foods";
    @BindView(R.id.YoutubeCardView) RecyclerView mYoutubeCardView;
    @BindView(R.id.swipe_youtube_cardview_async) SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView.LayoutManager layoutManager;
    List<VideoClip> clips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_api_card_view);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        createSynchronousOkHttp();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                createSynchronousOkHttp();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    protected void createSynchronousOkHttp(){
        new AsyncTask<Void, Void, JSONObject>() {
            @Override
            protected JSONObject doInBackground(Void... voids) {

                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(JsonURL).build();

                try {
                    Response response = okHttpClient.newCall(request).execute();
                    if(response.isSuccessful()){
                        try {
                            return new JSONObject(response.body().string());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(JSONObject jsonObject) {
                super.onPostExecute(jsonObject);

                Gson gson = new Gson();
                Youtube youtube = gson.fromJson(String.valueOf(jsonObject),Youtube.class);
                clips = youtube.getClips();

                layoutManager = new LinearLayoutManager(getApplicationContext());
                mYoutubeCardView.setLayoutManager(layoutManager);

                YoutubeClipCardViewAdapter adapter = new YoutubeClipCardViewAdapter(getApplicationContext(),clips);
                mYoutubeCardView.setAdapter(adapter);
            }
        }.execute();
    }

    private class YoutubeClipCardViewAdapter extends RecyclerView.Adapter<ViewHolder> {
        private Context ctx;
        private List<VideoClip> clips;

        YoutubeClipCardViewAdapter(Context ctx, List<VideoClip> clips) {
            this.ctx = ctx;
            this.clips = clips;
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(ctx).inflate(R.layout.item_cardview_clips, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            VideoClip videoClip = clips.get(position);

            Glide.with(ctx).load(videoClip.getYoutube_image()).fitCenter().into(holder.youtube_image);
            Glide.with(ctx).load(videoClip.getAvatar_image()).fitCenter().into(holder.avatar_image);
            holder.title.setText(videoClip.getTitle());
            holder.subtitle.setText(videoClip.getSubtitle());
        }

        @Override
        public int getItemCount() {
            return clips.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_youtube_cardview) ImageView youtube_image;
        @BindView(R.id.image_avatar_cardview) ImageView avatar_image;
        @BindView(R.id.text_title_cardview) TextView title;
        @BindView(R.id.text_subtiltle_cardview) TextView subtitle;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
