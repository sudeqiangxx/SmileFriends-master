package cn.com.sdq.smilefriends.bean.entity;



import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/8/31.
 */
/**
 * http://news-at.zhihu.com/api/4/news/latest返回的JSON字符串对应的实体类。
 */

public class LatestNews extends BaseEntity implements Serializable {


    private String date;


    private List<Story> stories;


    private List<TopStory> top_stories;

    public void setDate(String date) {
        this.date = date;
    }

    public void setStories(List<Story> stories) {
        this.stories = stories;
    }

    public void setTop_stories(List<TopStory> topStories) {
        this.top_stories = topStories;
    }

    public String getDate() {
        return date;
    }

    public List<Story> getStories() {
        return stories;
    }

    public List<TopStory> getTopStories() {
        return top_stories;
    }

}
