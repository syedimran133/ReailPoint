package com.reail.point;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.reail.point.utiles.PreManager;

public class MainActivity extends AppCompatActivity {

    private PreManager preManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preManager = new PreManager(MainActivity.this);
        AppSingle.getInstance().initActivity(this);
        new Handler().postDelayed(() -> {
            if (isNetworkAvailable(MainActivity.this)) {
                if (preManager.getIsLogin()) {
                    if (preManager.getUsersLogin().getAdmin().equalsIgnoreCase("Yes")) {
                        Intent intent = new Intent(MainActivity.this, DashboardAdmin.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(MainActivity.this, Dashboard.class);
                        startActivity(intent);
                    }
                }else{
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }


            } else {
                Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
            }
        }, 3000);
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void hideKeyboard() {
        try {
            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getCurrentFocus()
                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}