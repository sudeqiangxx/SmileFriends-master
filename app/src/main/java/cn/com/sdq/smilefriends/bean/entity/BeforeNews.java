package cn.com.sdq.smilefriends.bean.entity;


import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/8/31.
 */

/**
 * 如：http://news.at.zhihu.com/api/4/news/before/20160831（输入日期）返回的字符串对应的实体类
 * 相比LatestNews没有List<TopStory>。
 */

public class BeforeNews implements Serializable {


    private String date;

    private List<Story> stories;

    public void setDate(String date) {
        this.date = date;
    }

    public void setStories(List<Story> stories) {
        this.stories = stories;
    }

    public String getDate() {
        return date;
    }

    public List<Story> getStories() {
        return stories;
    }
}
