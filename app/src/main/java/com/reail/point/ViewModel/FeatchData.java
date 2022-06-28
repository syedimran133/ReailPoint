package com.reail.point.ViewModel;

import android.app.Application;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.firebase.geofire.GeoLocation;
import com.reail.point.Repository.FeatchDataRepo;
import com.reail.point.model.LocationData;
import com.reail.point.utiles.PreManager;

import java.util.List;

public class FeatchData extends AndroidViewModel {

    //this is the data that we will fetch asynchronously
    private MutableLiveData<List<LocationData>> loginData;
    private FeatchDataRepo loginRepo;

    public FeatchData(@NonNull Application application) {
        super(application);
        init();
    }
    public void init() {
		loginRepo = new FeatchDataRepo();
        loginData = new MutableLiveData<>();
    }
    //we will call this method to get the Login data
    public LiveData<List<LocationData>> login(Context mContext, PreManager preManager, GeoLocation geo_loc) {
        //we will load it asynchronously from server in this method
		 init();
        loginRepo.featchData(mContext,preManager, geo_loc);
        loginData = loginRepo.getData();
        return loginData;
    }

    public MutableLiveData<List<LocationData>> getData() {
        return loginData;
    }
}