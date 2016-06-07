package com.yoloho.lv.httpproject.actmenus.dropdownmenu.bean;


/**
 * Created by lv on 2016/5/25.
 */
public class TopicObject {

    public int id;

    public int icon;

    public String desc;

    public String name;

    public String time;

    public int commentCount;

    public TopicLabelObject labels;


    public TopicObject(int icon, String desc, String name, String time, int commentCount, TopicLabelObject labels) {
        this.icon = icon;
        this.desc = desc;
        this.name = name;
        this.time = time;
        this.commentCount = commentCount;
        this.labels = labels;
    }
}
