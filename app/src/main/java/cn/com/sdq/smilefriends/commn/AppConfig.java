package cn.com.sdq.smilefriends.commn;

/**
 * Created by Administrator on 2016/10/30.
 */

public class AppConfig {
    //按指定时间查找笑话
    public static final String TIME_URL="http://japi.juhe.cn/joke/content/list.from/";
    //最新笑话
    public static final String NEW_URL="http://japi.juhe.cn/joke/content/text.from/";
    //按指定时间查找趣图
    public static final String TIME_IMG_URL="http://japi.juhe.cn/joke/img/list.from";
    //最新趣图
    public static final String NEW_IMG_URL="http://japi.juhe.cn/joke/img/text.from";

    public static final String GRILD_LIST="http://gank.io/";
    public static final String GRILD_URL="api/data/福利/20/";


    public static final String ZHIHU_COM="http://news-at.zhihu.com/";

    public static final String ZHIHU_URL="api/4/news/latest";
    public static final String LIST_PA="api/data/%E7%A6%8F%E5%88%A9/";
    public static final String PATH_CACHE="http-cache";

    public static final String APP_ID="1106368797";

    public static final String AD_ID="0011";


//知乎

//    @GET("api/v3/videos")
//    Call<Issue> getCategoryFeature(
//            @Query("strategy")String strategy1,
//            @Query("udid")String udid1,
//            @Query("vc")String vc1,
//            @Query("categoryName")String categoryName1
//    );
    public static final String VIDEO_URL="http://baobab.wandoujia.com/";
    //num为1返回一天的精选，为2返回两条的精选。
    public static final String JING_XUAN="api/v2/feed?num=1&udid=26868b32e808498db32fd51fb422d00175e179df&vc=83";


    public static final String FENLEI="api/v2/categories?udid=26868b32e808498db32fd51fb422d00175e179df&vc=83";
    public static final String GET_VIDEOS="api/v3/videos";


}
