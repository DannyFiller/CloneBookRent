package com.example.cmpm.AdminPage;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.cmpm.Activity.InfoHoaDonActivity;
import com.example.cmpm.Adapter.BookAdapter;
import com.example.cmpm.Adapter.ChonSachThueAdapter;
import com.example.cmpm.Model.Book;
import com.example.cmpm.R;
import com.example.cmpm.ui.LoginActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ChonSachActivity extends AppCompatActivity implements ChonSachThueAdapter.CallBack{

    private ChonSachThueAdapter chonSachThueAdapter;
     ArrayList<Book> listBook;
    FirebaseFirestore db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_sach);


       db = FirebaseFirestore.getInstance();

        listBook = new ArrayList<>();
        chonSachThueAdapter = new ChonSachThueAdapter(ChonSachActivity.this,listBook, this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rcChonSach);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChonSachActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(chonSachThueAdapter);

        Intent i = getIntent();
        String idSach = i.getStringExtra("idSachChon");

        db.collection("DauSach").document(idSach).collection("SachTonKho").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot d :list)
                {
                    Book book = d.toObject(Book.class);
                    book.setId(d.getId());
                    listBook.add(book);
                }
                chonSachThueAdapter.notifyDataSetChanged();

            }

        });


    }

    @Override
    public void onClick(int position, Book book) {
        Intent i = getIntent();
        String idSach = i.getStringExtra("idSachChon");
        Intent a = getIntent();
        String id = a.getStringExtra("maHoaDon");
        String idUser = LoginActivity.auth.getUid();
        db.collection("DauSach").document(idSach).collection("SachTonKho").document(book.getId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot snapshot) {
                final long tinhTrang = snapshot.getLong("tinhTrang");

                // nếu sách đã được cho thuê
                if(tinhTrang == 1)
                {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(ChonSachActivity.this);
                    builder1.setMessage("Sách đã được cho thuê");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Oke",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }

                // nếu chưa được thuê
                else{
                    db.collection("DauSach").document(idSach).collection("SachTonKho").document(book.getId()).update("tinhTrang",1);
                    db.collection("HoaDon").document(id).collection("danhSach").document(idSach).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot snapshot) {
                            final long   kiemTra = snapshot.getLong("tinhTrang");
                            db.collection("HoaDon").document(id).collection("danhSach").document(idSach).update("tinhTrang",1);
                            if(kiemTra == 0)
                            {
                                db.collection("DauSach").document(idSach).collection("SachTonKho").document(book.getId()).update("tinhTrang",1);
                                db.collection("HoaDon").document(id).collection("danhSach").document(idSach).update("idSachThue",book.getId());
                                db.collection("HoaDon").document(id).collection("danhSach").document(idSach).update("tinhTrang",1);
                            }else
                            {
                                db.collection("HoaDon").document(id).collection("danhSach").document(idSach).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot snapshot) {
                                        final String  idSachThue = snapshot.getString("idSachThue");
                                        db.collection("DauSach").document(idSach).collection("SachTonKho").document(idSachThue).update("tinhTrang",0).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                db.collection("DauSach").document(idSach).collection("SachTonKho").document(book.getId()).update("tinhTrang",1);
                                                db.collection("HoaDon").document(id).collection("danhSach").document(idSach).update("idSachThue",book.getId());
                                                db.collection("HoaDon").document(id).collection("danhSach").document(idSach).update("tinhTrang",1);
                                            }
                                        });
                                    }
                                });
                            }
                        }
                    });

                    finish();
                }

            }
        });



    }
}