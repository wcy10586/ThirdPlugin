package com.yoloho.lv.httpproject.domain.forum;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mylinux on 16/05/11.
 */
public class TopicInfo {
    @SerializedName("stsChangeDate")
    public String stsChangeDate;
    public String id;
    public String title;
    public String status;
    public String createUid;
    public String nickName;
    public String stepInfo;
    public String createDate;
    public String viewnum;
    public String favnum;
    public String answernum;
    public String lastAnswerTime;
    public String ip;
    public String isanonymous;
    public String topicgroupid;
    public String topicGroupName;
    public String userIcon;
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
    public String userGroupId;
    public String isBan;
    public List<Piclist> piclist = new ArrayList<Piclist>();
    public String isMixed;
    public String currentUserIdentity;
    public String createSortDate;
    public String defaultSortDate;
    public String isOnWhitelist;
    public List<Object> mixContent = new ArrayList<Object>();
    public String canshare;
    public String shareUrl;
    public String viewstr;
    public String likecount;
    public String islike;
    public String userHonor;
    public String relationKnowledge;
    public List<Object> recomList = new ArrayList<Object>();
    public List<Object> tagList = new ArrayList<Object>();
}
