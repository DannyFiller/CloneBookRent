package com.example.cmpm.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cmpm.R;
import com.example.cmpm.ui.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class InfoBookActivity extends AppCompatActivity {

    ImageView imDetail,ivFavourite;
    TextView tenSach,Gia,lbMota,tvNoi,tvNoiDung;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    Button btnThemGio,btnThanhToan;

    DocumentReference ref;

    FirebaseAuth auth;


    @Override
    protected void onStart() {
        super.onStart();
        //kiểm tra dánh dấu sách
        ref.get().addOnSuccessListener(KiemTraYeuThich());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_book);

        //Ánh xạ
        tenSach = findViewById(R.id.tvTenSachDetail);
        Gia = findViewById(R.id.tvGia);
        lbMota = findViewById(R.id.lbMota);
        tvNoiDung = findViewById(R.id.tvNoiDung);
        imDetail = findViewById(R.id.ivDetail);
        ivFavourite = findViewById(R.id.ivFavourite);
        btnThemGio = findViewById(R.id.btnThemGio);

        //chuyển dữ liệu từ trang danh sách sang trang chi tiết sách
        Intent i = getIntent();
        String image = i.getStringExtra("image");
        String ten = i.getStringExtra("ten");
        String id= i.getStringExtra("id");
        String tacGia =i.getStringExtra("tacgia");
        String phanLoai = i.getStringExtra("phanloai");
        int gia = i.getIntExtra("gia",0);
        String idUser = LoginActivity.auth.getUid();

        //Load ảnh từ link lấy từ storage trên firebase
        Picasso.get().load(image).into(imDetail);
        tenSach.setText(ten);

        ref =db.collection("User").document(idUser).collection("KhoSach").document(id);

        //Sự kiện nút yêu thích
        ivFavourite.setOnClickListener(BamNutYeuThich(image, ten, id, idUser));

        btnThemGio.setOnClickListener(ThemGioHang(image, ten, id,tacGia,phanLoai,gia, idUser));
    }

    @NonNull
    private OnSuccessListener<DocumentSnapshot> KiemTraYeuThich() {
        return new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    double yeuThich = documentSnapshot.getDouble("YeuThich");
                    if (yeuThich == 1) {
                        ivFavourite.setImageResource(R.drawable.baseline_favorite_24);
                    }
                }
            }
        };
    }

    @NonNull
    private View.OnClickListener BamNutYeuThich(String image, String ten, String id, String idUser) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("User").document(idUser).collection("KhoSach").document(id)
                        .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            double yeuThich = documentSnapshot.getDouble("YeuThich");
                            if (yeuThich == 1) {
                                ivFavourite.setImageResource(R.drawable.baseline_favorite_border_24);
                                db.collection("User").document(idUser).collection("KhoSach").document(id).delete();
                            }
                        }
                        else
                        {
                            ivFavourite.setImageResource(R.drawable.baseline_favorite_24);
                            Map<String, Object> FarBook = new HashMap<>();
                            FarBook.put("id", id);
                            FarBook.put("image", image);
                            FarBook.put("YeuThich", 1);
                            FarBook.put("TenSach", ten);
                            db.collection("User").document(idUser).collection("KhoSach").document(id).set(FarBook);
                        }
                    }
                });
            }
        };
    }

    @NonNull
    private View.OnClickListener ThemGioHang(String image, String ten, String id,String tacGia,String phanLoai,int gia, String idUser) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> rentBook = new HashMap<>();
                rentBook.put("id", id);
                rentBook.put("tenSach", ten);
                rentBook.put("tacGia", tacGia);
                rentBook.put("loai", phanLoai);
                rentBook.put("gia",gia);
                rentBook.put("image", image);

                db.collection("User").document(idUser).collection("GioHang").document(id).set(rentBook);
            }
        };
    }
}