package com.example.cmpm.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmpm.MainActivity;
import com.example.cmpm.Model.User;
import com.example.cmpm.R;
import com.example.cmpm.ui.LoginActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.api.Authentication;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.Signature;


public class InfoFragment extends Fragment {

    TextView btnOut;
    TextView tvGmail,tvSdt,tvTenKH;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v =  inflater.inflate(R.layout.fragment_info, container, false);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        getActivity().setTitle("Thông Tin Tài Khoản");
        btnOut = v.findViewById(R.id.btnOut);
        tvTenKH = v.findViewById(R.id.tvTenKH);
        tvGmail = v.findViewById(R.id.tvGmailKH);
        tvSdt = v.findViewById(R.id.tvSDT);

        btnOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.auth.signOut();
                Intent i = new Intent(getContext(), LoginActivity.class);
                startActivity(i);
            }
        });

        String userId = LoginActivity.auth.getUid();
        db.collection("User").document(userId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot snapshot) {
                User user = snapshot.toObject(User.class);
                tvTenKH.setText(user.getTen());
                tvGmail.setText(user.getGmail());
                tvSdt.setText(user.getSdt());
            }
        });



        return v;
    }
}