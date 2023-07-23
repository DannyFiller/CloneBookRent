package com.example.cmpm.AdminPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.cmpm.Activity.InfoHoaDonActivity;
import com.example.cmpm.Adapter.GioHangAdapter;
import com.example.cmpm.Adapter.HoaDonAdapter;
import com.example.cmpm.Adapter.ListThueAdapter;
import com.example.cmpm.Model.Book;
import com.example.cmpm.Model.HoaDon;
import com.example.cmpm.R;
import com.example.cmpm.ui.LoginActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HoaDonActivity extends AppCompatActivity implements HoaDonAdapter.CallBack {
    ArrayList<HoaDon> hoaDon;
    CollectionReference ref;
    FirebaseFirestore db;
    HoaDonAdapter hoaDonAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Xác nhận sách cho thuê");


        db = FirebaseFirestore.getInstance();

        hoaDon = new ArrayList<>();

        hoaDonAdapter = new HoaDonAdapter(HoaDonActivity.this,hoaDon,this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rcHoaDon);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HoaDonActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(hoaDonAdapter);

        db.collection("HoaDon").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot d :list)
                {
                    HoaDon hd = d.toObject(HoaDon.class);

                    hoaDon.add(hd);
                }
                hoaDonAdapter.notifyDataSetChanged();

            }

        });
    }

    @Override
    public void onClick(int position, HoaDon hd) {
        Intent i = new Intent(HoaDonActivity.this, InfoHoaDonActivity.class);
        i.putExtra("maHoaDon",hd.getMaHoaDon());
        i.putExtra("maKH",hd.getMaKH());
        i.putExtra("tongTien",hd.getTongTien());
        i.putExtra("ngayThue",hd.getNgayThue());



        Intent a = new Intent(HoaDonActivity.this, ChonSachActivity.class);
        a.putExtra("maHoaDon",hd.getMaHoaDon());
        a.putExtra("maKH",hd.getMaKH());
        a.putExtra("tongTien",hd.getTongTien());
        a.putExtra("ngayThue",hd.getNgayThue());
        startActivity(i);
    }
}