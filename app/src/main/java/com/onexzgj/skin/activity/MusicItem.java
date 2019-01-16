package com.onexzgj.skin.activity;

/**
 * des：
 * author：onexzgj
 * time：2019/1/15
 */
public class MusicItem {

    private String name;
    private String time;
    private String author;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public MusicItem(String name, String time, String author) {
        this.name = name;
        this.time = time;
        this.author = author;
    }
}
