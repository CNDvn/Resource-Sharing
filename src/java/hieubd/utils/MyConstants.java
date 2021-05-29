/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieubd.utils;

import java.io.Serializable;

/**
 *
 * @author CND
 */
public class MyConstants implements Serializable {

    //paging
    public static final int TOTAL_ITEM_IN_PAGE = 20;
    //Role
    public static final int ROLE_EMPLOYEE = 2,
            ROLE_MANAGE = 1;

    //Status
    public static final int STATUS_NEW = 1,
            STATUS_ACTIVE = 2,
            STATUS_INACTIVE = 3,
            STATUS_ACCEPT = 4,
            STATUS_DELETE = 5;

    //Recaptcha google
    public static final String SITE_KEY = "6Lcdm9EaAAAAANh8CbwBuEUfA2OHoZTjfOtdGiwq";

    public static final String SECRET_KEY = "6Lcdm9EaAAAAALB8BMstte6-h83XoAoPIDGLgEeK";

    //OAuth Google
    public static final String GOOGLE_CLIENT_ID = "225507771210-07p34499hvol7l70ojv69di12fd1tja7.apps.googleusercontent.com";
    public static final String GOOGLE_CLIENT_SECRET = "KtZSjiFbjjr0tawa2k8lrbS6";
    public static final String GOOGLE_REDIRECT_URI = "http://localhost:8084/ResourceSharing/MainController?btnAction=LoginGoogle";
    public static final String GOOGLE_LINK_GET_TOKEN = "https://accounts.google.com/o/oauth2/token";
    public static final String GOOGLE_LINK_GET_USER_INFO = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=";
    public static final String GOOGLE_GRANT_TYPE = "authorization_code";

    //Send Email
    public static final String EMAIL = "resourcesharinglab16@gmail.com";
    public static final String PASSWORD_EMAIL = "duchieu123";
    public static final int TIME_OUT_CONFIRM_CODE = 5;

}
