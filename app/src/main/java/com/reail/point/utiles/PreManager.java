package com.reail.point.utiles;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.reail.point.Constant;
import com.reail.point.model.LoginUser;

public class PreManager {

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public PreManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setLoginUser(String password) {editor.putString(Constant.USER, password).apply(); }
    public String getLoginUser() {
        return sharedPreferences.getString(Constant.USER, "");
    }
    public LoginUser getUsersLogin() {return new Gson().fromJson(getLoginUser(), LoginUser.class); }
    public void setPassword(String password) {editor.putString(Constant.PASSWORD, password).apply();}
    public String getPassword() {
        return sharedPreferences.getString(Constant.PASSWORD, "");
    }
    public void setReliability(int reliability) { editor.putInt(Constant.RELIABILITY, reliability).apply();}
    public int getReliability() {
        return sharedPreferences.getInt(Constant.RELIABILITY, 1);
    }
    public void setPowerType(int power_type) { editor.putInt(Constant.POWERTYPE, power_type).apply();}
    public int getPowerType() {
        return sharedPreferences.getInt(Constant.POWERTYPE, 7);
    }
    public void setRadius(int radius) { editor.putInt(Constant.RADIUS, radius).apply();}
    public int getRadius() {
        return sharedPreferences.getInt(Constant.RADIUS, 3);
    }
	  public void setIsLogin(boolean is_login) { editor.putBoolean(Constant.IS_LOGIN, is_login).apply();}
    public boolean getIsLogin() {
        return sharedPreferences.getBoolean(Constant.IS_LOGIN, false);
    }
}
