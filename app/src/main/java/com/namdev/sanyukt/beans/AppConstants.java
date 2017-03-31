package com.namdev.sanyukt.beans;

/**
 * Created by Harish on 1/25/2017.
 */
public class AppConstants {
    //    public static final String BASE_URL = "http://192.168.43.120:8080/snykut/webapi/snykut/";

    public static final String BASE_URL = "http://10.81.234.10/SanyuktPhpAdmin/admin/mainadmin/api.php";
    public static final String NEW_USER_REGISTRATION_URL = BASE_URL + "";
    public static final String USER_LOGIN = BASE_URL + "api.php";
    public static final String USER_DETAILS = BASE_URL + "user/user_details";
    public static final String USER_VALIDATE = BASE_URL + "user/validate_user";
    public static final String NEW_MEMBER = BASE_URL + "user/new_member";

    public static final String MEMBER_DETAILS = BASE_URL + "user/member_details";

    public static final String SUCCESS = "success";
    public static final String GENDER = "GENDER";

    public static final String USER_LOGIN_ACTION = "USER_LOGIN";
    public static final String USER_CREATE_ACTION = "USER_CREATE";
    public static final String GET_MEMBER_LIST = "GET_MEMBER_LIST";
    public static final String MEMBER_CREATE = "MEMBER_CREATE";


    public static long COUNT_DOWN_TIME_OPT = 1000 * 100;
    public static long COUNT_DOWN_TIME_INTERVAL = 1000;
    public static String MemberId = "memberId";
}
