package com.example.rkjc.news_app_2;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class NewsRecyclerViewAdapter  extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsViewHolder> {
    Context mContext;
    //ArrayList<NewsItem> mNews;
    private List<NewsItem> mNews;
    private NewsItemViewModel viewModel;

    public NewsRecyclerViewAdapter(Context context, NewsItemViewModel viewModel){
        this.mContext = context;
        this.viewModel = viewModel;
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

    void setNewsItems(List<NewsItem> newsItems) {
        mNews = newsItems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mNews != null) return mNews.size();
        else return 0;
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder  {
        TextView title;
        TextView abstr;
        ImageView img;


        public NewsViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            abstr = (TextView) itemView.findViewById(R.id.abstr);
            img = (ImageView) itemView.findViewById(R.id.img);
        }

        void bind(final int listIndex) {
            title.setText(mNews.get(listIndex).getTitle());
            abstr.setText(mNews.get(listIndex).getPublishedAt());
            abstr.append(" . ");
            abstr.append(mNews.get(listIndex).getDescription());

            if(mNews.get(listIndex).getUrlToImage() != null) {
                Picasso.get().load(mNews.get(listIndex).getUrlToImage()).into(img);
            }

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
