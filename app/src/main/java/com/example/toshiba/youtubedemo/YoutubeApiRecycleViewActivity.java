package com.example.toshiba.youtubedemo;

import android.content.Context;
import android.graphics.Color;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class YoutubeApiRecycleViewActivity extends AppCompatActivity {
    final String jsonUrl = "http://codemobiles.com/adhoc/youtubes/index_new.php?username=admin&password=password&type=foods";
    @BindView(R.id.swipe_refresh_async_layout) SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.YoutubeRecycleView) RecyclerView mYoutubeRecycleView;
    RecyclerView.LayoutManager layoutManager;
    YoutubeClipRecycleViewAdapter adapter;
    List<VideoClip> clips;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_api_recycleview);
        ButterKnife.bind(this);
        mRefreshLayout.setColorSchemeColors(Color.parseColor("#4183D7"),
                Color.parseColor("#F62459"),
                Color.parseColor("#03C9A9"),
                Color.parseColor("#F4D03F"));
    }

    @Override
    protected void onStart() {
        super.onStart();
        createSynchronousOKHttp();
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                createSynchronousOKHttp();
                adapter.notifyDataSetChanged();
                mRefreshLayout.setRefreshing(false);
            }
        });
//        AsynchronousOKHttp();
//        getJsonDataWithVolley();
//        createRetrofitCall();
    }

    protected void createSynchronousOKHttp(){
        new AsyncTask<Void, Void, JSONObject>() {

            @Override
            protected JSONObject doInBackground(Void... voids) {
                OkHttpClient okHttpClient = new OkHttpClient();
                com.squareup.okhttp.Request.Builder builder = new com.squareup.okhttp.Request.Builder();
                com.squareup.okhttp.Request request = builder.url(jsonUrl).build();

                try {
                    com.squareup.okhttp.Response response = okHttpClient.newCall(request).execute();
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

                mYoutubeRecycleView.setHasFixedSize(true);

                layoutManager = new LinearLayoutManager(YoutubeApiRecycleViewActivity.this);
                layoutManager.setAutoMeasureEnabled(true);
                mYoutubeRecycleView.setLayoutManager(layoutManager);

                adapter = new YoutubeClipRecycleViewAdapter(YoutubeApiRecycleViewActivity.this,clips);
                mYoutubeRecycleView.setAdapter(adapter);
            }
        }.execute();
    }

    protected void createAsynchronousOKHttp(){
        new AsyncTask<String, Void, JSONObject>(){
            @Override
            protected JSONObject doInBackground(String... strings) {
                OkHttpClient okHttpClient = new OkHttpClient();
                com.squareup.okhttp.Request.Builder builder = new com.squareup.okhttp.Request.Builder();
                com.squareup.okhttp.Request request = builder.url(jsonUrl).build();

                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(com.squareup.okhttp.Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(com.squareup.okhttp.Response response) throws IOException {
                        if(response.isSuccessful()){
                            try {
                                updateViewOnSuccess(new JSONObject(response.body().string()));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            updateViewOnFailure(response.code());
                        }
                    }

                    void updateViewOnSuccess(final JSONObject jsonObject){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Gson gson = new Gson();
                                Youtube youtube = gson.fromJson(String.valueOf(jsonObject),Youtube.class);
                                clips = youtube.getClips();

                                mYoutubeRecycleView = (RecyclerView) findViewById(R.id.YoutubeRecycleView);
                                mYoutubeRecycleView.setHasFixedSize(true);

                                layoutManager = new LinearLayoutManager(YoutubeApiRecycleViewActivity.this);
                                layoutManager.setAutoMeasureEnabled(true);
                                mYoutubeRecycleView.setLayoutManager(layoutManager);

                                YoutubeClipRecycleViewAdapter adapter = new YoutubeClipRecycleViewAdapter(YoutubeApiRecycleViewActivity.this,clips);
                                mYoutubeRecycleView.setAdapter(adapter);
                            }
                        });                    }

                    void updateViewOnFailure(int resultCode){
                        Toast.makeText(YoutubeApiRecycleViewActivity.this,String.valueOf(resultCode),Toast.LENGTH_LONG).show();
                    }
                });
                return null;
            }
        }.execute();
    }

    private class YoutubeClipRecycleViewAdapter extends RecyclerView.Adapter<ViewHolder> {
        private Context ctx;
        private List<VideoClip> clips;

        YoutubeClipRecycleViewAdapter(Context ctx, List<VideoClip> clips) {
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

    class ViewHolder extends RecyclerView.ViewHolder {
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
