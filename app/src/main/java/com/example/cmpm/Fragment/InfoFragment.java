package com.example.cmpm.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmpm.AdminPage.ChonSachActivity;
import com.example.cmpm.MainActivity;
import com.example.cmpm.Model.User;
import com.example.cmpm.R;
import com.example.cmpm.ui.LoginActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.api.Authentication;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.Signature;
import java.util.HashMap;
import java.util.Map;


public class InfoFragment extends Fragment {

    TextView btnOut;
    TextView tvGmail,tvSdt,tvTenKH;
    Button btnSua;

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
        btnSua = v.findViewById(R.id.btnSuaThongTin);

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

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(getContext());
                View promptsView = li.inflate(R.layout.dialog_change_info, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setView(promptsView);

                final EditText ten = (EditText) promptsView.findViewById(R.id.edTenSua);
                final EditText sdt = (EditText) promptsView.findViewById(R.id.edSDTsua);

                alertDialogBuilder.setCancelable(false).setPositiveButton("Cập Nhật", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                HashMap<String,Object> a  = new HashMap<>();
                                a.put("ten",ten.getText().toString());
                                a.put("sdt",sdt.getText().toString());
                                db.collection("User").document(LoginActivity.auth.getUid()).update(a);
                            }
                        })
                        .setNegativeButton("Hủy",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                ten.setText(LoginActivity.curUser.getTen());
                sdt.setText(LoginActivity.curUser.getSdt());
                // show it
                alertDialog.show();

            }
        });


        return v;
    }
}