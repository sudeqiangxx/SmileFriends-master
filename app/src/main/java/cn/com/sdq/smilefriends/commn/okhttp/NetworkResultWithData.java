package cn.com.sdq.smilefriends.commn.okhttp;

/**
 * @author sudeqiang
 * @time 2017/6/8 09:04
 * @updateAuthor $Author$
 * @updateDate $Date$
 */
public class NetworkResultWithData {

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private String message;
        private String data;
        private int code;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }
}
