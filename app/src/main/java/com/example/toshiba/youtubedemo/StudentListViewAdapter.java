package com.example.toshiba.youtubedemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

class StudentListViewAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater mInflater;
    List<Student> list;

    StudentListViewAdapter(Context mContext, List<Student> list) {
        this.mContext = mContext;
        this.list = list;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            //load layout
            view = mInflater.inflate(R.layout.item_student,null);
            viewHolder = new ViewHolder();
            viewHolder.std_id = (TextView) view.findViewById(R.id.text_std_id);
            viewHolder.std_name = (TextView) view.findViewById(R.id.text_std_name);
            viewHolder.std_tel = (TextView) view.findViewById(R.id.text_std_tel);
            viewHolder.icon_arrow = (ImageView) view.findViewById(R.id.icon_arrow);
            view.setTag(viewHolder);
        } else {
            //rebind layout
            viewHolder = (ViewHolder) view.getTag();
        }

        Student student = list.get(i);
        if (viewHolder != null) {
            viewHolder.std_id.setText(student.getStudent_id());
            viewHolder.std_name.setText(student.getStudent_name());
            viewHolder.std_tel.setText(student.getStudent_tel());
            viewHolder.icon_arrow.setImageResource(R.drawable.arrow_right);
            viewHolder.icon_arrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext,"Clicked",Toast.LENGTH_SHORT).show();
                }
            });
        }
        return view;
    }

    class ViewHolder{
        TextView std_id;
        TextView std_name;
        TextView std_tel;
        ImageView icon_arrow;
    }
}
