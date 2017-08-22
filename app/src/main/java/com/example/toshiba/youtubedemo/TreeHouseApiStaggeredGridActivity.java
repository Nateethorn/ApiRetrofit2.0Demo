package com.example.toshiba.youtubedemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.etsy.android.grid.StaggeredGridView;
import com.etsy.android.grid.util.DynamicHeightImageView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TreeHouseApiStaggeredGridActivity extends AppCompatActivity {
    StaggeredGridView staggeredGridView;
    List<Post> posts;
//    final String url = "http://blog.teamtreehouse.com/api/get_recent_summary/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree_house_api_staggered_grid);

        staggeredGridView = (StaggeredGridView) findViewById(R.id.staggeredGrid);
    }

    @Override
    protected void onStart() {
        super.onStart();
        feedDataWithRetrofit();
    }

    private void feedDataWithRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://blog.teamtreehouse.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TreeHouseApiInterface apiInterface = retrofit.create(TreeHouseApiInterface.class);
        Call<Blog> call = apiInterface.getBlog();
        call.enqueue(new Callback<Blog>() {
            @Override
            public void onResponse(@NonNull Call<Blog> call, @NonNull Response<Blog> response) {
                if(response.isSuccessful()){
                    Blog blog = response.body();
                    posts = blog.getPosts();

                    staggeredGridView.setAdapter(new CustomAdapter(getApplicationContext(),posts));
                }
                else{
                    Toast.makeText(getApplicationContext(),response.errorBody().toString(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Blog> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private class CustomAdapter extends BaseAdapter{
        Context ctx;
        List<Post> posts;

        CustomAdapter(Context ctx, List<Post> posts) {
            this.ctx = ctx;
            this.posts = posts;
        }

        @Override
        public int getCount() {
            return posts.size();
        }

        @Override
        public Object getItem(int i) {
            return posts.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            ViewHolder viewHolder;
            if(view == null){
                LayoutInflater layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = layoutInflater.inflate(R.layout.item_posts,viewGroup,false);
                viewHolder = new ViewHolder();
                viewHolder.imageView = (DynamicHeightImageView) findViewById(R.id.image_thumbnail);
                viewHolder.title = (TextView) findViewById(R.id.text_title);
                viewHolder.author = (TextView) findViewById(R.id.text_author);
                viewHolder.date = (TextView) findViewById(R.id.text_date);
                viewHolder.url = (TextView) findViewById(R.id.text_url);
                view.setTag(viewHolder);
            }
            else{
                viewHolder = (ViewHolder) view.getTag();
            }

            Post post = posts.get(i);
            if(viewHolder.imageView != null){
                Glide.with(ctx).load(post.getThumbnail()).fitCenter().into(viewHolder.imageView);
            }
//            if(viewHolder.title != null){
//                viewHolder.title.setText(post.getTitle());
//            }
//            if(viewHolder.author != null){
//                viewHolder.author.setText(post.getAuthor());
//            }
//            if(viewHolder.date != null){
//                viewHolder.date.setText(post.getDate());
//            }
//            if(viewHolder.url != null){
//                viewHolder.url.setText(post.getUrl());
//            }
            return view;
        }
    }

    private class ViewHolder{
        DynamicHeightImageView imageView;
        TextView title;
        TextView author;
        TextView date;
        TextView url;
    }
}
