package com.yoloho.lv.httpproject.actmenus.dropdownmenu.bean;

/**
 * Created by lv on 2016/5/25.
 * <p/>
 * 标签实体类
 */
public class TopicLabelObject {

    /*标签的唯一id*/
    public int id;
    /*需要显示的数量,如(5)*/
    public int count;
    /*标签的文案*/
    public String name;

    public TopicLabelObject(int id, int count, String name) {
        this.id = id;
        this.count = count;
        this.name = name;
    }
}
