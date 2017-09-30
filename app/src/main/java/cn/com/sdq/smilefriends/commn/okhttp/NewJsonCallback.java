package cn.com.sdq.smilefriends.commn.okhttp;

import com.lzy.okgo.callback.AbsCallback;

import cn.com.sdq.smilefriends.util.utils.T;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by sudeqiang on 2017/9/15.
 */

public class NewJsonCallback extends AbsCallback<T> {
    @Override
    public T convertSuccess(Response response) throws Exception {
        return null;
    }

    @Override
    public void onSuccess(T t, Call call, Response response) {

    }
}
