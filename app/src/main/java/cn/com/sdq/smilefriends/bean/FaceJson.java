package cn.com.sdq.smilefriends.bean;

import java.io.Serializable;

/**
 * Created by sudeqiang on 2017/9/15.
 */

public class FaceJson implements Serializable {
//    {
//        "time_used": 473,
//            "confidence": 96.46,
//            "thresholds": {
//        "1e-3": 65.3,
//                "1e-5": 76.5,
//                "1e-4": 71.8
//    },
//        "request_id": "1469761507,07174361-027c-46e1-811f-ba0909760b18"
//
    private String time_used;
    private String confidence;
    private String request_id;
    private Data thresholds;

    public String getTime_used() {
        return time_used;
    }

    public void setTime_used(String time_used) {
        this.time_used = time_used;
    }

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public Data getThresholds() {
        return thresholds;
    }

    public void setThresholds(Data thresholds) {
        this.thresholds = thresholds;
    }

    public class Data{
        private String le_3;
        private String le_4;
        private String le_5;

        public String getLe_3() {
            return le_3;
        }

        public void setLe_3(String le_3) {
            this.le_3 = le_3;
        }

        public String getLe_4() {
            return le_4;
        }

        public void setLe_4(String le_4) {
            this.le_4 = le_4;
        }

        public String getLe_5() {
            return le_5;
        }

        public void setLe_5(String le_5) {
            this.le_5 = le_5;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "le_3='" + le_3 + '\'' +
                    ", le_4='" + le_4 + '\'' +
                    ", le_5='" + le_5 + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "FaceJson{" +
                "time_used='" + time_used + '\'' +
                ", confidence='" + confidence + '\'' +
                ", request_id='" + request_id + '\'' +
                ", thresholds=" + thresholds +
                '}';
    }
}
