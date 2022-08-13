package com.reail.point;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.reail.point.model.LocData;
import com.reail.point.model.LoginUser;
import com.reail.point.utiles.PreManager;
import com.reail.point.utiles.Utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private TextView tv_forgot_password, tv_sign_up, tv_sign_in;
    private EditText editTextEmail, editTextPassword;
    private String email, password, mErrorMsg = "Email is empty";

    PreManager preManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        preManager = new PreManager(this);
        tv_forgot_password = findViewById(R.id.tv_forgot_password);
        tv_sign_up = findViewById(R.id.tv_sign_up);
        tv_sign_in = findViewById(R.id.tv_sign_in);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        tv_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPassword.class);
                startActivity(intent);
            }
        });
        tv_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUp.class);
                startActivity(intent);
            }
        });
        tv_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = editTextEmail.getText().toString();
                password = editTextPassword.getText().toString();
                if (validate()) {
                    ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                    progressDialog.setMessage("Please wait ...");
                    progressDialog.show();
                    AppSingle.getInstance().getmAuth().signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            getDataLogin();
                            preManager.setIsLogin(true);
                        } else {
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Sign-in failed. " + task.getException().getLocalizedMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                } else {
                    Toast.makeText(LoginActivity.this, mErrorMsg,
                            Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    public boolean validate() {
        boolean result = true;
        if (TextUtils.isEmpty(password)) {
            result = false;
            mErrorMsg = "Please enter valid password";
        }

        if (TextUtils.isEmpty(email) && !(Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
            result = false;
            mErrorMsg = "please enter valid email address";
        }
        return result;
    }



    public void Update_user(LoginUser user) {
        user.setLoginDate(Utility.getLoginDate());
        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("users");
        ref2.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(LoginActivity.this, task -> {
        }).addOnFailureListener(e -> {
        });
    }

    public void getDataLogin() {
        ProgressDialog progressDialog2 = new ProgressDialog(LoginActivity.this);
        progressDialog2.setMessage("Please wait .....");
        progressDialog2.show();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users/" + FirebaseAuth.getInstance().getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    preManager.setLoginUser(new Gson().toJson(dataSnapshot.getValue()));
                    preManager.setPassword(password);
                    Update_user(preManager.getUsersLogin());
                    progressDialog2.dismiss();
                    if (preManager.getUsersLogin().getAdmin().equalsIgnoreCase("Yes")) {
                        Intent intent = new Intent(LoginActivity.this, DashboardAdmin.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(LoginActivity.this, Dashboard.class);
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    Log.e("UserListActivity", e.getLocalizedMessage());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("UserListActivity", "Error occured");
                progressDialog2.dismiss();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        editTextEmail.setText("");
        editTextPassword.setText("");
    }
}
