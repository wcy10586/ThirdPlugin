package com.yoloho.lv.httpproject.actmenus.dropdownmenu.bean;

/**
 * 下拉菜单的实体类
 * Created by lv on 2016/5/25.
 */
public class DropdownItemObject {

    /**列表选项的id*/
    public int id;
    /*文本*/
    public String text;
    /*值*/
    public String value;
    /*后缀,说明:全部讨论(100),后缀代表的是(100)*/
    public String suffix;

    public DropdownItemObject(String text, int id, String value) {
        this.text = text;
        this.id = id;
        this.value = value;
    }

}
