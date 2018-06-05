package model;

/**
 * Created by LockyLuo on 2017/9/20.
 *
 */

public class ApiUrl {
    public static void setClassUrl(String classUrl) {
        CLASS_URL = classUrl;
    }
    public static String BASE_URL ="https://api.bmob.cn/1/";
    public static String CLASS_URL = "https://api.bmob.cn/1/classes/";
    public static String DrugDataBase_URL="http://bmob-cdn-19708.b0.upaiyun.com/2018/06/05/6725171f40562989804615781e094caf.db";

    public static class Get {

        public static String LOGIN_USER_URL = BASE_URL + "login";//登录
        public static String DOCTOR_RSA_URL = CLASS_URL + "DoctorRsa";
        public static String DOCTOR_URL = BASE_URL + "users";
        public static String Drug_URL = CLASS_URL + "drugDB";
        public static String DrugDatabase_URL = CLASS_URL + "drugDatabase";
    }

    public static class Post{
        public static String REGISTER_USER_URL = BASE_URL + "User";
        public static String Patient_URL = CLASS_URL + "Patient";
        public static String PatientInfo_URL = CLASS_URL + "PatientInfo";
        public static String REGISTER_Info = CLASS_URL + "RegisterInfo";
    }

    public static class Put{
        public static final String DEL_DYNAMIC_FAKE_URL = CLASS_URL + "dynamic/batchDelete";


    }

    public static class Del{
        public static final String DEL_ATTEND_URL = CLASS_URL + "attend/";

    }
}
