package com.reail.point;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private ImageView iv_back;
    private EditText emailET;
    private TextView send;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_forgot_password);
        iv_back = findViewById(R.id.iv_back);
        emailET = findViewById(R.id.editTextEmailAddress);
        send = findViewById(R.id.tv_send);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(emailET.getText().toString()) && (Patterns.EMAIL_ADDRESS.matcher(emailET.getText().toString()).matches())) {
                    ProgressDialog progressDialog = new ProgressDialog(ForgotPassword.this);
                    progressDialog.setMessage("Please wait ...");
                    progressDialog.show();
                    mAuth.sendPasswordResetEmail(emailET.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                Toast.makeText(ForgotPassword.this, "Reset link send to your register email", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ForgotPassword.this, LoginActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(ForgotPassword.this, "Fail " + task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(ForgotPassword.this, "please enter valid email address", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}