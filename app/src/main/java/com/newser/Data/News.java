package com.newser.Data;

import com.google.firebase.Timestamp;

/**
 * Created by sahni on 15/8/18.
 */

public class News {
    private String id;
    private String title;
    private Timestamp time;
    private String content;
    private String img_link;

    public String getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getContent() {
        return content;
    }
    public Timestamp getTime() {
        return time;
    }
    public String getImg_link() {
        return img_link;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setTime(Timestamp time) {
        this.time = time;
    }
    public void setImg_link(String img_link) {
        this.img_link = img_link;
    }
}
