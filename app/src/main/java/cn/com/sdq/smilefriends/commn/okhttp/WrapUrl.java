package cn.com.sdq.smilefriends.commn.okhttp;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;

import cn.com.sdq.smilefriends.commn.APIService;


/**
 * @author sudeqiang
 * @time 2017/6/22 14:02
 * @updateAuthor $Author$
 * @updateDate $Date$
 */
public class WrapUrl {

    public static String wrap(String url) {
        return APIService.BASE_URL + url;
    }

    /**
     * 拼接get请求url
     *
     * @param url
     * @param params
     */
    public static String getUrl(String url, HashMap<String, String> params) {
        String wrapUrl = APIService.BASE_URL + url;

        String finalUrl = null;
        StringBuffer sb = null;
        // 添加url参数
        if (params != null) {
            Iterator<String> it = params.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                String value = params.get(key);


                if (sb == null) {
                    sb = new StringBuffer();
                    sb.append("?");
                } else {
                    sb.append("&");
                }
                sb.append(key);
                sb.append("=");

                try {
                    String encodeValue = URLEncoder.encode(value, "utf-8");
                    sb.append(encodeValue);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            if (wrapUrl.contains("?")) {

                int i = StringUtils.indexOf(wrapUrl, "?");
                String substring = wrapUrl.substring(i);
                finalUrl = wrapUrl.replace(substring, sb.toString());

            } else {
                finalUrl = wrapUrl + sb.toString();
            }

        }
        return finalUrl;
    }
}
