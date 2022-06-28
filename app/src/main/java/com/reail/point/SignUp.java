package com.reail.point;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.reail.point.model.LoginUser;
import com.reail.point.utiles.Utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

public class SignUp extends AppCompatActivity {

    private ImageView iv_back;
    private FirebaseAuth mAuth;
    private EditText editTextTextName, editTextTextEmail, editTextTextPhoneNumber, editTextTextPassword, editTextTextConfPassword;
    private TextView tv_register;
    private String mName, mEmail, mPhoneNumber, mPassword, mConfirmPassword, mErrorMsg = "Please Enter Password";
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mContext=this;
        mAuth = FirebaseAuth.getInstance();
        iv_back = findViewById(R.id.iv_back);
        editTextTextName = findViewById(R.id.editTextTextName);
        editTextTextEmail = findViewById(R.id.editTextTextEmail);
        editTextTextPhoneNumber = findViewById(R.id.editTextTextPhoneNumber);
        editTextTextPassword = findViewById(R.id.editTextTextPassword);
        editTextTextConfPassword = findViewById(R.id.editTextTextConfPassword);
        tv_register = findViewById(R.id.tv_register);
        iv_back.setOnClickListener(v -> finish());
        tv_register.setOnClickListener(v -> {
            mName = editTextTextName.getText().toString();
            mEmail = editTextTextEmail.getText().toString();
            mPhoneNumber = editTextTextEmail.getText().toString();
            mPassword = editTextTextPassword.getText().toString();
            mConfirmPassword = editTextTextConfPassword.getText().toString();
            if (validate()) {
                ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Please wait ...");
                progressDialog.show();
                mAuth.createUserWithEmailAndPassword(mEmail, mPassword)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();
                                if (task.isSuccessful()) {
                                    Log.d("TAG", "createUserWithEmail:success");
                                    update_user();
                                    Toast.makeText(SignUp.this, "Sign-up successful",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignUp.this, LoginActivity.class);
                                    startActivity(intent);
                                } else {
                                    Log.w("TAG", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(SignUp.this, "Sign-up failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            } else {
                Toast.makeText(SignUp.this, mErrorMsg,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean validate() {
        boolean result = true;

        if (!validPassord(mPassword, mConfirmPassword)) {
            //mErrorMsg = "Please Enter Phone Number";
            result = false;
        }

        if (TextUtils.isEmpty(mPhoneNumber)) {
            mErrorMsg = "Please Enter Phone Number";
            result = false;
        }

        if (TextUtils.isEmpty(mEmail) && !(Patterns.EMAIL_ADDRESS.matcher(mEmail).matches())){
            mErrorMsg = "Please Enter valid email";
            result = false;
        }
        if (TextUtils.isEmpty(mName)) {
            mErrorMsg = "Please Enter Full Name";
            result = false;
        }
        return result;
    }

    public boolean validPassord(String pass1, String pass2) {
        boolean result = false;
        if (pass1.equalsIgnoreCase(pass2)) {
            if (pass1.length() == 6) {
                result = true;
            } else {
                mErrorMsg = "Please use minimum 6 character password.";
            }
        } else {
            mErrorMsg = "Password and Confirm Password doesn't match.";
        }
        return result;
    }

    public void update_user() {
        ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Updating wait ...");
        progressDialog.show();
        LoginUser user = new LoginUser();
        user.setAdmin("No");
        user.setSubscribed("No");
        user.setExpiryDate(Utility.getexpiryDate());
        user.setLoginDate("");
        user.setUid(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
        user.setDevice("Android");
        user.setEmail(mEmail);
        user.setFullname(mName);
        user.setPhone_number(mPhoneNumber);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        reference.child(mAuth.getCurrentUser().getUid()).setValue(user).
                addOnCompleteListener(SignUp.this,
                        task -> {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Sign Up Successfully", Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}