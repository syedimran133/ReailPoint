package com.reail.point;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.reail.point.adpter.SearchAdpter;
import com.reail.point.model.LocationData;
import com.reail.point.model.LoginUser;
import com.reail.point.model.SearchData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserFragment  extends Fragment {
    private View v;
    Context mContext;
    List<LoginUser> list;
    RecyclerView search_rv;
    SearchAdpter adpter;
    ArrayList<SearchData> listS = new ArrayList<>();
    EditText editTextSearch;
    TextView tvCancel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.layout_users, container, false);
        mContext=getContext();
        search_rv = v.findViewById(R.id.rv);
        editTextSearch= v.findViewById(R.id.editTextSearch);
        tvCancel= v.findViewById(R.id.tvCancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlowOrganizer.getInstance().popUpBackTo(1);
            }
        });
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                adpter.getFilter().filter(s.toString());
            }
        });
        getData();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void getData() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    list = new ArrayList<>();
                    Iterator<DataSnapshot> d = dataSnapshot.getChildren().iterator();
                    while (d.hasNext()) {
                        try {
                            String key=d.next().getKey();
                            Object value=dataSnapshot.child(key).getValue();
                            String json=new Gson().toJson(value);
                            LoginUser user=new Gson().fromJson(json, LoginUser.class);
                            list.add(user);
                        } catch (Exception e) {
                            Log.e("Error fetching",e.getLocalizedMessage());
                        }
                    }
                } catch (Exception e) {
                    Log.e("Error fetching",e.getLocalizedMessage());
                }
                setData();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("UserListActivity", "Error occured");
                // Do something about the erro
            }
        });
    }
    private void setAdapters(ArrayList<SearchData> items) {
        adpter = new SearchAdpter(getContext(), items,2);
        search_rv.setLayoutManager(new LinearLayoutManager(getContext()));
        search_rv.setAdapter(adpter);
        adpter.registerOnItemClickListener(position -> {
            Constant.singalUserData=list.get(position);
            FlowOrganizer.getInstance().add(new UserDetailsFragment(), true);
        });
        adpter.notifyDataSetChanged();
    }
    public void setData(){
        for (int i=0;i<list.size();i++){
            listS.add(new SearchData(list.get(i).getFullname(),"Phone Number: "+list.get(i).getPhone_number(),"email: "+list.get(i).getEmail(),"uid: "+list.get(i).getUid()));
        }
        setAdapters(listS);
    }
}
