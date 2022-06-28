package com.reail.point;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.reail.point.model.LoginUser;

import java.util.Objects;

public class UserDetailsFragment extends Fragment {

    EditText etAdmin, etSubscribe, etPhone,etExpirydate, etFullname;
    TextView tvSave, tvUid, tvEmail, tvLoginDate,etDevice;
ImageView iv_back;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_details, container, false);
        etAdmin = v.findViewById(R.id.etAdmin);
        etSubscribe = v.findViewById(R.id.etSubscribe);
        etPhone = v.findViewById(R.id.etPhone);
        etDevice = v.findViewById(R.id.etDevice);
        etExpirydate = v.findViewById(R.id.etExpirydate);
        etFullname = v.findViewById(R.id.etFullname);
        tvUid = v.findViewById(R.id.tvUid);
        tvEmail = v.findViewById(R.id.tvEmail);
        tvLoginDate = v.findViewById(R.id.tvLoginDate);
        tvSave = v.findViewById(R.id.tvSave);

        iv_back = v.findViewById(R.id.iv_back);

        etAdmin.setText(Constant.singalUserData.getAdmin());
        etSubscribe.setText(Constant.singalUserData.getSubscribed());
        etPhone.setText(Constant.singalUserData.getPhone_number());
        etDevice.setText(Constant.singalUserData.getDevice());
        etExpirydate.setText(Constant.singalUserData.getExpiryDate());
        etFullname.setText(Constant.singalUserData.getFullname());
        tvUid.setText(Constant.singalUserData.getUid());
        tvEmail.setText(Constant.singalUserData.getEmail());
        tvLoginDate.setText(Constant.singalUserData.getLoginDate());
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Update_user();
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlowOrganizer.getInstance().add(new UserFragment(), false);
            }
        });
        return v;
    }

    public void Update_user() {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please wait ...");
        progressDialog.show();
        LoginUser user = new LoginUser();
        user.setAdmin(etAdmin.getText().toString());
        user.setSubscribed(etSubscribe.getText().toString());
        user.setExpiryDate(etExpirydate.getText().toString());
        user.setLoginDate(tvLoginDate.getText().toString());
        user.setUid(tvUid.getText().toString());
        user.setDevice(etDevice.getText().toString());
        user.setEmail(tvEmail.getText().toString());
        user.setFullname(etFullname.getText().toString());
        user.setPhone_number(etPhone.getText().toString());
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        reference.child(tvUid.getText().toString()).setValue(user).
                addOnCompleteListener(getActivity(),
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressDialog.dismiss();
                                if (task.isSuccessful()) {
                                    Toast.makeText(getContext(), "Updated Successfully", Toast.LENGTH_LONG).show();
                                    FlowOrganizer.getInstance().add(new UserFragment(), false);
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
