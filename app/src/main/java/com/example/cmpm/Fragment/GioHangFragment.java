package com.example.cmpm.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmpm.Adapter.BookAdapter;
import com.example.cmpm.Adapter.GioHangAdapter;
import com.example.cmpm.MainActivity;
import com.example.cmpm.Model.Book;
import com.example.cmpm.Model.User;
import com.example.cmpm.R;
import com.example.cmpm.ui.LoginActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class GioHangFragment extends Fragment implements BookAdapter.CallBack, GioHangAdapter.CallBack {

    final int tongtien = 0;
    GioHangAdapter gioHangAdapter;
    ArrayList<Book> listBook;
    CollectionReference ref;
    FirebaseFirestore db;
    TextView tvTongTien;
    Button btnThanhToan;


    String idUser = LoginActivity.auth.getUid();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_giohang, container, false);
        getActivity().setTitle("Giỏ Hàng");
        db = FirebaseFirestore.getInstance();

        tvTongTien = v.findViewById(R.id.tvTongTien);
        btnThanhToan = v.findViewById(R.id.btnThanhToanGH);
        String idUser = LoginActivity.auth.getUid();

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
                        }
                        gioHangAdapter.notifyDataSetChanged();

                        int i = 0;

                        for(Book book : listBook)
                        {
                            i+= book.getGiaThue();
                            tvTongTien.setText(String.valueOf(i));
                        }
                    }
                });

        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for(int i = 0 ; i< listBook.size();i++)
                {
                    SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss");
                    SimpleDateFormat ngayThue = new SimpleDateFormat("ddMMyyyyHHmm");

                    String currentDateandTime = sdf.format(new Date());
                    String ngayThueSach = ngayThue.format(new Date());

                    HashMap<String,Object> hd = new HashMap<>();
                    hd.put("maHoaDon",currentDateandTime);
                    hd.put("maKH",idUser);
                    hd.put("ngayThue",ngayThueSach);
                    hd.put("tongTien", Integer.parseInt(tvTongTien.getText().toString()));
                    hd.put("TenKh", LoginActivity.curUser.getTen());

                    db.collection("HoaDon").document(currentDateandTime).set(hd);


                    db.collection("HoaDon").document(currentDateandTime).collection("danhSach").document(listBook.get(i).getId()).set(listBook.get(i));
                    db.collection("User").document(idUser).collection("GioHang").document(listBook.get(i).getId()).delete();
                }
                Toast.makeText(getContext(), "Bạn đã thanh toán thành công", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getContext(), MainActivity.class);
                startActivity(i);
            }

        });
        return v;
    }



    @Override
    public void onClick(int position, Book book) {
        db.collection("User").document(idUser).collection("GioHang").document(book.getId()).delete();
        listBook.remove(book);
        Toast.makeText(getContext(), "đã xoa", Toast.LENGTH_SHORT).show();

        int a = Integer.valueOf(tvTongTien.getText().toString().trim());
        int b = a - book.getGia();
        tvTongTien.setText(String.valueOf(b));
        gioHangAdapter.notifyDataSetChanged();
    }

    @Override
    public void setFilteredList(ArrayList<Book> filteredList) {

    }
}