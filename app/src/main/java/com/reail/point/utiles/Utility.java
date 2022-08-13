package com.reail.point.utiles;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.reail.point.Constant;
import com.reail.point.MapsMarker;
import com.reail.point.R;
import com.reail.point.model.LocationData;
import com.reail.point.model.Locations;
import com.reail.point.model.User;

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

    public static long setDiff(Date d1, Date d2) {
        long difference = Math.abs(d1.getTime() - d2.getTime());
        return difference / (24 * 60 * 60 * 1000);
    }

    public static List<User> getItems(List<LocationData> locData) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < locData.size(); i++) {
            if (Constant.subscribed) {
                LatLng loc = new LatLng(locData.get(i).getL().get(0), locData.get(i).getL().get(1));
                users.add(new User(locData.get(i).getName(), loc, Utility.subUser(locData.get(i))));
            } else {
                LatLng loc = new LatLng(locData.get(i).getL().get(0), locData.get(i).getL().get(1));
                users.add(new User(locData.get(i).getName(), loc, Utility.getimgRes(locData.get(i))));
            }
        }
        return users;
    }

    public static Locations getItems(LocationData locData, String rating) {
        Locations users = new Locations();
        users.setConnector1Type(locData.getConnector1Type());
        users.setPaymentRequiredDetails(locData.getPaymentRequiredDetails());
        users.setDeviceOwnerWebsite(locData.getDeviceOwnerWebsite());
        users.setHydrogen(locData.getHydrogen());
        users.setConnector4Status(locData.getConnector4Status());
        users.setSubscriptionRequired(locData.getSubscriptionRequired());
        users.setLatitude(locData.getLatitude());
        users.setLocationType(locData.getLocationType());
        users.setOutputKW(locData.getOutputKW());
        users.setPaymentRequired(locData.getPaymentRequired());
        users.setLocationLongDescription(locData.getLocationLongDescription());
        users.setConnector2OutputKW(locData.getConnector2OutputKW());
        users.setConnector3Type(locData.getConnector3Type());
        users.setPointsAtServiceStations(locData.getPointsAtServiceStations());
        users.setReliabilityRating(rating);
        users.setLongitude(locData.getLongitude());
        users.setDeviceControllerTelephoneNo(locData.getDeviceControllerTelephoneNo());
        users.setHomeNetwork(locData.getHomeNetwork());
        users.setG(locData.getG());
        users.setPostcode(locData.getPostcode());
        users.setLiveData(locData.getLiveData());
        users.setConnector3OutputKW(locData.getConnector3OutputKW());
        users.setConnector4Type(locData.getConnector4Type());
        users.setConnector4OutputKW(locData.getConnector4OutputKW());
        users.setL(locData.getL());
        users.setConnector3Status(locData.getConnector3Status());
        users.setReliaPointNetworkID(locData.getReliaPointNetworkID());
        users.setConnector2Type(locData.getConnector2Type());
        users.setDeviceNetworks(locData.getDeviceNetworks());
        users.setName(locData.getName());
        users.setPointsInCarParks(locData.getPointsInCarParks());
        users.setChargeDeviceStatus(locData.getChargeDeviceStatus());
        users.setConnector1Status(locData.getConnector1Status());
        return users;
    }

    public static LocationData getItemSearch(List<LocationData> locData, String value) {
        LocationData users = null;
        for (int i = 0; i < locData.size(); i++) {
            if (locData.get(i).getName().equals(value)) {
                users = locData.get(i);
            }
        }
        return users;
    }
    public static int getItemIndex(List<LocationData> locData, String value) {
        int index = 0;
        for (int i = 0; i < locData.size(); i++) {
            if (locData.get(i).getName().equals(value)) {
                index = i;
            }
        }
        return index;
    }
    public static BitmapDescriptor BitmapFromVector(Context context, int vectorResId) {
        // below line is use to generate a drawable.
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        // below line is use to set bounds to our vector drawable.
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        // below line is use to create a bitmap for our
        // drawable which we have added.
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        // below line is use to add bitmap in our canvas.
        Canvas canvas = new Canvas(bitmap);
        // below line is use to draw our
        // vector drawable in canvas.
        vectorDrawable.draw(canvas);
        // after generating our bitmap we are returning our bitmap.
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }


    public static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    public static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    public static List<LocationData> getfilterdata(List<LocationData> locData, int available) {
        List<LocationData> result = new ArrayList<>();
        Log.d(" Input Array Size :: ", locData.size() + "");
        if (available == 1) {
            result = locData;
        } else if (available == 2) {
            for (int i = 0; i < locData.size(); i++) {
                try {
                    if (locData.get(i).getReliabilityRating() == null || locData.get(i).getReliabilityRating().equals("")) {
                        int a = Utility.getCompanyReliaablityPoint(locData.get(i).getDeviceNetworks());
                        if (a <= 3) {
                            result.add(locData.get(i));
                        }
                    } else {
                        if (locData.get(i).getReliabilityRating().equals("1"))
                            result.add(locData.get(i));
                    }
                } catch (Exception e) {
                    Log.e("Error :: ", e.getMessage());
                }
            }
        } else if (available == 3) {
            for (int i = 0; i < locData.size(); i++) {
                if (locData.get(i).getReliabilityRating() == null || locData.get(i).getReliabilityRating().equals("")) {
                    int a = Utility.getCompanyReliaablityPoint(locData.get(i).getDeviceNetworks());
                    if (a == 4 || a == 5 || a == 6) {
                        result.add(locData.get(i));
                    }
                } else {
                    if (locData.get(i).getReliabilityRating().equals("2"))
                        result.add(locData.get(i));
                }
            }
        } else if (available == 4) {
            for (int i = 0; i < locData.size(); i++) {
                if (locData.get(i).getReliabilityRating() == null || locData.get(i).getReliabilityRating().equals("")) {
                    int a = Utility.getCompanyReliaablityPoint(locData.get(i).getDeviceNetworks());
                    if (a >= 7) {
                        result.add(locData.get(i));
                    }
                } else {
                    if (locData.get(i).getReliabilityRating().equals("3"))
                        result.add(locData.get(i));
                }
            }
        }
        Log.d(" Output Array Size :: ", result.size() + "");
        //Utility.getItems(locData,"locData");
        return result;
    }

    public static int subUser(LocationData value) {
        int res = R.drawable.green43;
        int currentCapacity1 = 0;
        try {
            currentCapacity1 = Integer.parseInt(value.getOutputKW());
        } catch (Exception e) {
        }
        int currentCapacity2 = 0;
        try {
            currentCapacity2 = Integer.parseInt(value.getConnector2OutputKW());
        } catch (Exception e) {
        }
        int currentCapacity3 = 0;
        try {
            currentCapacity3 = Integer.parseInt(value.getConnector3OutputKW());
        } catch (Exception e) {
        }
        int currentCapacity4 = 0;
        try {
            currentCapacity4 = Integer.parseInt(value.getConnector4OutputKW());
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
        try {
            String rating = value.getReliabilityRating();
            if (rating.equalsIgnoreCase("")) {
                int companyRatingValue = getCompanyReliaablityPoint(value.getDeviceNetworks());
                if (companyRatingValue >= 0 && companyRatingValue <= 4) {
                    if (maxValue <= 7) {
                        res = R.drawable.amber7;
                    } else if (maxValue > 7 && maxValue <= 22) {
                        res = R.drawable.amber22;
                    } else if (maxValue > 22 && maxValue <= 43) {
                        res = R.drawable.amber43;
                    } else if (maxValue > 43) {
                        res = R.drawable.amber50pluse;
                    }
                } else if (companyRatingValue > 4 && companyRatingValue <= 6) {
                    if (maxValue <= 7) {
                        res = R.drawable.amber7;
                    } else if (maxValue > 7 && maxValue <= 22) {
                        res = R.drawable.amber22;
                    } else if (maxValue > 22 && maxValue <= 43) {
                        res = R.drawable.amber43;
                    } else if (maxValue > 43) {
                        res = R.drawable.amber50pluse;
                    }

                } else {
                    if (maxValue <= 7) {
                        res = R.drawable.green7;
                    } else if (maxValue > 7 && maxValue <= 22) {
                        res = R.drawable.green22;
                    } else if (maxValue > 22 && maxValue <= 43) {
                        res = R.drawable.green43;
                    } else if (maxValue > 43) {
                        res = R.drawable.green50pluse;
                    }
                }
            } else if (rating.equalsIgnoreCase("1")) {
                if (maxValue <= 7) {
                    res = R.drawable.red7;
                } else if (maxValue > 7 && maxValue <= 22) {
                    res = R.drawable.red22;
                } else if (maxValue > 22 && maxValue <= 43) {
                    res = R.drawable.red43;
                } else if (maxValue > 43) {
                    res = R.drawable.red50pluse;
                }
            } else if (rating.equalsIgnoreCase("2")) {
                if (maxValue <= 7) {
                    res = R.drawable.amber7;
                } else if (maxValue > 7 && maxValue <= 22) {
                    res = R.drawable.amber22;
                } else if (maxValue > 22 && maxValue <= 43) {
                    res = R.drawable.amber43;
                } else if (maxValue > 43) {
                    res = R.drawable.amber50pluse;
                }
            } else if (rating.equalsIgnoreCase("3")) {
                if (maxValue <= 7) {
                    res = R.drawable.green7;
                } else if (maxValue > 7 && maxValue <= 22) {
                    res = R.drawable.green22;
                } else if (maxValue > 22 && maxValue <= 43) {
                    res = R.drawable.green43;
                } else if (maxValue > 43) {
                    res = R.drawable.green50pluse;
                }
            }
        } catch (Exception e) {
            Log.e("Error ::", e.getLocalizedMessage());
        }
        return res;
    }

    public static int getCompanyReliaablityPoint(String chargertype) {

        int companyRatingValue = 0;
        if (chargertype.contains("Tesla")) {
            companyRatingValue = 8;
        } else if (chargertype.contains("InstaVolt Ltd")) {
            companyRatingValue = 9;
        } else if (chargertype.contains("Osprey")) {
            companyRatingValue = 7;
        } else if (chargertype.contains("Shell Recharge")) {
            companyRatingValue = 7;
        } else if (chargertype.contains("POD Point")) {
            companyRatingValue = 6;
        } else if (chargertype.contains("Grid serve")) {
            companyRatingValue = 6;
        } else if (chargertype.contains("Ionity")) {
            companyRatingValue = 6;
        } else if (chargertype.contains("Engie")) {
            companyRatingValue = 6;
        } else if (chargertype.contains("ChargePlace Scotland")) {
            companyRatingValue = 3;
        } else if (chargertype.contains("The GeniePoint Network")) {
            companyRatingValue = 6;
        } else if (chargertype.contains("Charge Your Car")) {
            companyRatingValue = 3;
        } else if (chargertype.contains("Source London")) {
            companyRatingValue = 6;
        } else if (chargertype.contains("Chargemaster (POLAR)") || chargertype.contains("Plugged-in Midlands") || chargertype.contains("Milton Keyne") || chargertype.contains("BP pulse")) {
            companyRatingValue = 5;
        } else if (chargertype.contains("GMEV")) {
            companyRatingValue = 7;
        } else if (chargertype.contains("ecars ESB")) {
            companyRatingValue = 7;
        } else if (chargertype.contains("Ecotricity (Electric Highway")) {
            companyRatingValue = 7;
        } else {
            companyRatingValue = 7;
        }
        return companyRatingValue;
    }

    public static int getimgRes(LocationData value) {
        int res = R.drawable.green43;
        int currentCapacity1 = 0;
        try {
            currentCapacity1 = Integer.parseInt(value.getOutputKW());
        } catch (Exception e) {
        }
        int currentCapacity2 = 0;
        try {
            currentCapacity2 = Integer.parseInt(value.getConnector2OutputKW());
        } catch (Exception e) {
        }
        int currentCapacity3 = 0;
        try {
            currentCapacity3 = Integer.parseInt(value.getConnector3OutputKW());
        } catch (Exception e) {
        }
        int currentCapacity4 = 0;
        try {
            currentCapacity4 = Integer.parseInt(value.getConnector4OutputKW());
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
        try {
            if (value.getChargeDeviceStatus().equalsIgnoreCase("In service")) {
                if (maxValue <= 7) {
                    res = R.drawable.green7;
                } else if (maxValue > 7 && maxValue <= 22) {
                    res = R.drawable.green22;
                } else if (maxValue > 22 && maxValue <= 43) {
                    res = R.drawable.green43;
                } else if (maxValue > 43) {
                    res = R.drawable.green50pluse;
                }
            } else {
                if (maxValue <= 7) {
                    res = R.drawable.red7;
                } else if (maxValue > 7 && maxValue <= 22) {
                    res = R.drawable.red22;
                } else if (maxValue > 22 && maxValue <= 43) {
                    res = R.drawable.red43;
                } else if (maxValue > 43) {
                    res = R.drawable.red50pluse;
                }
            }
        } catch (Exception e) {
            Log.e("Error ::", e.getLocalizedMessage());
        }
        return res;
    }

    public static List<LocationData> listsort(int power, List<LocationData> list) {
        List<LocationData> list_final = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
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

            if (maxValue <= power) {
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
