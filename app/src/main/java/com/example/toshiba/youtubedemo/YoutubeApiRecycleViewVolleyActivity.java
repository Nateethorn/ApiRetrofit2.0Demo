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

public class YoutubeApiRecycleViewVolleyActivity extends AppCompatActivity {
    RecyclerView youtubeRecycleView;
    SwipeRefreshLayout swipeRefreshLayout;
    YoutubeApiVolleyAdapter adapter;
    List<VideoClip> clips;
    final String jsonUrl = "http://codemobiles.com/adhoc/youtubes/index_new.php?username=admin&password=password&type=foods";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_api_recycle_view_volley);
        youtubeRecycleView = (RecyclerView) findViewById(R.id.recycleview_youtube_volley);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_youtube_volley_layout);
    }

    @Override
    protected void onStart() {
        super.onStart();
        feedDataWithVolley();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                feedDataWithVolley();
//                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void feedDataWithVolley(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, jsonUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                Youtube youtube = gson.fromJson(String.valueOf(response),Youtube.class);
                clips = youtube.getClips();
                Log.i("JsonClips",String.valueOf(clips));

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                youtubeRecycleView.setLayoutManager(layoutManager);

                adapter = new YoutubeApiVolleyAdapter(getApplicationContext(),clips);
                youtubeRecycleView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
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
        ImageView youtube_img;
        ImageView avatar_img;
        TextView Title;
        TextView Subtitle;


        ViewHolder(View itemView) {
            super(itemView);
            youtube_img = (ImageView) itemView.findViewById(R.id.Youtube_Image);
            avatar_img = (ImageView) itemView.findViewById(R.id.Avatar_Image);
            Title = (TextView) itemView.findViewById(R.id.Title);
            Subtitle = (TextView) itemView.findViewById(R.id.SubTitle);
        }
    }
}
