package cn.com.sdq.smilefriends.commn.okhttp;

/**
 * @author sudeqiang
 * @time 2017/06/08 09:28
 * @updateAuthor $Author$
 * @updateDate $Date$
 */
public class NetWorkResult {


    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private String message;
        private int code;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }
}
