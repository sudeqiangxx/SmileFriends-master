package cn.com.sdq.smilefriends.commn;


import java.util.List;

import cn.com.sdq.smilefriends.bean.JakeBean;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by RaphetS on 2016/10/15.
 */

public interface APIService {

    /**
     * 笑话列表
     *
     */
    //http://japi.juhe.cn/joke/content/list.from
    // ?key=您申请的KEY&page=2&pagesize=10&sort=asc&time=1418745237
    @GET("data/福利/{num}/{page}")
    Observable<JakeHttppResponse<List<JakeBean>>> getGirlList(@Path("num") int num, @Path("page") int page);
    /**
     * 笑话列表
     */
    @GET("data/福利/{num}/{page}")
    Observable<JakeHttppResponse<List<JakeBean>>> getJakeList(@Path("num") int num, @Path("page") int page);

    String BASE_URL="http://v.juhe.cn";
    String GET_NEWS="/toutiao/index";
    String APP_KEY="8fcf21bc49566f841ddbaf59f8daf5f0";

    String FACEADD_TEST="https://api-cn.faceplusplus.com/facepp/v3/compare";
    String FACE_ONE_TEST="https://api-cn.faceplusplus.com/facepp/v3/detect";
    String FACE_APP_KEY="K4GS-4GnW99Bjm1xOb2dM72FhEietrOn";
    String FACE_APP_SELECT="mTXuylR0CvyEAtNHcLcyELPQLPqSMghl";
}
