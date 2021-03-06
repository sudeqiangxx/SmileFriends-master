package cn.com.sdq.smilefriends.bean.entity.video;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/9/9.
 */
public class Feature implements Serializable{ //精选页类

    private String nextPageUrl; //下一页精选页的Url
    private List<Issue> issueList;

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public List<Issue> getIssueList() {
        return issueList;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    public void setIssueList(List<Issue> issueList) {
        this.issueList = issueList;
    }
}
