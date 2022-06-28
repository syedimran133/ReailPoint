package com.reail.point;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Subscriptions extends Fragment {
    private View v;
    private TextView tvCancel, test_subscribed, test_unsubscribed;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_subscriptions, container, false);
        tvCancel = v.findViewById(R.id.tvCancel);
        test_subscribed = v.findViewById(R.id.test_subscribed);
        test_unsubscribed = v.findViewById(R.id.test_unsubscribed);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlowOrganizer.getInstance().popUpBackTo(1);
            }
        });
        test_subscribed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.subscribed = true;
                FlowOrganizer.getInstance().popUpBackTo(1);
            }
        });
        test_unsubscribed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.subscribed = false;
                FlowOrganizer.getInstance().popUpBackTo(1);
            }
        });
        return v;
    }
}
