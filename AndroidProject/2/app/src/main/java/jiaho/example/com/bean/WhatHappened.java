package jiaho.example.com.bean;

public class WhatHappened {
    /*
    * 1.顺序和名字必须和Json对象中的一样，否则解析失败
    * 2.必须写get和set方法
    * */
    private String img;
    private String year;
    private String title;
    private int month;
    private int day;

    public String getImg() {
        return img;
    }

    public String getYear() {
        return year;
    }

    public String getTitle() {
        return title;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
