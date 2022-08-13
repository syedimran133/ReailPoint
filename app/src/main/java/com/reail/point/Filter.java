package com.reail.point;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.reail.point.model.SearchData;
import com.reail.point.utiles.PreManager;

public class Filter extends Fragment {

    private View v;
    private ImageView iv_back;
    private TextView tvSave;
    private PreManager preManager;
    private RadioGroup radioGroup;
    private RadioButton rb_all, rb_not_working, rb_working, rb_working_well;
    private SeekBar sbPower, sbRadius;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_filter, container, false);
        preManager = new PreManager(getContext());
        iv_back = v.findViewById(R.id.iv_back);
        radioGroup = (RadioGroup) v.findViewById(R.id.radioGroup);
        sbPower = v.findViewById(R.id.seekBar);
        sbPower.setProgress(preManager.getPowerType());
        sbPower.getProgressDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        sbPower.getThumb().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        sbRadius = v.findViewById(R.id.seekBar2);
        sbRadius.setProgress(preManager.getRadius());
        sbRadius.getProgressDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        sbRadius.getThumb().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        rb_all = v.findViewById(R.id.rb_all);
        rb_not_working = v.findViewById(R.id.rb_not_working);
        rb_working = v.findViewById(R.id.rb_working);
        rb_working_well = v.findViewById(R.id.rb_working_well);
        tvSave = v.findViewById(R.id.tvSave);
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addListenerOnButton();
                FlowOrganizer.getInstance().add(new MapsMarker(), false);
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addListenerOnButton();
                FlowOrganizer.getInstance().popUpBackTo(1);
            }
        });
        setValue();
        return v;
    }

    private void setValue() {
        int type = preManager.getReliability();
        if (type == 1) {
            rb_all.setChecked(true);
            rb_not_working.setChecked(false);
            rb_working.setChecked(false);
            rb_working_well.setChecked(false);
        } else if (type == 2) {
            rb_all.setChecked(false);
            rb_not_working.setChecked(true);
            rb_working.setChecked(false);
            rb_working_well.setChecked(false);
        } else if (type == 3) {
            rb_all.setChecked(false);
            rb_not_working.setChecked(false);
            rb_working.setChecked(true);
            rb_working_well.setChecked(false);
        } else if (type == 4) {
            rb_all.setChecked(false);
            rb_not_working.setChecked(false);
            rb_working.setChecked(false);
            rb_working_well.setChecked(true);
        }
    }

    private void addListenerOnButton() {
        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        View radioButton = radioGroup.findViewById(radioButtonID);
        preManager.setReliability(radioGroup.indexOfChild(radioButton) + 1);
        if (sbPower.getProgress() < 25) {
            preManager.setPowerType(25);
        } else {
            preManager.setPowerType(sbPower.getProgress());
        }
        if (sbRadius.getProgress() < 3) {
            preManager.setRadius(3);
        } else {
            preManager.setRadius(sbRadius.getProgress());
        }
    }
}
