package com.yoloho.lv.httpproject.domain.forum;

import java.util.ArrayList;
import java.util.List;

/**
 * 关注人基本信息(人的信息和话题信息)
 * Created by mylinux on 16/05/19.
 */
public class AttentionInfoBean {
    public String id;
    public String title;
    public String status;
    public String createUid;
    public String nickName;
    public String createDate;
    public String stsChangeDate;
    public String viewnum;
    public String favnum;
    public String answernum;
    public String lastAnswerTime;
    public String ip;
    public String isanonymous;
    public String topicgroupid;
    public String topicGroupName;
    public String topicTypeId;
    public String isgrouptop;
    public String isalltop;
    public String source;
    public String isessence;
    public String ishot;
    public String isevent;
    public String ismedical;
    public String islock;
    public String lockDate;
    public String isDown;
    public String downdate;
    public String isfav;
    public String dateline;
    public String content;
    public String dayimaTopicId;
    public String createSortDate;
    public String defaultSortDate;
    public String isOnWhitelist;
    public String contents;
    public String viewstr;
    public String stepInfo;
    public String userIcon;
    public String likecount;
    public String islike;
    public String isAd;
    public String adFlag;
    public String adLink;
    public String adSort;
    public String adId;
    public List<Piclist> piclist = new ArrayList<Piclist>();

    @Override
    public String toString() {
        return "[ id = " + id + " nickName = " + nickName + " content = " + content + "]";
    }
}
