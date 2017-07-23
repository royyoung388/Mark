package com.roy.mark.framework;

/**
 * Created by Administrator on 2017/7/19.
 */

public class Matter {
    public String title, content;
    public boolean finish;
    public int priority;
    public String beginTime, endTime;
    public int id;

    public Matter() {}

    public Matter(String title, String content, String beginTime, String endTime, int priority, boolean finish) {
        this.title = title;
        this.content = content;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.priority = priority;
        this.finish = finish;
    }
}
