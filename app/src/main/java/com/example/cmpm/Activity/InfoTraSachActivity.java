package com.example.cmpm.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmpm.Adapter.ListThueAdapter;
import com.example.cmpm.Adapter.TraSachAdapter;
import com.example.cmpm.AdminPage.ChonSachActivity;
import com.example.cmpm.AdminPage.HoaDonActivity;
import com.example.cmpm.Model.Book;
import com.example.cmpm.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class InfoTraSachActivity extends AppCompatActivity implements TraSachAdapter.CallBack {

    RecyclerView rcView;
    private TraSachAdapter traSachAdapter;
    ArrayList<Book> CTHoaDon;
    CollectionReference ref;
    TextView tvTenKH;
    FirebaseFirestore db;
    RecyclerView recyclerView;
    Button btnTraSach;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_tra_sach);

        btnTraSach = findViewById(R.id.btnTraSach);

        db = FirebaseFirestore.getInstance();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CTHoaDon = new ArrayList<>();

        traSachAdapter = new TraSachAdapter(InfoTraSachActivity.this,CTHoaDon,this);
        recyclerView = findViewById(R.id.rcTraSachInfo);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(InfoTraSachActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(traSachAdapter);

        Intent i = getIntent();
        String maHD = i.getStringExtra("maHoaDon");

        db.collection("HoaDon").document(maHD).collection("danhSach").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot d :list)
                {
                    Book book = d.toObject(Book.class);
                    book.setId(d.getId());
                    CTHoaDon.add(book);
                }
                traSachAdapter.notifyDataSetChanged();

            }

        });

        btnTraSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = getIntent();
                String id = a.getStringExtra("maHoaDon");
                db.collection("HoaDon").document(id).collection("danhSach").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d :list)
                            {

                                Book book1 = d.toObject(Book.class);
                                db.collection("DauSach").document(book1.getId()).collection("SachTonKho").document(book1.getIdSachThue()).update("tinhTrang",0);
                                Toast.makeText(InfoTraSachActivity.this, "Xác nhận trả sách hoàn tất", Toast.LENGTH_SHORT).show();
                                finish();

                            }
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onClick(int position, Book book) {

    }
}