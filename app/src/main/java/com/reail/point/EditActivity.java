package com.reail.point;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.reail.point.model.LoginUser;
import com.reail.point.model.User;
import com.reail.point.model.Users;
import com.reail.point.utiles.PreManager;

import java.util.Objects;

public class EditActivity extends AppCompatActivity {

    private TextView tvCancel, tvSave;
    EditText editTextName, editTextPhoneNumber;
    Context mContext;
    PreManager preManager;
    LoginUser users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        mContext=this;
        preManager = new PreManager(this);
        users=preManager.getUsersLogin();
        tvCancel = findViewById(R.id.tvCancel);
        tvSave = findViewById(R.id.tvSave);
        editTextName = findViewById(R.id.editTextName);
        editTextName.setText(users.getFullname());
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        editTextPhoneNumber.setText(users.getPhone_number());
        tvCancel.setOnClickListener(v -> finish());
        tvSave.setOnClickListener(v -> Update_user());
    }

    public void Update_user() {
        ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please wait ...");
        progressDialog.show();
        LoginUser user = new LoginUser();
        user.setAdmin(preManager.getUsersLogin().getAdmin());
        user.setSubscribed(preManager.getUsersLogin().getSubscribed());
        user.setExpiryDate(preManager.getUsersLogin().getExpiryDate());
        user.setLoginDate(preManager.getUsersLogin().getLoginDate());
        user.setUid(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
        user.setDevice("Android");
        user.setEmail(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail());
        user.setFullname(editTextName.getText().toString());
        user.setPhone_number(editTextPhoneNumber.getText().toString());
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).
                addOnCompleteListener(EditActivity.this,
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressDialog.dismiss();
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_LONG).show();
                                }
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