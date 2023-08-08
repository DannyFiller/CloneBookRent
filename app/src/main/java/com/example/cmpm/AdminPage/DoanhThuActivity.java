package com.example.cmpm.AdminPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

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

public class DoanhThuActivity extends AppCompatActivity implements XacNhanTraSachAdapter.CallBack{
    ArrayList<HoaDon> hoaDons;
    CollectionReference ref;
    FirebaseFirestore db;
    TextView tvTongDoanhThu;
    XacNhanTraSachAdapter xacNhanTraSachAdapter;
    private SearchView searchView;
    public int tong = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doanh_thu);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        tvTongDoanhThu = findViewById(R.id.tvTongTienDT);

        hoaDons = new ArrayList<>();

        xacNhanTraSachAdapter = new XacNhanTraSachAdapter(DoanhThuActivity.this,hoaDons,this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rcDoanhThu);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DoanhThuActivity.this, LinearLayoutManager.VERTICAL, false);
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
                    db.collection("HoaDon").document(hd.getMaHoaDon()).collection("danhSach").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for(DocumentSnapshot a : list)
                            {
                                Book book = a.toObject(Book.class);
                                tong+= book.getGiaThue();
                                tvTongDoanhThu.setText(String.valueOf(tong) +" VND");
                            }
                        }
                    });
                }
                xacNhanTraSachAdapter.notifyDataSetChanged();

            }
        });

        // tạo thanh tìm kiếm
        searchView = findViewById(R.id.searchViewDT);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }
        });
    }

    @Override
    public void onClick(int position, HoaDon hd) {
    }

    private void filterList(String text) {
        ArrayList<HoaDon> filterList = new ArrayList<>();
        for (HoaDon b : hoaDons){
            if(b.getMaHoaDon().toLowerCase().contains(text.toLowerCase())){
                filterList.add(b);
            }
        }

        if(filterList.isEmpty()){

        }
        else {
            setFilteredList(filterList);
        }
    }

    @Override
    public void setFilteredList(ArrayList<HoaDon> filteredList) {
        XacNhanTraSachAdapter.hoaDon =  filteredList;
        xacNhanTraSachAdapter.notifyDataSetChanged();
    }
}