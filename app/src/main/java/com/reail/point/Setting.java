package com.reail.point;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.reail.point.utiles.PreManager;

public class Setting extends Fragment {

    private View v;
    private Button btnLogout;
    private TextView tvProfileUpdate,tvChangePassword,tvConditionsPrices,tvAboutUs,tvPrivacyPolicy,tvTermsAndConditions;
    PreManager preManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.setting_fragment, container, false);
        preManager = new PreManager(getContext());
        btnLogout=v.findViewById(R.id.btnLogout);
        tvProfileUpdate=v.findViewById(R.id.tvProfileUpdate);
        tvChangePassword=v.findViewById(R.id.tvChangePassword);
        tvConditionsPrices=v.findViewById(R.id.tvConditionsPrices);
        tvAboutUs=v.findViewById(R.id.tvAboutUs);
        tvPrivacyPolicy=v.findViewById(R.id.tvPrivacyPolicy);
        tvTermsAndConditions=v.findViewById(R.id.tvTermsAndConditions);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                //preManager.c
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
				preManager.setIsLogin(false);
            }
        });
        tvConditionsPrices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.TITLE="About Us";
                FlowOrganizer.getInstance().add(new Subscriptions(),true);
            }
        });
        tvProfileUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EditActivity.class);
                startActivity(intent);
            }
        });
        tvChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
        tvPrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.TITLE="Privacy Policy";
                FlowOrganizer.getInstance().add(new UrlWebView(),true);
            }
        });
        tvAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.TITLE="About Us";
                FlowOrganizer.getInstance().add(new UrlWebView(),true);
            }
        });
        tvTermsAndConditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.TITLE="Terms & Conditions";
                FlowOrganizer.getInstance().add(new UrlWebView(),true);
            }
        });
        return v;
    }
}
