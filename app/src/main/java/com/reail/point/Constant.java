package com.reail.point;

import com.reail.point.model.LocData;
import com.reail.point.model.LocationData;
import com.reail.point.model.LoginUser;

import java.util.List;

public class Constant {
    static final String AUTH_TOKEN = "authToken";
    public static final String PREF_NAME = "Signup_pref";
    public static final String RADIUS = "radius";
    public static final String RELIABILITY = "reliability";
    public static final String POWERTYPE = "power_type";
    public static final String USER = "user";
    public static final String PASSWORD = "password";
    public static final String DEVICE_ID = "DEVICE_ID";
    public static final String DEVICE_VERSION = "DEVICE_VERSION";
    public static final String DEVICE_NAME = "DEVICE_NAME";
    public static final String LOCATION = "location";
    public static final String LAT = "lat";
    public static final String LOG = "log";
	public static final String IS_LOGIN = "is_login";
    public static final String DATEFORMAT = "yyyy-MM-dd HH:mm:ss Z";
    public static String TITLE = "About Us";
    public static boolean subscribed = true;
    public static int radius = 3;
    public static LocationData singalItemData=null;
    public static LoginUser singalUserData=null;
    public static final String BASE_URL_LINK = "http://mtf.ydns.eu/";
    // public static final String MAIN_BASE_URL="http://mtf.ydns.eu/index.php/api/";
    public static final String MAIN_BASE_URL = "https://videomessagetofuture.com/api/";
}
