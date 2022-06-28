package com.reail.point;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.reail.point.utiles.PreManager;

public class DashboardAdmin  extends AppCompatActivity {

    PreManager preManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_admin);
        Context mContext = this;
        preManager = new PreManager(this);
        FrameLayout fragments_container = findViewById(R.id.fragment_container);
        AppSingle.getInstance().initActivity(this);
        FlowOrganizer.getInstance().initParentFrame(fragments_container);
        FlowOrganizer.getInstance().add(new UserFragment(),true);
        findViewById(R.id.layoutUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlowOrganizer.getInstance().add(new UserFragment(), true);
            }
        });
        findViewById(R.id.layoutPoint).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlowOrganizer.getInstance().add(new PointsFragment(), true);
            }
        });
        findViewById(R.id.layout_Setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlowOrganizer.getInstance().add(new Setting(), true);
            }
        });
    }
    public void hideKeyboard() {
        /*try {
            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}
