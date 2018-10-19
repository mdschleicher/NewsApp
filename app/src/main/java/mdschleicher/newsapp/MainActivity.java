package mdschleicher.newsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private EditText mSearchBoxEditText;

    private RecyclerView mRecyclerView;
    private RepoAdapter mAdapter;
    private ArrayList</newsItem> articles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchBoxEditText = (EditText) findViewById(R.id.et_search_box);

        mRecyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
        mAdapter = new RepoAdapter(this, articles);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    class NewsAppQueryTask extends AsyncTask<URL,Void, String> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected String doInBackgroud(URL... urls) {
            String newsappSearchResults = "";

            try {
                newsappSearchResults = NetworkUtils.getResponseFromHttpUrl(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return newsappSearchResults;
        }

        @Override
        protected void onPostExecutre(String s) {
            Log.d("mycode", s);
            super.onPostExecute(s);
            articles = JSONUtils.makeArticlesList(s);
            mAdapter.mArticles.addAll(articles);
            mAdapter.notifyDataSetChanged();
        }
    }
}
