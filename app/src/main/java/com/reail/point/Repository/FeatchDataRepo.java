package com.reail.point.Repository;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.reail.point.model.LocationData;
import com.reail.point.utiles.PreManager;
import com.reail.point.utiles.Utility;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeatchDataRepo {

    //this is the data that we will fetch asynchronously
    private MutableLiveData<List<LocationData>> loginData;

    public void featchData(Context mContext,PreManager preManager, GeoLocation geo_loc) {
        Utility.showProgressDialogue(mContext,"","Please wait..");
        if (loginData == null) {
            loginData = new MutableLiveData<>();
        }
        ArrayList<String> keys = new ArrayList<>();
		//Toast.makeText(mContext,""+preManager.getRadius(), Toast.LENGTH_LONG).show();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("locations");
        GeoFire geoFire = new GeoFire(reference);
		GeoQuery query  = geoFire.queryAtLocation(geo_loc, 1.60934 * preManager.getRadius());
        query.addGeoQueryEventListener(new GeoQueryEventListener() {

            @Override
            public void onKeyEntered(String key, GeoLocation location) {
               // Utility.cancelProgressDialogue();
			 
                keys.add(key);
                //Toast.makeText(getApplicationContext(),"onKeyEntered"+key,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onKeyExited(String key) {
              // Utility.cancelProgressDialogue();
                //Toast.makeText(getApplicationContext(),"onKeyExited ",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {
              // Utility.cancelProgressDialogue();
                //Toast.makeText(getApplicationContext(),"onKeyMoved",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onGeoQueryReady() {
                List<LocationData> locData = new ArrayList<>();
				 ArrayList<String> vals = new ArrayList<>();
                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
				//Toast.makeText(mContext,"keys :-"+keys.size(),Toast.LENGTH_LONG).show();
                for (int i = 0; i < keys.size(); i++) {
                    DatabaseReference ref = database.child("locations/" + keys.get(i));
                    final int k = i;
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                           // try {
								//vals.add(new Gson().toJson(dataSnapshot.getValue()));
                                locData.add(new Gson().fromJson(new Gson().toJson(dataSnapshot.getValue()), LocationData.class));
                            /*} catch (Exception e) {
                                Log.e("Error " + k + " ::", e.getLocalizedMessage());
								Toast.makeText(mContext,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                            }*/
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
							 Log.e("Error " + k + " ::", databaseError.getMessage());
							 Toast.makeText(mContext,"error :-",Toast.LENGTH_LONG).show();
                        }

                    });
                }
				loginData.postValue(locData);
               Utility.cancelProgressDialogue();
            }

            @Override
            public void onGeoQueryError(DatabaseError error) {
                 Utility.cancelProgressDialogue();
                Toast.makeText(mContext, "onGeoQueryError", Toast.LENGTH_LONG).show();
            }
        });
    }

    public MutableLiveData<List<LocationData>> getData() {
        return loginData;
    }
   
}