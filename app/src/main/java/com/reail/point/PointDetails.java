package com.reail.point;

import android.app.ProgressDialog;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.reail.point.model.LocationData;
import com.reail.point.model.Locations;
import com.reail.point.model.LoginUser;
import com.reail.point.utiles.Utility;

import java.util.List;
import java.util.Objects;

public class PointDetails extends Fragment {

    private View v;
    private SimpleRatingBar appCompatRatingBar;
    private TextView tv_point_name, tv_point_type, tv_point_status, tv_url, tv_phone, tv_zip, tv_type1, tv_type1_status;
    private TextView tv_type1_power, tv_type2, tv_type2_status, tv_type2_power;
    private TextView tv_type3, tv_type3_status, tv_type3_power;
    private TextView tv_type4, tv_type4_status, tv_type4_power;
    private LinearLayout ll_type3, ll_type4;
    private ImageView iv_back, tvMap, iv1, iv2, iv3, iv4;
    public LocationData itemData = null;
    public List<LocationData> itemsData;
    private MapsMarker act;

    public void getValues(List<LocationData> itemsData, LocationData itemData,MapsMarker act) {
        this.itemsData = itemsData;
        this.itemData = itemData;
        this.act = act;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_point_details, container, false);
        tv_point_name = v.findViewById(R.id.tv_point_name);
        tv_point_type = v.findViewById(R.id.tv_point_type);
        tv_point_status = v.findViewById(R.id.tv_point_status);
        tvMap = v.findViewById(R.id.tvMap);
        iv1 = v.findViewById(R.id.iv1);
        iv2 = v.findViewById(R.id.iv2);
        iv3 = v.findViewById(R.id.iv3);
        iv4 = v.findViewById(R.id.iv4);
        appCompatRatingBar = v.findViewById(R.id.ratingBar);
        appCompatRatingBar.setStarsSeparation(60, Dimension.DP);
        appCompatRatingBar.setNumberOfStars(3);
        appCompatRatingBar.setStepSize(1f);
        appCompatRatingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            int a = 0;
            try {
                a = Integer.parseInt(itemData.getReliabilityRating());
            } catch (Exception e) {
            }
            if (a != rating) {
                Update_reating((int) rating + "");
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
        iv_back.setOnClickListener(v12 ->{
            FlowOrganizer.getInstance().add(new MapsMarker());
        });
        tvMap.setOnClickListener(v1 -> FlowOrganizer.getInstance().add(new MapFragment(new LatLng(itemData.getL().get(0), itemData.getL().get(1)),itemData)));
        initUI();
        return v;
    }

    public void initUI() {

        tv_point_name.setText(itemData.getName());
        tv_point_type.setText(itemData.getLocationType());
        tv_point_status.setText(itemData.getChargeDeviceStatus());

        tv_url.setText(itemData.getDeviceOwnerWebsite());
        tv_phone.setText(itemData.getDeviceControllerTelephoneNo());
        tv_zip.setText(itemData.getPostcode());

        int currentCapacity1 = 0;
        try {
            currentCapacity1 = Integer.parseInt(itemData.getOutputKW());
        } catch (Exception e) {
        }
        int currentCapacity2 = 0;
        try {
            currentCapacity2 = Integer.parseInt(itemData.getConnector2OutputKW());
        } catch (Exception e) {
        }
        int currentCapacity3 = 0;
        try {
            currentCapacity3 = Integer.parseInt(itemData.getConnector3OutputKW());
        } catch (Exception e) {
        }
        int currentCapacity4 = 0;
        try {
            currentCapacity4 = Integer.parseInt(itemData.getConnector4OutputKW());
        } catch (Exception e) {
        }
        String rating = itemData.getReliabilityRating();
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
                tv_type1.setText("1." + itemData.getConnector1Type());
                tv_type1_status.setText(itemData.getConnector1Status());
                tv_type1_power.setText(String.valueOf(itemData.getOutputKW()) + " kw");
                iv1.setImageDrawable(getContext().getDrawable(getImg(itemData.getConnector1Type())));
            } catch (Exception e) {
                Log.e("Error1 :: ", e.getLocalizedMessage());
            }
        }

        if (currentCapacity2 != 0) {
            try {
                tv_type2.setText("2." + itemData.getConnector2Type());
                tv_type2_status.setText("");
                tv_type2_power.setText(String.valueOf(itemData.getConnector2OutputKW()) + " kw");
                iv2.setImageDrawable(getContext().getDrawable(getImg(itemData.getConnector2Type())));
            } catch (Exception e) {
                Log.e("Error2 :: ", e.getLocalizedMessage());
            }
        }
        if (currentCapacity3 != 0) {
            ll_type3.setVisibility(View.VISIBLE);
            try {
                tv_type3.setText("3." + itemData.getConnector3Type());
                tv_type3_status.setText(itemData.getConnector3Status());
                tv_type3_power.setText(String.valueOf(itemData.getConnector3OutputKW()) + " kw");
                iv3.setImageDrawable(getContext().getDrawable(getImg(itemData.getConnector3Type())));
            } catch (Exception e) {
                Log.e("Error2 :: ", e.getLocalizedMessage());
            }
        } else {
            ll_type3.setVisibility(View.GONE);
        }
        if (currentCapacity4 != 0) {
            ll_type4.setVisibility(View.VISIBLE);
            try {
                tv_type4.setText("4." + itemData.getConnector4Type());
                tv_type4_status.setText(itemData.getConnector4Status());
                tv_type4_power.setText(String.valueOf(itemData.getConnector4OutputKW()) + " kw");
                iv4.setImageDrawable(getContext().getDrawable(getImg(itemData.getConnector4Type())));
            } catch (Exception e) {
                Log.e("Error2 :: ", e.getLocalizedMessage());
            }
        } else {
            ll_type4.setVisibility(View.GONE);
        }


    }

    public int getImg(String str) {
        int res = 0;
        if (str.contains("Mennekes")) {
            res = R.drawable.type2;
        } else if (str.contains("CCS")) {
            res = R.drawable.ccs;
        } else if (str.contains("ChAdeMo") || str.contains("CHAdeMo") || str.contains("CHAdeMO")) {
            res = R.drawable.chademo;
        } else if (str.contains("Type 1") || str.contains("SAE") || str.contains("3-pin")) {
            res = R.drawable.pin;
        }
        return res;
    }

    @Override
    public void onPause() {
        super.onPause();
        ll_type4.setVisibility(View.GONE);
        ll_type3.setVisibility(View.GONE);
    }


    public void Update_reating(String rating) {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please wait ...");
        progressDialog.show();
        //Locations locations = Utility.getItems(itemData, rating);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = reference.child("locations/" + itemData.getPath() + "/Reliability rating");
       // DatabaseReference ref = reference.child("locations/loc-10339/Reliability rating");
        ref.setValue("" + rating).
                addOnCompleteListener(AppSingle.getInstance().getActivity(),
                        task -> {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                            /*    List<LocationData> temp=itemsData;
                                int i=Utility.getItemIndex(temp,itemData.getName());
                                LocationData loc=temp.get(i);
                                loc.setReliabilityRating("" + rating);
                                temp.set(i,loc);
                                act.setLocData(temp);*/
                                //Toast.makeText(getContext(), "Updated Successfully", Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                });
    }
}

