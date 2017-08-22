package com.example.toshiba.youtubedemo;

import android.content.Context;
import android.support.annotation.NonNull;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class YoutubeApiCardViewRetrofitActivity extends AppCompatActivity {
    RecyclerView cardViewYoutubeRetrofit;
    SwipeRefreshLayout swipeRefreshLayout;
    List<VideoClip> clips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_api_card_view_retrofit);

        cardViewYoutubeRetrofit = (RecyclerView) findViewById(R.id.cardview_youtube_retrofit);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_youtube_cardview_retrofit);
    }

    @Override
    protected void onStart() {
        super.onStart();
        callWithRetrofit();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callWithRetrofit();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void callWithRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://codemobiles.com/adhoc/youtubes/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<Youtube> call = apiInterface.getYoutube("admin","password","foods");
        call.enqueue(new Callback<Youtube>() {
            @Override
            public void onResponse(@NonNull Call<Youtube> call, @NonNull Response<Youtube> response) {
                if(response.isSuccessful()){
                    Youtube youtube = response.body();
                    clips = youtube.getClips();

                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    cardViewYoutubeRetrofit.setLayoutManager(layoutManager);

                    YoutubeApiCardViewAdapter adapter = new YoutubeApiCardViewAdapter(getApplicationContext(),clips);
                    cardViewYoutubeRetrofit.setAdapter(adapter);
                }
                else{
                    Toast.makeText(getApplicationContext(),response.errorBody().toString(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Youtube> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_LONG).show();
            }
        });
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
        ImageView youtube_img;
        ImageView avatar_img;
        TextView Title;
        TextView Subtitle;

        ViewHolder(View itemView) {
            super(itemView);
            youtube_img = (ImageView) itemView.findViewById(R.id.image_youtube_cardview);
            avatar_img = (ImageView) itemView.findViewById(R.id.image_avatar_cardview);
            Title = (TextView) itemView.findViewById(R.id.text_title_cardview);
            Subtitle = (TextView) itemView.findViewById(R.id.text_subtiltle_cardview);
        }
    }
}
