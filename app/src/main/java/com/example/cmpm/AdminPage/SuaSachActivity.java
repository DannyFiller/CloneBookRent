package com.example.cmpm.AdminPage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmpm.Model.Book;
import com.example.cmpm.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class SuaSachActivity extends AppCompatActivity {

    EditText edTenSach,edTacGia,edGia,edPhanLoai,edSoLuong,edMota,edGiaThue;
    ImageView imUpload;
    Button btnUpload;
    Uri ImageUri;

    Spinner spPhanLoai;

    FirebaseFirestore db ;
    FirebaseStorage firebaseStorage;
    String id;

    String setID;
    int numberId ;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_sach);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        edTenSach = findViewById(R.id.edTenSachSua);
        edTacGia = findViewById(R.id.edTacGiaSua);
        edPhanLoai = findViewById(R.id.edPhanLoaiSua);
        edSoLuong = findViewById(R.id.edSoLuongSua);
        edGia = findViewById(R.id.edGiaSua);
        edMota = findViewById(R.id.edMotaSua);
        edGiaThue = findViewById(R.id.edGiaThueSua);

        imUpload = findViewById(R.id.ivUploadSua);

        btnUpload = findViewById(R.id.btnUploadSua);

        imUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadImage();
            }
        });

        LoadThongTin();

    }


    // load ảnh từ điện thoại
    private void UploadImage(){
        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Intent i = new Intent();
                        i.setType("image/*");
                        i.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(i,101);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(SuaSachActivity.this, "không lấy ảnh được", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();



        //Upload ảnh lên storage trên firebase
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final StorageReference ref = firebaseStorage.getReference().child("book")
                        .child(System.currentTimeMillis()+"");
                ref.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Book book = new Book();
                                book.setTenSach(edTenSach.getText().toString());
                                book.setTacGia(edTacGia.getText().toString());
                                book.setLoai(edPhanLoai.getText().toString());
                                book.setGia(Integer.valueOf(edGia.getText().toString()));
                                book.setSoLuong(Integer.valueOf(edSoLuong.getText().toString()));
                                book.setMoTa(edMota.getText().toString().trim());
                                book.setImage(uri.toString());
                                book.setGiaThue(Integer.valueOf(edGiaThue.getText().toString()));
                                //Tạo ID
                                if(book.getLoai().equals("Manga"))
                                {
                                    CheckSoLuong("Manga",book);
                                    setID = "MG";
                                    // lay so luong
//                                    AddBook(book);
                                }else if(book.getLoai().equals("Manhwa")){
                                    CheckSoLuong("Manhwa",book);
                                    setID = "MW";
                                    // lay so luong
//                                    AddBook(book);
                                }else if(book.getLoai().equals("Tiểu thuyết")){
                                    CheckSoLuong("Tiểu thuyết",book);
                                    setID = "TT";
                                    // lay so luong
//                                    AddBook(book);
                                }else if(book.getLoai().equals("Giáo dục")){
                                    CheckSoLuong("Giáo dục",book);
                                    setID = "GD";
                                    // lay so luong
//                                    AddBook(book);
                                }else if(book.getLoai().equals("Kinh tế")){
                                    CheckSoLuong("Kinh tế",book);
                                    setID = "KT";
                                    // lay so luong
//                                    AddBook(book);
                                }else if(book.getLoai().equals("Lập trính")){
                                    CheckSoLuong("Lập trình",book);
                                    setID = "LT";
                                    // lay so luong
//                                    AddBook(book);
                                }else if(book.getLoai().equals("Tâm lý")){
                                    CheckSoLuong("Tâm lý",book);
                                    setID = "TL";
                                    // lay so luong
//                                    AddBook(book);
                                }

                                Toast.makeText(SuaSachActivity.this, "Image Uploaded", Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                });
            }
        });
    }

    void AddBook(Book book){

        Map<String, Object> addBook = new HashMap<>();
        addBook.put("tenSach", book.getTenSach());
        addBook.put("tacGia", book.getTacGia());
        addBook.put("loai", book.getLoai());
        addBook.put("gia", book.getGia());
        addBook.put("soLuong", book.getSoLuong());
        addBook.put("moTa", book.getMoTa());
        addBook.put("image", book.getImage());
        addBook.put("id",book.getId());
        addBook.put("giaThue",book.getGiaThue());

        //Xóa sách tồn kho để cập nhật số lượng
        //Xóa sách tồn kho trước rồi mới xóa đầu sách
        db.collection("DauSach").document(id).collection("SachTonKho").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@androidx.annotation.NonNull Task<QuerySnapshot> task) {
                for(QueryDocumentSnapshot snapshot : task.getResult()){
                    db.collection("DauSach").document(id).collection("SachTonKho").document(snapshot.getId()).delete();
                }
            }
        });

        //xóa sách đầu sách
        db.collection("DauSach").document(id).delete();


        //Thêm sách tồn kho theo số lượng cập nhật
        db.collection("DauSach").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                setID = setID+ numberId;


                db.collection("DauSach").document(String.valueOf(setID)).set(addBook).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        db.collection("DauSach").document(setID).update("id",setID);
                        //Tạo sách theo số lượng
                        for (int i = 0; i <book.getSoLuong();i++)
                        {
                            Book book1 = book;
                            //tạo id cho sách tồn kho
                            String a = String.valueOf(i);
                            String idKho = setID+"A"+a;

                            book1.setTinhTrang(0);
                            book1.setId(idKho);


                            db.collection("DauSach").document(String.valueOf(setID)).collection("SachTonKho").document(idKho).set(book1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Intent i = new Intent(SuaSachActivity.this,ListBookAdminActivity.class);
                                    startActivity(i);
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    //đếm số lượng sách theo phân loại
    void CheckSoLuong(String phanLoai,Book book){

        AggregateQuery queryTheoPL = db.collection("DauSach").whereEqualTo("loai",phanLoai).count();
        queryTheoPL.get(AggregateSource.SERVER).addOnCompleteListener(new OnCompleteListener<AggregateQuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<AggregateQuerySnapshot> task) {
                if (task.isSuccessful()) {
                    AggregateQuerySnapshot snapshot = task.getResult();
                    numberId = (int) snapshot.getCount();
                    AddBook(book);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101 && resultCode == RESULT_OK){
            ImageUri = data.getData();
            imUpload.setImageURI(ImageUri);
        }
    }

    void LoadThongTin(){
        Intent i = getIntent();

        String image = i.getStringExtra("image");
        String ten = i.getStringExtra("ten");
        id = i.getStringExtra("id");
        String tacGia = i.getStringExtra("tacgia");
        String phanLoai = i.getStringExtra("phanloai");
        int gia = i.getIntExtra("gia", 0);
        int giaThue = i.getIntExtra("giaThue", 0);
        int soLuong = i.getIntExtra("soLuong",0);
        String moTa = i.getStringExtra("moTa");

        edTenSach.setText(ten);
        edTacGia.setText(tacGia);

        edGia.setInputType(InputType.TYPE_CLASS_TEXT);
        edGia.setText(String.valueOf(gia));
        edGiaThue.setText(String.valueOf(giaThue));
        edSoLuong.setText(String.valueOf(soLuong));
        edMota.setText(moTa);
        edPhanLoai.setText(phanLoai);

        Picasso.get().load(image).into(imUpload);
    }


}