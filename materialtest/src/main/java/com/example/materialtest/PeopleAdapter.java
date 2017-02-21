package com.example.materialtest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by GTR on 2017/2/19.
 */

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.ViewHolder> {

    private Context mContext;
    private List<People> mPeopleList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView peopleImage;
        TextView peopleName;

        public ViewHolder(View itemView) { //itemView是RecyclerView的子项
            super(itemView);
            cardView = (CardView) itemView;
            peopleImage = (ImageView) itemView.findViewById(R.id.people_image);
            peopleName = (TextView) itemView.findViewById(R.id.people_name);
        }
    }

    //这个构造方法用于把要展示的数据源传进来
    public PeopleAdapter(List<People> peopleList){
        mPeopleList = peopleList;
    }

    //用于创建ViewHolder的实例
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.people_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                People people = mPeopleList.get(position);
                Intent intent = new Intent(mContext, PeopleActivity.class);
                intent.putExtra(PeopleActivity.PEOPLE_NAME, people.getName());
                intent.putExtra(PeopleActivity.PEOPLE_IMAGE_ID, people.getImageId());
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    private static List<Integer> mHeights = new LinkedList<>();
    //用于对RecyclerView子项的数据进行赋值
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        People people = mPeopleList.get(position);
        holder.peopleName.setText(people.getName());
        ImageView imageView = holder.peopleImage;
        if (mHeights.size() <= position) {
            mHeights.add((int) (500 + Math.random() * 200)); //得到随机item的高度
        }
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams(); //得到item的LayoutParams布局参数
        layoutParams.height = mHeights.get(position); //把随机的高度赋予item布局
        imageView.setLayoutParams(layoutParams); ////把params设置给item布局
        Glide.with(mContext).load(people.getImageId()).into(imageView);

    }

    //RecyclerView子项的数目
    @Override
    public int getItemCount() {
        return mPeopleList.size();
    }

    public static void clearHeights() {
        mHeights.clear();
    }
}
