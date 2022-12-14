package com.reail.point;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.IndoorBuilding;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.reail.point.ViewModel.FeatchData;
import com.reail.point.model.LocationData;
import com.reail.point.model.User;
import com.reail.point.utiles.PreManager;
import com.reail.point.utiles.Utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static android.widget.Toast.*;

public class MapsMarker extends Fragment {

    private GoogleMap mMap;
    private View v;
    private ImageView button_current;
    private TextView button;
    private PreManager preManager;
    public static int loc_type = 1;
    public static GeoLocation current_geo_loc = null;
    public static LatLng current_latlong = null;
    public static GeoLocation search_geo_loc = null;
    public static LatLng search_latlong = null;
    public static GeoLocation geo_loc = null;
    public LatLng latlong = null;
    //public List<LocationData> locData;
    public static List<LocationData> locDataSearch;
    private EditText editTextSearch;
    private LifecycleOwner owner;
    private Map<Marker, String> markers = new HashMap<>();
    private SupportMapFragment mMapFragment;
    private FeatchData pageViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        owner = this;
        preManager = new PreManager(getContext());
        //current_latlong = new LatLng(preManager.getLat(),preManager.getLong());
        //current_geo_loc = new GeoLocation(preManager.getLat(), preManager.getLong());
        if (search_latlong != null) {
            latlong = new LatLng(search_latlong.latitude, search_latlong.longitude);
            geo_loc = search_geo_loc;
        } else {
            current_latlong = latlong = new LatLng(preManager.getLat(), preManager.getLong());
            current_geo_loc = new GeoLocation(latlong.latitude, latlong.longitude);
            geo_loc = current_geo_loc;
        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_maps, container, false);
        pageViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory.getInstance(AppSingle.getInstance())).get(FeatchData.class);

            pageViewModel.login(getContext(), preManager, geo_loc).observe(owner, locationData -> {
                //locData = locationData;
                // Toast.makeText(getContext(), "" + locationData.size(), Toast.LENGTH_LONG).show();
                Utility.showProgressDialogue(getContext(), "", "Please wait......");
                final Handler handler = new Handler();
                handler.postDelayed(() -> {
                    // Do something after 5s = 5000ms
                    Log.d("GeoQuery  CreateView post data size:: ",""+locationData.size());
                    Utility.cancelProgressDialogue();
                    init(locationData);
                }, 5000);
            });

        return v;
    }

    @SuppressLint("PotentialBehaviorOverride")
    void init(List<LocationData> locItems) {

        try {
            ImageView iv_filter = v.findViewById(R.id.iv_filter);
            button = v.findViewById(R.id.button);
            button.setVisibility(View.GONE);
            button_current = v.findViewById(R.id.button_current);
            editTextSearch = v.findViewById(R.id.editTextSearch);
            Places.initialize(AppSingle.getInstance().getApplicationContext(), "AIzaSyAwnbQWbsFwMekiP38kM_cujWaae2bQ1UU");
            editTextSearch.setFocusable(false);

            if (Constant.subscribed) {
                iv_filter.setVisibility(View.VISIBLE);
            } else {
                iv_filter.setVisibility(View.GONE);
            }
            editTextSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loc_type = 2;
                    List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);
                    Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(AppSingle.getInstance().getActivity());
                    startActivityForResult(intent, 100);
                }
            });
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    geo_loc = search_geo_loc;
                    latlong = search_latlong;
                    pageViewModel.login(getContext(), preManager, geo_loc).observe(owner, locationData -> {
                        // Toast.makeText(getContext(), "" + locationData.size(), Toast.LENGTH_LONG).show();
                        Utility.showProgressDialogue(getContext(), "", "Please wait......");
                        final Handler handler = new Handler();
                        handler.postDelayed(() -> {
                            Log.d("GeoQuery   button.setOnClickListener post data size:: ",""+locationData.size());
                            Utility.cancelProgressDialogue();
                            init(locationData);
                        }, 5000);
                    });
                }
            });
            button_current.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    latlong = current_latlong;
                    geo_loc = current_geo_loc;
                    pageViewModel.login(getContext(), preManager, geo_loc).observe(owner, locationData -> {
                        Utility.showProgressDialogue(getContext(), "", "Please wait......");
                        final Handler handler = new Handler();
                        handler.postDelayed(() -> {
                            Log.d("GeoQuery button_current.setOnClickListener post data size:: ",""+locationData.size());
                            Utility.cancelProgressDialogue();
                            init(locationData);
                        }, 5000);
                    });
                }
            });
            iv_filter.setOnClickListener(v -> FlowOrganizer.getInstance().add(new Filter(), true));
            try {
                mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
                assert mMapFragment != null;
                mMapFragment.getMapAsync(googleMap -> {
                    mMap = googleMap;
                    mMap.clear();
                    mMap.setOnCameraChangeListener(cameraPosition -> {
                        button.setVisibility(View.GONE);
                        LatLngBounds latBond = googleMap.getProjection().getVisibleRegion().latLngBounds;
                        LatLng center = latBond.getCenter();
                        search_geo_loc = new GeoLocation(center.latitude, center.longitude);
                        search_latlong = new LatLng(center.latitude, center.longitude);
                        if (preManager.getRadius() < Utility.distance(latlong.latitude, latlong.longitude, center.latitude, center.longitude)) {
                            button.setVisibility(View.VISIBLE);
                        } else {
                            button.setVisibility(View.GONE);
                        }
                    });
                    mMap.setOnInfoWindowClickListener(arg0 -> {
                        String markerName = markers.get(arg0);
                        PointDetails frg=new PointDetails();
                        frg.getValues(locItems,Utility.getItemSearch(locItems, markerName),this);
                        FlowOrganizer.getInstance().add(frg, true);
                    });
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latlong));
                    animateZoomInCamera(latlong);
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    Circle circle = mMap.addCircle(new CircleOptions()
                            .center(latlong)
                            .radius(1610 * preManager.getRadius())
                            .strokeColor(Color.parseColor("#cccccc"))
                            .fillColor(Color.parseColor("#66cccccc")));
                    setMarker(googleMap, locItems);
                    //drawMapCircle(mMap,latlong,circle);

                });
            } catch (Exception e) {
                Toast.makeText(AppSingle.getInstance().getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(AppSingle.getInstance().getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
    }

    void animateZoomInCamera(LatLng latLng) {
        if (3 >= preManager.getRadius()) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.5f));
        } else if (6 >= preManager.getRadius()) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11.7f));
        } else {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10.7f));
        }
    }

    public void setMarker(GoogleMap googleMap, List<LocationData> data) {
        try {
            List<LocationData> temp = null;
            if (Constant.subscribed) {
                temp = Utility.getfilterdata(Utility.listsort(preManager.getPowerType(), data), preManager.getReliability());
            } else {
                temp = Utility.getfilterdata(data, preManager.getReliability());
            }
            mMap = googleMap;
            List<User> items = Utility.getItems(temp);
            for (int i = 0; i < items.size(); i++) {
                Marker a = mMap.addMarker(new MarkerOptions().position(items.get(i).getPosition()).title(items.get(i).getTitle()).icon(Utility.BitmapFromVector(getContext(), items.get(i).getImage())));
                markers.put(a, items.get(i).getTitle());
            }
        } catch (Exception e) {
            Toast.makeText(AppSingle.getInstance().getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 100) {
            Place place = Autocomplete.getPlaceFromIntent(data);
            // Log.i("TAG", "Place: " + place.getName() + ", " + place.getId() + ", " + place.getAddress());
            //Toast.makeText(AppSingle.getInstance().getApplicationContext(), " latlong: " + place.getLatLng(), Toast.LENGTH_LONG).show();
            geo_loc = new GeoLocation(place.getLatLng().latitude, place.getLatLng().longitude);
            latlong = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude);
            search_geo_loc = geo_loc;
            search_latlong = latlong;
            pageViewModel.login(getContext(), preManager, geo_loc).observe(owner, locationData -> {
                //Toast.makeText(getContext(), "" + locationData.size(), Toast.LENGTH_LONG).show();
                Utility.showProgressDialogue(getContext(), "", "Please wait......");
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("GeoQuery search post data size:: ",""+locationData.size());
                        Utility.cancelProgressDialogue();
                        init(locationData);
                    }
                }, 5000);
            });
        }
    }
}
