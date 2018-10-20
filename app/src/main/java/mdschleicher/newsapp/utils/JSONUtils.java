package mdschleicher.newsapp.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import mdschleicher.newsapp.models.NewsItem;

public class JSONUtils {

    public static ArrayList<NewsItem> makeNewsItemList(String jsonResult){
        ArrayList<NewsItem> newsList = new ArrayList<>();
        try {
            JSONObject mainJSONObject = new JSONObject(jsonResult);
            JSONArray items = mainJSONObject.getJSONArray("articles");

            for(int i = 0; i < items.length(); i++){
                JSONObject item = items.getJSONObject(i);
                newsList.add(new NewsItem(
                        item.getString("author"),
                        item.getString("title"),
                        item.getString("description"),
                        item.getString("url"),
                        item.getString("urlToImage"),
                        item.getString("publishedAt")
                        ));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newsList;
    }
}