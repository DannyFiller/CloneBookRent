package com.example.cmpm.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmpm.Adapter.BookAdapter;
import com.example.cmpm.Adapter.ChonSachThueAdapter;
import com.example.cmpm.Adapter.GioHangAdapter;
import com.example.cmpm.Adapter.HoaDonAdapter;
import com.example.cmpm.Adapter.ListThueAdapter;
import com.example.cmpm.AdminPage.ChonSachActivity;
import com.example.cmpm.AdminPage.HoaDonActivity;
import com.example.cmpm.AdminPage.ListBookAdminActivity;
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

public class InfoHoaDonActivity extends AppCompatActivity implements ListThueAdapter.CallBack{

    RecyclerView rcView;
    private ListThueAdapter listThueAdapter;
    ArrayList<Book> CTHoaDon;
    CollectionReference ref;
    TextView tvTenKH;

    private ArrayList<Book> listBooks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_hoa_don);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CTHoaDon = new ArrayList<>();

        listThueAdapter = new ListThueAdapter(InfoHoaDonActivity.this,CTHoaDon,this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rcInfoHD);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(InfoHoaDonActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(listThueAdapter);

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
                listThueAdapter.notifyDataSetChanged();
            }

        });


    }

    @Override
    public void onClick(int position, Book book) {
        Intent i = new Intent(this, ChonSachActivity.class);
        i.putExtra("idSachChon",book.getId());

        Intent a = getIntent();
        String id = a.getStringExtra("maHoaDon");


        i.putExtra("maHoaDon",id);

        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
        startActivity(i);
    }
}