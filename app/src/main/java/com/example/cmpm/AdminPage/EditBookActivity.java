package com.example.cmpm.AdminPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmpm.Adapter.GioHangAdapter;
import com.example.cmpm.Adapter.SachTonKhoAdapter;
import com.example.cmpm.Model.Book;
import com.example.cmpm.R;
import com.example.cmpm.ui.LoginActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class EditBookActivity extends AppCompatActivity implements SachTonKhoAdapter.CallBack{
    private SachTonKhoAdapter sachTonKhoAdapter;
    private ArrayList<Book> listSachTon;
    FirebaseFirestore db;

    Button btnThemGio,btnThanhToan,btnXoa;
    ImageView imDetail,ivFavourite;
    TextView tenSach,tvGiaMua,tvGiaThue,lbMota,tvNoi,tvNoiDung;
    String idBook;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listSachTon = new ArrayList<>();

        //Ánh xạ
        tenSach = findViewById(R.id.tvTenSachDetailED);
        tvGiaMua = findViewById(R.id.tvGiaED);
        tvGiaThue = findViewById(R.id.tvGiaThueED);
        imDetail = findViewById(R.id.ivDetailED);
        btnXoa = findViewById(R.id.btnXoa);

        db = FirebaseFirestore.getInstance();

        sachTonKhoAdapter = new SachTonKhoAdapter(EditBookActivity.this, listSachTon, this);
        RecyclerView recyclerView = findViewById(R.id.rcSachTonKho);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(EditBookActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(sachTonKhoAdapter);

        Intent i = getIntent();
        String image = i.getStringExtra("image");
        String ten = i.getStringExtra("ten");
        idBook = i.getStringExtra("id");
        String tacGia = i.getStringExtra("tacgia");
        String phanLoai = i.getStringExtra("phanloai");
        int gia = i.getIntExtra("gia", 0);
        int giaThue = i.getIntExtra("giaThue", 0);
        String idUser = LoginActivity.auth.getUid();

        //Load ảnh từ link lấy từ storage trên firebase
        Picasso.get().load(image).into(imDetail);
        tenSach.setText(ten);
        tvGiaMua.setText(String.valueOf(gia));
        tvGiaThue.setText(String.valueOf(giaThue));


        db.collection("DauSach").document(idBook).collection("SachTonKho").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot d : list) {
                    Book book = d.toObject(Book.class);
                    listSachTon.add(book);
                }
                sachTonKhoAdapter.notifyDataSetChanged();
            }

        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //xóa toán bộ sách tồn kho
                db.collection("DauSach").document(idBook).collection("DauSach").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        queryDocumentSnapshots.getDocuments().forEach(snapshot -> snapshot.getReference().delete());
                    }
                });
                //xóa đầu sách
                db.collection("DauSach").document(idBook).delete();
                Toast.makeText(EditBookActivity.this, "Đã xóa", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(EditBookActivity.this,ListBookAdminActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onClick(int position, Book book) {
        db.collection("DauSach").document(idBook).collection("SachTonKho").document(book.getId()).delete();
        listSachTon.remove(book);
        sachTonKhoAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Đã xóa", Toast.LENGTH_SHORT).show();
    }
}