package com.example.cmpm.AdminPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.cmpm.Activity.InfoHoaDonActivity;
import com.example.cmpm.Activity.InfoTraSachActivity;
import com.example.cmpm.Adapter.HoaDonAdapter;
import com.example.cmpm.Adapter.XacNhanTraSachAdapter;
import com.example.cmpm.Model.Book;
import com.example.cmpm.Model.HoaDon;
import com.example.cmpm.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class XNTraSachActivity extends AppCompatActivity implements XacNhanTraSachAdapter.CallBack {
    ArrayList<HoaDon> hoaDons;
    CollectionReference ref;
    FirebaseFirestore db;
    XacNhanTraSachAdapter xacNhanTraSachAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xntra_sach);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Xác nhận trả sách");


        db = FirebaseFirestore.getInstance();

        hoaDons = new ArrayList<>();

        xacNhanTraSachAdapter = new XacNhanTraSachAdapter(XNTraSachActivity.this,hoaDons,this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rcTraSach);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(XNTraSachActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(xacNhanTraSachAdapter);

        db.collection("HoaDon").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot d :list)
                {
                    HoaDon hd = d.toObject(HoaDon.class);

                    hoaDons.add(hd);
                }
                xacNhanTraSachAdapter.notifyDataSetChanged();

            }

        });
    }


    @Override
    public void onClick(int position, HoaDon hd) {
        Intent i = new Intent(XNTraSachActivity.this, InfoTraSachActivity.class);
        i.putExtra("maHoaDon",hd.getMaHoaDon());
        i.putExtra("maKH",hd.getMaKH());
        i.putExtra("tongTien",hd.getTongTien());
        i.putExtra("ngayThue",hd.getNgayThue());
        Toast.makeText(this, hd.getMaHoaDon(), Toast.LENGTH_SHORT).show();


        Intent a = new Intent(XNTraSachActivity.this, InfoTraSachActivity.class);
        a.putExtra("maHoaDon",hd.getMaHoaDon());
        a.putExtra("maKH",hd.getMaKH());
        a.putExtra("tongTien",hd.getTongTien());
        a.putExtra("ngayThue",hd.getNgayThue());
        Toast.makeText(this, hd.getMaHoaDon(), Toast.LENGTH_SHORT).show();
        startActivity(i);
    }

    @Override
    public void setFilteredList(ArrayList<HoaDon> filteredList) {

    }
}