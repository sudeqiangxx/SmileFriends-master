package cn.com.sdq.smilefriends.commn.okhttp;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class StreamTool {

    /**
     * 将网络请求中的二进制数据转换为明文
     * @param in
     * @return
     */
    public static String decodeStream(InputStream in) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = in.read(buf)) != -1) {

                baos.write(buf, 0, len);
            }

            String temp = baos.toString();

            if (temp.contains("gbk")) {
                return baos.toString("gbk");
            } else if (temp.contains("utf-8")) {
                return baos.toString("utf-8");
            } else {
                return temp;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

}
