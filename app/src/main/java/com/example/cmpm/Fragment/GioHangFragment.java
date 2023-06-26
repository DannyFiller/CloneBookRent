package com.example.cmpm.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmpm.Adapter.BookAdapter;
import com.example.cmpm.Adapter.GioHangAdapter;
import com.example.cmpm.Model.Book;
import com.example.cmpm.R;
import com.example.cmpm.ui.LoginActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class GioHangFragment extends Fragment implements BookAdapter.CallBack, GioHangAdapter.CallBack {

    final int tongtien = 0;
    GioHangAdapter gioHangAdapter;
    ArrayList<Book> listBook;
    CollectionReference ref;
    FirebaseFirestore db;
    TextView tvTongTien;
    final int[] i = {0};
    String idUser = LoginActivity.auth.getUid();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_giohang, container, false);
        db = FirebaseFirestore.getInstance();

        tvTongTien = v.findViewById(R.id.tvTongTien);

//        ref = db.collection("Sach");

        listBook = new ArrayList<>();

        gioHangAdapter = new GioHangAdapter(getContext(), listBook,this);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.rcGiaHang);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(gioHangAdapter);

        db.collection("User").document(idUser).collection("GioHang")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d :list)
                        {
                            Book book = d.toObject(Book.class);
                            book.setId(d.getId());
                            listBook.add(book);


                            listBook.forEach(b -> {
                                i[0] +=b.getGia();
                            });

                            tvTongTien.setText(String.valueOf(i[0]));
                        }
                        gioHangAdapter.notifyDataSetChanged();
                    }
                });






        return v;

    }

    @Override
    public void onClick(int position, Book book) {
        db.collection("User").document(idUser).collection("GioHang").document(book.getId()).delete();
        listBook.remove(book);
        Toast.makeText(getContext(), "đã xoa", Toast.LENGTH_SHORT).show();


        gioHangAdapter.notifyDataSetChanged();
    }
}