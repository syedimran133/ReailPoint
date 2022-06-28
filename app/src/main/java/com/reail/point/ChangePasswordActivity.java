package com.reail.point;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.reail.point.utiles.PreManager;

public class ChangePasswordActivity extends AppCompatActivity {

    private TextView tvCancel, tvSave;
    PreManager preManager;
    EditText editTextPassword, editTextConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        preManager = new PreManager(this);
        tvCancel = findViewById(R.id.tvCancel);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        tvSave = findViewById(R.id.tvSave);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextPassword.getText().toString().equalsIgnoreCase(editTextConfirmPassword.getText().toString())) {
                    change_password();
                }
            }
        });
    }

    private void change_password() {
        ProgressDialog progressDialog = new ProgressDialog(ChangePasswordActivity.this);
        progressDialog.setMessage("Please wait ...");
        progressDialog.show();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credential = EmailAuthProvider
                .getCredential(preManager.getUsersLogin().getEmail(), preManager.getPassword());
        assert user != null;
        user.reauthenticate(credential)
                .addOnCompleteListener(task -> {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        user.updatePassword(editTextPassword.getText().toString()).addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Password updated", Toast.LENGTH_LONG).show();
                                Log.d("TAG", "Password updated");
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Error password not updated", Toast.LENGTH_LONG).show();
                                Log.d("TAG", "Error password not updated");
                            }
                        });
                    } else {
                        Log.d("TAG", "Error auth failed");
                        Toast.makeText(getApplicationContext(), "Error auth failed", Toast.LENGTH_LONG).show();
                    }
                });
    }
}