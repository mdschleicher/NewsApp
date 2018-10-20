package mdschleicher.newsapp;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import mdschleicher.newsapp.models.NewsItem;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder>{
    Context mContext;
    ArrayList<NewsItem> mNews;

    public NewsAdapter(Context context, ArrayList<NewsItem> news){
        this.mContext = context;
        this.mNews = news;
    }

    @Override
    public NewsAdapter.NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.item, parent, shouldAttachToParentImmediately);
        NewsHolder viewHolder = new NewsHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NewsAdapter.NewsHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mNews.size();
    }

    public class NewsHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView description;
        TextView date;

        public NewsHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);
            date = (TextView) itemView.findViewById(R.id.date);
        }

        void bind(int listIndex) {
            title.setText(mNews.get(listIndex).getTitle());
            description.setText(mNews.get(listIndex).getDescription());
            date.setText(mNews.get(listIndex).getPublishedAt());

        }
    }
}