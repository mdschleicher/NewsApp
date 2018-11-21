package com.example.rkjc.news_app_2;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class NewsItemRepository {
    private NewsItemDAO mNewsItemDao;
    private LiveData<List<NewsItem>> mAllNewsItems;

    public NewsItemRepository(Application application) {
        NewsItemDatabase db = NewsItemDatabase.getDatabase(application);
        mNewsItemDao = db.newsItemDAO();
        mAllNewsItems = mNewsItemDao.loadAllNewsItems();
    }

    LiveData<List<NewsItem>> getAllNewsItems() {
        if(mAllNewsItems == null) {
            Log.d("mycode","Live data is null?");
        }

        return mAllNewsItems;
    }

    public void sync(URL url) {
        Log.d("mycode", "in sync NewsItemRepo");
        new insertAsyncTask(mNewsItemDao).execute(url);
    }

    private static class insertAsyncTask extends AsyncTask<URL, Void, Void> {
        private NewsItemDAO mAsyncTaskDao;

        insertAsyncTask(NewsItemDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final URL... urls) {
            String newsappSearchResults = "";

            try {
                newsappSearchResults = NetworkUtils.getResponseFromHttpUrl(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            List<NewsItem> articles = JsonUtils.parseNews(newsappSearchResults);
            Log.d("mycode", "Inserting " + articles.size() + " to the db.");
            mAsyncTaskDao.clearAll();
            for(NewsItem article: articles) {
                mAsyncTaskDao.insert(article);
            }
            return null;
        }

    }

}
