package com.example.rkjc.news_app_2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class NewsRecyclerViewAdapter  extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsViewHolder> {
    Context mContext;
    ArrayList<NewsItem> mNews;

    public NewsRecyclerViewAdapter(Context context, ArrayList<NewsItem> news){
        this.mContext = context;
        this.mNews = news;
    }

    @Override
    public NewsRecyclerViewAdapter.NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.news_item, parent, shouldAttachToParentImmediately);
        NewsViewHolder viewHolder = new NewsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NewsRecyclerViewAdapter.NewsViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mNews.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder  {
        TextView title;
        TextView description;
        TextView date;


        public NewsViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);
            date = (TextView) itemView.findViewById(R.id.date);
        }

        void bind(final int listIndex) {
            title.setText("Title: " + mNews.get(listIndex).getTitle());
            description.setText("Description: " + mNews.get(listIndex).getDescription());
            date.setText("Date: " + mNews.get(listIndex).getPublishedAt());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri webpage = Uri.parse(mNews.get(listIndex).getUrl());
                    Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

                    if(intent.resolveActivity(mContext.getPackageManager()) != null) {
                        mContext.startActivity(intent);
                    }
                }
            });

        }


    }

}
