package com.reail.point;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.reail.point.adpter.SearchAdpter;
import com.reail.point.model.LocationData;
import com.reail.point.model.SearchData;

import java.util.ArrayList;

public class Search extends Fragment {
    private View v;
    RecyclerView search_rv;
    SearchAdpter adpter;
    ArrayList<SearchData> list = new ArrayList<>();
    EditText editTextSearch;
    TextView tvCancel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_search, container, false);
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
        setData();
        setAdapter(list);
        return v;
    }

    private void setAdapter(ArrayList<SearchData> items) {
        adpter = new SearchAdpter(getContext(), items,1);
        search_rv.setLayoutManager(new LinearLayoutManager(getContext()));
        search_rv.setAdapter(adpter);
        adpter.registerOnItemClickListener(new SearchAdpter.IonItemSelect() {
            @Override
            public void onItemSelect(int position) {
                Constant.singalItemData=MapsMarker.locDataSearch.get(position);
                FlowOrganizer.getInstance().add(new PointDetails(), true);
            }
        });
        adpter.notifyDataSetChanged();
    }

    public void setData(){
        Toast.makeText(getContext(),""+MapsMarker.locDataSearch.size(),Toast.LENGTH_LONG).show();
        for (int i=0;i<MapsMarker.locDataSearch.size();i++){
            list.add(new SearchData(MapsMarker.locDataSearch.get(i).getName(),MapsMarker.locDataSearch.get(i).getChargeDeviceStatus(),String.valueOf(MapsMarker.locDataSearch.get(i).getL().get(0)),String.valueOf(MapsMarker.locDataSearch.get(i).getL().get(1))));
        }
    }
}
