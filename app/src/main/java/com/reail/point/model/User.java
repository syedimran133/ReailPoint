package com.reail.point.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class User implements ClusterItem {

    private final String username;
    private final LatLng latLng;
    private int index;
    private int image;


    public User(String username, LatLng latLng,int image) {
        this.username = username;
        this.latLng = latLng;
        this.image = image;
    }
    @NonNull
    @Override
    public LatLng getPosition() {
        return latLng;
    }

    @Nullable
    @Override
    public String getTitle() {
        return username;
    }

    @Nullable
    @Override
    public String getSnippet() {
        return "";
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
