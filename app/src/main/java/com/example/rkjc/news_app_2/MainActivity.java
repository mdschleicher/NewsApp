package com.example.rkjc.news_app_2;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private NewsRecyclerViewAdapter mAdapter;
    //private ArrayList<NewsItem> articles = new ArrayList<>();
    private NewsItemViewModel mNewsItemViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNewsItemViewModel = ViewModelProviders.of(this).get(NewsItemViewModel.class);
        mRecyclerView = (RecyclerView)findViewById(R.id.news_recyclerview);
        mAdapter = new NewsRecyclerViewAdapter(this, mNewsItemViewModel);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        NewsAppBackgroupUpdaterUtilities.schedulerBackgroudUpdater(this);

        mNewsItemViewModel.getmAllNewsItems().observe(this, new Observer<List<NewsItem>>() {
            @Override
            public void onChanged(@Nullable List<NewsItem> newsItems) {
                mAdapter.setNewsItems(newsItems);
            }
        });

    }

    private URL makeNewsSearchQuery() {
        URL newsSearchUrl = NetworkUtils.buildUrl();
        String urlString = newsSearchUrl.toString();
        Log.d("mycode", urlString);
        return newsSearchUrl;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_search) {
            URL url = makeNewsSearchQuery();
            mNewsItemViewModel.sync(url);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*class NewsAppQueryTask extends AsyncTask<URL,Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            String newsappSearchResults = "";

            try {
                newsappSearchResults = NetworkUtils.getResponseFromHttpUrl(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return newsappSearchResults;
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }


        @Override
        protected void onPostExecute(String s) {
            Log.d("mycode", s);
            super.onPostExecute(s);
            articles = JsonUtils.parseNews(s);
            mAdapter.mNews.addAll(articles);
            mAdapter.notifyDataSetChanged();
        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}
