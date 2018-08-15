package com.newser.Data;

import java.util.ArrayList;

/**
 * Created by sahni on 15/8/18.
 */

public class Categories {
    String title;
    ArrayList<String> news;

    public String getTitle() {
        return title;
    }
    public ArrayList<String> getNews() {
        return news;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setNews(ArrayList<String> news) {
        this.news = news;
    }
}
