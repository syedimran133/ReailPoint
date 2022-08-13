package com.reail.point;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoFireUtils;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryBounds;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.reail.point.model.LocData;
import com.reail.point.model.LocationData;
import com.reail.point.utiles.GpsTracker;
import com.reail.point.utiles.PreManager;
import com.reail.point.utiles.Utility;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class Dashboard extends AppCompatActivity {

    private FrameLayout fragments_container;
    private LinearLayout layout_Setting;
    private Context mContext;
    PreManager preManager;
    private LocationManager locationManager;
    private GpsTracker gpsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dashboard);
        mContext = this;
        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        preManager = new PreManager(this);
        fragments_container = findViewById(R.id.fragment_container);
        layout_Setting = findViewById(R.id.layout_Setting);
        AppSingle.getInstance().initActivity(this);
        FlowOrganizer.getInstance().initParentFrame(fragments_container);
        //getData();
        getLocation();
        layout_Setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlowOrganizer.getInstance().add(new Setting(), true);
            }
        });
        findViewById(R.id.layout_mypoint).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlowOrganizer.getInstance().add(new MyPoint(), true);
            }
        });
        findViewById(R.id.layoutMap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlowOrganizer.getInstance().add(new MapsMarker(), true);
            }
        });
        findViewById(R.id.layoutSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlowOrganizer.getInstance().add(new Search(), true);
            }
        });
        try {
            long g= Utility.setDiff(Calendar.getInstance().getTime(),Utility.getDate(preManager.getUsersLogin().getExpiryDate()));
            if(g>0){
                Constant.subscribed=true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        LatLng lt=new LatLng(preManager.getLat(),preManager.getLong());
        if(lt!=null){
            FlowOrganizer.getInstance().add(new MapsMarker(),true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void hideKeyboard() {
        /*try {
            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    public void getData() {
        ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please wait ...");
        progressDialog.show();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("locations");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    MapsMarker.locDataSearch = new ArrayList<>();
                    Iterator<DataSnapshot> d = dataSnapshot.getChildren().iterator();
                    while (d.hasNext()) {
                        try {
                            MapsMarker.locDataSearch.add(new Gson().fromJson(new Gson().toJson(d.next().getValue()), LocationData.class));
                        } catch (Exception e) {
                        }
                    }
                    progressDialog.dismiss();
                    FlowOrganizer.getInstance().add(new MapsMarker(), true);
                } catch (Exception e) {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("UserListActivity", "Error occured");
                // Do something about the error
                progressDialog.dismiss();
            }
        });
    }
    public void getLocation(){
        gpsTracker = new GpsTracker(Dashboard.this);
        if(gpsTracker.canGetLocation()){
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            preManager.setLat(latitude);
            preManager.setLong(longitude);
            AppSingle.getInstance().setLatLng(new LatLng(latitude,longitude));
        }else{
            gpsTracker.showSettingsAlert();
        }
    }

}
