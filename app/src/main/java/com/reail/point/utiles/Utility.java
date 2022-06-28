package com.reail.point.utiles;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import com.reail.point.Constant;
import com.reail.point.MapsMarker;
import com.reail.point.model.LocationData;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Utility {
	public static ProgressDialog pDialogue;
    public static String getexpiryDate() {
        Date today = new Date();
        Calendar cal = new GregorianCalendar();
        cal.setTime(today);
        cal.add(Calendar.DAY_OF_MONTH, 90);
        Date today90 = cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat(Constant.DATEFORMAT);
        return dateFormat.format(today90);
    }
    public static String getLoginDate() {
        DateFormat dateFormat = new SimpleDateFormat(Constant.DATEFORMAT);
        return dateFormat.format(new Date());
    }
    public static Date getDate(String date) throws ParseException {
        return new SimpleDateFormat(Constant.DATEFORMAT).parse(date);
    }
    public static long setDiff(Date d1 , Date d2){
        long difference = Math.abs(d1.getTime() - d2.getTime());
        return difference / (24 * 60 * 60 * 1000);
    }

    public static List<LocationData> listsort(int power, List<LocationData> list){
        List<LocationData> list_final=new ArrayList<>();
        for (int i=0;i<list.size();i++){
            int currentCapacity1 = 0;
            try {
                currentCapacity1 = Integer.parseInt(list.get(i).getOutputKW());
            } catch (Exception e) {
            }
            int currentCapacity2 = 0;
            try {
                currentCapacity2 = Integer.parseInt(list.get(i).getConnector2OutputKW());
            } catch (Exception e) {
            }
            int currentCapacity3 = 0;
            try {
                currentCapacity3 = Integer.parseInt(list.get(i).getConnector3OutputKW());
            } catch (Exception e) {
            }
            int currentCapacity4 = 0;
            try {
                currentCapacity4 = Integer.parseInt(list.get(i).getConnector4OutputKW());
            } catch (Exception e) {
            }
            int maxValue = currentCapacity1;
            if (currentCapacity2 > maxValue) {
                maxValue = currentCapacity2;
            }
            if (currentCapacity3 > maxValue) {
                maxValue = currentCapacity3;
            }
            if (currentCapacity4 > maxValue) {
                maxValue = currentCapacity4;
            }

            if(maxValue<=power){
                list_final.add(list.get(i));
            }
        }
        return list_final;
    }
public static void showProgressDialogue(Context context, String tittle, String msg) {
        if (context != null) {
            ProgressDialog progressDialog = pDialogue;
            if (progressDialog == null || !progressDialog.isShowing()) {
                try {
                    ProgressDialog progressDialog2 = new ProgressDialog(context);
                    pDialogue = progressDialog2;
                    progressDialog2.setTitle(tittle);
                    pDialogue.setMessage(msg);
                    pDialogue.setCanceledOnTouchOutside(false);
                    pDialogue.show();
                } catch (Exception e) {
                }
            } else {
                try {
                    pDialogue.setTitle(tittle);
                    pDialogue.setMessage(msg);
                } catch (Exception e) {
                }
            }
            try {
                pDialogue.setCanceledOnTouchOutside(true);
                pDialogue.setCancelable(true);
            } catch (Exception e) {
            }
        }
    }
	public static void cancelProgressDialogue() {
        try {
            ProgressDialog progressDialog = pDialogue;
            if (progressDialog != null && progressDialog.isShowing()) {
                pDialogue.dismiss();
                pDialogue = null;
            }
        } catch (Exception e) {
        }
    }
}
