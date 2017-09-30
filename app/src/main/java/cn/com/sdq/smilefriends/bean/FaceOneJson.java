package cn.com.sdq.smilefriends.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sudeqiang on 2017/9/22.
 */

public class FaceOneJson implements Serializable {
    private String image_id;
    private String request_id;
    private String time_used;
    private List<Face> faces;

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getTime_used() {
        return time_used;
    }

    public void setTime_used(String time_used) {
        this.time_used = time_used;
    }

    public List<Face> getFaces() {
        return faces;
    }

    public void setFaces(List<Face> faces) {
        this.faces = faces;
    }

    public class Face{
        private String face_token;
        private Attributes attributes;

        public String getFace_token() {
            return face_token;
        }

        public void setFace_token(String face_token) {
            this.face_token = face_token;
        }

        public Attributes getAttributes() {
            return attributes;
        }

        public void setAttributes(Attributes attributes) {
            this.attributes = attributes;
        }

        @Override
        public String toString() {
            return "Face{" +
                    "face_token='" + face_token + '\'' +
                    ", attributes=" + attributes +
                    '}';
        }

        public class Attributes{
            private String gender;
            private String age;
            private Headpose headpose;
            private Smiling smiling;
            private Emotion emotion;
            private Facequality facequality;
            private Mouthstatus mouthstatus;
            private Skinstatus skinstatus;

            @Override
            public String toString() {
                return "Attributes{" +
                        "gender='" + gender + '\'' +
                        ", age='" + age + '\'' +
                        ", headpose=" + headpose +
                        ", smiling=" + smiling +
                        ", emotion=" + emotion +
                        ", facequality=" + facequality +
                        ", mouthstatus=" + mouthstatus +
                        ", skinstatus=" + skinstatus +
                        '}';
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
                this.age = age;
            }



            public Headpose getHeadpose() {
                return headpose;
            }

            public void setHeadpose(Headpose headpose) {
                this.headpose = headpose;
            }

            public Smiling getSmiling() {
                return smiling;
            }

            public void setSmiling(Smiling smiling) {
                this.smiling = smiling;
            }


            public Emotion getEmotion() {
                return emotion;
            }

            public void setEmotion(Emotion emotion) {
                this.emotion = emotion;
            }

            public Facequality getFacequality() {
                return facequality;
            }

            public void setFacequality(Facequality facequality) {
                this.facequality = facequality;
            }



            public Mouthstatus getMouthstatus() {
                return mouthstatus;
            }

            public void setMouthstatus(Mouthstatus mouthstatus) {
                this.mouthstatus = mouthstatus;
            }

            public Skinstatus getSkinstatus() {
                return skinstatus;
            }

            public void setSkinstatus(Skinstatus skinstatus) {
                this.skinstatus = skinstatus;
            }

            public class Emotion{
                private String anger;
                private String disgust;//：厌恶
                private String fear;//：恐惧
                private String happiness;//：高兴
                private String neutral;//：平静
                private String sadness;//：伤心
                private String surprise;//：惊讶

                public String getAnger() {
                    return anger;
                }

                public void setAnger(String anger) {
                    this.anger = anger;
                }

                public String getDisgust() {
                    return disgust;
                }

                public void setDisgust(String disgust) {
                    this.disgust = disgust;
                }

                public String getFear() {
                    return fear;
                }

                public void setFear(String fear) {
                    this.fear = fear;
                }

                public String getHappiness() {
                    return happiness;
                }

                public void setHappiness(String happiness) {
                    this.happiness = happiness;
                }

                public String getNeutral() {
                    return neutral;
                }

                public void setNeutral(String neutral) {
                    this.neutral = neutral;
                }

                public String getSadness() {
                    return sadness;
                }

                public void setSadness(String sadness) {
                    this.sadness = sadness;
                }

                public String getSurprise() {
                    return surprise;
                }

                public void setSurprise(String surprise) {
                    this.surprise = surprise;
                }
            }
            public class Facequality{
                private String value;//：值为人脸的质量判断的分数，是一个浮点数，范围 [0,100]，小数点后 3 位有效数字。
                private String threshold;//：表示人脸质量基本合格的一个阈值，超过该阈值的人脸适合用于人脸比对。

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }

                public String getThreshold() {
                    return threshold;
                }

                public void setThreshold(String threshold) {
                    this.threshold = threshold;
                }
            }
            public class Mouthstatus{
                private String surgical_mask_or_respirator;//：嘴部被医用口罩或呼吸面罩遮挡的置信度
                private String other_occlusion;//：嘴部被其他物体遮挡的置信度
                private String close;//：嘴部没有遮挡且闭上的置信度
                private String open;//：嘴部没有遮挡且张开的置信度

                public String getSurgical_mask_or_respirator() {
                    return surgical_mask_or_respirator;
                }

                public void setSurgical_mask_or_respirator(String surgical_mask_or_respirator) {
                    this.surgical_mask_or_respirator = surgical_mask_or_respirator;
                }

                public String getOther_occlusion() {
                    return other_occlusion;
                }

                public void setOther_occlusion(String other_occlusion) {
                    this.other_occlusion = other_occlusion;
                }

                public String getClose() {
                    return close;
                }

                public void setClose(String close) {
                    this.close = close;
                }

                public String getOpen() {
                    return open;
                }

                public void setOpen(String open) {
                    this.open = open;
                }
            }
            public class Skinstatus{
                private String health;//：健康
                private String stain;//：色斑
                private String acne;//：青春痘
                private String dark_circle;//：黑眼圈

                public String getHealth() {
                    return health;
                }

                public void setHealth(String health) {
                    this.health = health;
                }

                public String getStain() {
                    return stain;
                }

                public void setStain(String stain) {
                    this.stain = stain;
                }

                public String getAcne() {
                    return acne;
                }

                public void setAcne(String acne) {
                    this.acne = acne;
                }

                public String getDark_circle() {
                    return dark_circle;
                }

                public void setDark_circle(String dark_circle) {
                    this.dark_circle = dark_circle;
                }
            }
            public class Headpose{
                private String pitch_angle;//：抬头
                private String roll_angle;//：旋转（平面旋转）
                private String yaw_angle;//：摇头

                public String getPitch_angle() {
                    return pitch_angle;
                }

                public void setPitch_angle(String pitch_angle) {
                    this.pitch_angle = pitch_angle;
                }

                public String getRoll_angle() {
                    return roll_angle;
                }

                public void setRoll_angle(String roll_angle) {
                    this.roll_angle = roll_angle;
                }

                public String getYaw_angle() {
                    return yaw_angle;
                }

                public void setYaw_angle(String yaw_angle) {
                    this.yaw_angle = yaw_angle;
                }
            }
            public class Smiling{
                private String value;//：值为一个 [0,100] 的浮点数，小数点后3位有效数字。数值越大表示笑程度高。
                private String threshold;//：代表笑容的阈值，超过该阈值认为有笑容。

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }

                public String getThreshold() {
                    return threshold;
                }

                public void setThreshold(String threshold) {
                    this.threshold = threshold;
                }
            }

        }

    }
//    {
//        "image_id": "Dd2xUw9S/7yjr0oDHHSL/Q==",
//            "request_id": "1470472868,dacf2ff1-ea45-4842-9c07-6e8418cea78b",
//            "time_used": 752,
//            "faces": [{



//        "face_rectangle": {
//            "width": 140,
//                    "top": 89,
//                    "left": 104,
//                    "height": 141
//        },
//        "face_token": "ed319e807e039ae669a4d1af0922a0c8"
//    }]
//    }


    @Override
    public String toString() {
        return "FaceOneJson{" +
                "image_id='" + image_id + '\'' +
                ", request_id='" + request_id + '\'' +
                ", time_used='" + time_used + '\'' +
                ", faces=" + faces +
                '}';
    }
}
