package com.reail.point;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment  extends Fragment {
    LatLng latLng;
    ImageView iv_back;
    TextView tvTitle;
    public MapFragment(LatLng latLng) {
        // Required empty public constructor
        this.latLng=latLng;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        tvTitle=rootView.findViewById(R.id.tvTitle);
        iv_back=rootView.findViewById(R.id.iv_back);

        tvTitle.setText(Constant.singalItemData.getName());
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.frg);  //use SuppoprtMapFragment for using in fragment instead of activity  MapFragment = activity   SupportMapFragment = fragment
        assert mapFragment != null;
        mapFragment.getMapAsync(mMap -> {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mMap.clear(); //clear old markers
            CameraPosition googlePlex = CameraPosition.builder()
                    .target(new LatLng(latLng.latitude,latLng.longitude))
                    .zoom(15)
                    .bearing(0)
                    .tilt(45)
                    .build();

            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 5000, null);

            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(latLng.latitude, latLng.longitude))
                    .title(Constant.singalItemData.getName())
                    .icon(bitmapDescriptorFromVector(getActivity(),getimgRes())));
        });

        iv_back.setOnClickListener(v -> FlowOrganizer.getInstance().popUpBackTo(1));
        return rootView;
    }
    private int getimgRes() {
        int res = R.drawable.green43;
        int currentCapacity1 = 0;
        try{
            currentCapacity1 = Integer.parseInt(Constant.singalItemData.getOutputKW());
        }catch (Exception e){}
        int currentCapacity2 = 0;
        try{
            currentCapacity2 = Integer.parseInt(Constant.singalItemData.getConnector2OutputKW());
        }catch (Exception e){}
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

        if (Constant.singalItemData.getChargeDeviceStatus().equalsIgnoreCase("In service")) {
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
        return res;
    }
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}
