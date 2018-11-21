package com.example.rkjc.news_app_2;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface NewsItemDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NewsItem newsItem);

    @Query("DELETE FROM news_item")
    void clearAll();

    @Query("Select * FROM news_item")
    LiveData<List<NewsItem>> loadAllNewsItems();
}
