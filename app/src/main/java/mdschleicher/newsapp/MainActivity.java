package mdschleicher.newsapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import mdschleicher.newsapp.models.NewsItem;
import mdschleicher.newsapp.utils.JSONUtils;
import mdschleicher.newsapp.utils.NetworkUtils;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private NewsAdapter mAdapter;
    private ArrayList<NewsItem> articles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
        mAdapter = new NewsAdapter(this, articles);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

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
            NewsAppQueryTask task = new NewsAppQueryTask();
            task.execute(url);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class NewsAppQueryTask extends AsyncTask<URL,Void, String> {

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
            articles = JSONUtils.makeNewsItemList(s);
            mAdapter.mNews.addAll(articles);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
