package com.reail.point;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.model.LatLng;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

public class PointDetails extends Fragment {

    private View v;
    private SimpleRatingBar appCompatRatingBar;
    private TextView tv_point_name, tv_point_type, tv_point_status, tv_url, tv_phone, tv_zip, tv_type1, tv_type1_status;
    private TextView tv_type1_power, tv_type2, tv_type2_status, tv_type2_power;
    private TextView tv_type3, tv_type3_status, tv_type3_power;
    private TextView tv_type4, tv_type4_status, tv_type4_power;
    private LinearLayout ll_type3, ll_type4;
    private ImageView iv_back,tvMap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_point_details, container, false);
        tv_point_name = v.findViewById(R.id.tv_point_name);
        tv_point_type = v.findViewById(R.id.tv_point_type);
        tv_point_status = v.findViewById(R.id.tv_point_status);
        tvMap = v.findViewById(R.id.tvMap);
        appCompatRatingBar = v.findViewById(R.id.ratingBar);
        appCompatRatingBar.setStarsSeparation(60, Dimension.DP);
        appCompatRatingBar.setNumberOfStars(3);
        //display the current rating value in the result (textview) automatically
        appCompatRatingBar.setOnRatingBarChangeListener(new SimpleRatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(SimpleRatingBar ratingBar, float rating,boolean fromUser) {
                //appCompatRatingBar.setRating(rating);
                Toast.makeText(getContext(), ""+(int)rating, Toast.LENGTH_SHORT).show();
            }
        });
        iv_back = v.findViewById(R.id.iv_back);

        ll_type3 = v.findViewById(R.id.ll_type3);
        ll_type4 = v.findViewById(R.id.ll_type4);

        tv_url = v.findViewById(R.id.tv_url);
        tv_phone = v.findViewById(R.id.tv_phone);
        tv_zip = v.findViewById(R.id.tv_zip);

        tv_type1 = v.findViewById(R.id.tv_type1);
        tv_type1_status = v.findViewById(R.id.tv_type1_status);
        tv_type1_power = v.findViewById(R.id.tv_power1);

        tv_type2 = v.findViewById(R.id.tv_type2);
        tv_type2_status = v.findViewById(R.id.tv_type2_status);
        tv_type2_power = v.findViewById(R.id.tv_power2);

        tv_type3 = v.findViewById(R.id.tv_type3);
        tv_type3_status = v.findViewById(R.id.tv_type3_status);
        tv_type3_power = v.findViewById(R.id.tv_power3);

        tv_type4 = v.findViewById(R.id.tv_type4);
        tv_type4_status = v.findViewById(R.id.tv_type4_status);
        tv_type4_power = v.findViewById(R.id.tv_power4);
        iv_back.setOnClickListener(v12 -> FlowOrganizer.getInstance().popUpBackTo(1));
        tvMap.setOnClickListener(v1 -> FlowOrganizer.getInstance().add(new MapFragment(new LatLng(Constant.singalItemData.getL().get(0),Constant.singalItemData.getL().get(1)))));
        initUI();
        return v;
    }

    public void initUI() {

        tv_point_name.setText(Constant.singalItemData.getName());
        tv_point_type.setText(Constant.singalItemData.getLocationType());
        tv_point_status.setText(Constant.singalItemData.getChargeDeviceStatus());

        tv_url.setText(Constant.singalItemData.getDeviceOwnerWebsite());
        tv_phone.setText(Constant.singalItemData.getDeviceControllerTelephoneNo());
        tv_zip.setText(Constant.singalItemData.getPostcode());

        int currentCapacity1 = 0;
        try {
            currentCapacity1 = Integer.parseInt(Constant.singalItemData.getOutputKW());
        } catch (Exception e) {
        }
        int currentCapacity2 = 0;
        try {
            currentCapacity2 = Integer.parseInt(Constant.singalItemData.getConnector2OutputKW());
        } catch (Exception e) {
        }
        int currentCapacity3 = 0;
        try {
            currentCapacity3 = Integer.parseInt(Constant.singalItemData.getConnector3OutputKW());
        } catch (Exception e) {
        }
        int currentCapacity4 = 0;
        try {
            currentCapacity4 = Integer.parseInt(Constant.singalItemData.getConnector4OutputKW());
        } catch (Exception e) {
        }
        String rating = Constant.singalItemData.getReliabilityRating();
        if (rating.equalsIgnoreCase("")) {
            appCompatRatingBar.setRating(0);
        } else if (rating.equalsIgnoreCase("1")) {
            appCompatRatingBar.setRating(1);
        } else if (rating.equalsIgnoreCase("2")) {
            appCompatRatingBar.setRating(2);
        } else if (rating.equalsIgnoreCase("3")) {
            appCompatRatingBar.setRating(3);
        }
        if (currentCapacity1 != 0) {
            try {
                tv_type1.setText("1." + Constant.singalItemData.getConnector1Type());
                tv_type1_status.setText(Constant.singalItemData.getConnector1Status());
                tv_type1_power.setText(String.valueOf(Constant.singalItemData.getOutputKW()) + " kw");
            } catch (Exception e) {
                Log.e("Error1 :: ", e.getLocalizedMessage());
            }
        }

        if (currentCapacity2 != 0) {
            try {
                tv_type2.setText("2." + Constant.singalItemData.getConnector2Type());
                tv_type2_status.setText("");
                tv_type2_power.setText(String.valueOf(Constant.singalItemData.getConnector2OutputKW()) + " kw");
            } catch (Exception e) {
                Log.e("Error2 :: ", e.getLocalizedMessage());
            }
        }
        if (currentCapacity3 != 0) {
            ll_type3.setVisibility(View.VISIBLE);
            try {
                tv_type3.setText("3." + Constant.singalItemData.getConnector3Type());
                tv_type3_status.setText(Constant.singalItemData.getConnector3Status());
                tv_type3_power.setText(String.valueOf(Constant.singalItemData.getConnector3OutputKW()) + " kw");
            } catch (Exception e) {
                Log.e("Error2 :: ", e.getLocalizedMessage());
            }
        } else {
            ll_type3.setVisibility(View.GONE);
        }
        if (currentCapacity4 != 0) {
            ll_type4.setVisibility(View.VISIBLE);
            try {
                tv_type4.setText("4." + Constant.singalItemData.getConnector4Type());
                tv_type4_status.setText(Constant.singalItemData.getConnector4Status());
                tv_type4_power.setText(String.valueOf(Constant.singalItemData.getConnector4OutputKW()) + " kw");
            } catch (Exception e) {
                Log.e("Error2 :: ", e.getLocalizedMessage());
            }
        } else {
            ll_type4.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        ll_type4.setVisibility(View.GONE);
        ll_type3.setVisibility(View.GONE);
    }
}

