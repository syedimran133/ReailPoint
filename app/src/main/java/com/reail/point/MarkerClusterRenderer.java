package com.reail.point;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;
import com.reail.point.model.User;
import com.reail.point.utiles.Utility;

public class MarkerClusterRenderer extends DefaultClusterRenderer<User> {
    private static final int MARKER_DIMENSION = 68;  // 2

    private final IconGenerator iconGenerator;
    private final ImageView markerImageView;
    private Context context;

    public MarkerClusterRenderer(Context context, GoogleMap map, ClusterManager<User> clusterManager) {
        super(context, map, clusterManager);
        this.context = context;
        iconGenerator = new IconGenerator(context);  // 3
        markerImageView = new ImageView(context);
        markerImageView.setLayoutParams(new ViewGroup.LayoutParams(MARKER_DIMENSION, MARKER_DIMENSION));
        iconGenerator.setContentView(markerImageView);  // 4

    }

    @Override
    protected void onBeforeClusterItemRendered(User item, MarkerOptions markerOptions) { // 5
       if (!Constant.subscribed) {
            //markerImageView.setImageResource(Utility.getimgRes(item.getIndex()));
       } else {
            //markerImageView.setImageResource(Utility.subUser(item.getIndex()));
        }
        Bitmap icon = iconGenerator.makeIcon();  // 7
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));  // 8
        markerOptions.title(item.getTitle());
    }
}
