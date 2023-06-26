package com.example.cmpm.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.cmpm.MainActivity;
import com.example.cmpm.R;
import com.example.cmpm.ui.LoginActivity;
import com.google.api.Authentication;

import java.security.Signature;


public class InfoFragment extends Fragment {

    Button btnOut;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_info, container, false);
        btnOut = v.findViewById(R.id.btnOut);

        btnOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.auth.signOut();
                Intent i = new Intent(getContext(), LoginActivity.class);
                startActivity(i);
            }
        });

        return v;
    }
}