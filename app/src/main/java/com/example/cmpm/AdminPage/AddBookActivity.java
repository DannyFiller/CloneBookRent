package com.example.cmpm.AdminPage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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

import java.util.HashMap;
import java.util.Map;

public class AddBookActivity extends AppCompatActivity {

    EditText edTenSach,edTacGia,edGia,edPhanLoai,edSoLuong,edMota;
    ImageView imUpload;
    Button btnUpload;
    Uri ImageUri;

    Spinner spPhanLoai;

    FirebaseFirestore db ;
    FirebaseStorage firebaseStorage;
    String id;

    String setID;
    int numberId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        edTenSach = findViewById(R.id.edTenSachAdd);
        edTacGia = findViewById(R.id.edTacGiaAdd);
        edPhanLoai = findViewById(R.id.edPhanLoai);
        edSoLuong = findViewById(R.id.edSoLuongAdd);
        edGia = findViewById(R.id.edGiaAdd);
        edMota = findViewById(R.id.edMota);

        imUpload = findViewById(R.id.ivUpload);

        btnUpload = findViewById(R.id.btnUpload);

        imUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadImage();
            }
        });

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
                        Toast.makeText(AddBookActivity.this, "không lấy ảnh được", Toast.LENGTH_SHORT).show();
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

                                //Tạo ID
                                if(book.getLoai().equals("Manga"))
                                {
                                    CheckSoLuong("Manga");
                                    setID = "MG";
                                    // lay so luong
                                    AddBook(book);
                                }else if(book.getLoai().equals("Manhwa")){
                                    CheckSoLuong("Manhwa");
                                    setID = "MW";
                                    // lay so luong
                                    AddBook(book);
                                }else if(book.getLoai().equals("Tiểu thuyết")){
                                    CheckSoLuong("Tiểu thuyết");
                                    setID = "TT";
                                    // lay so luong
                                    AddBook(book);
                                }else if(book.getLoai().equals("Giáo dục")){
                                    CheckSoLuong("Giáo dục");
                                    setID = "GD";
                                    // lay so luong
                                    AddBook(book);
                                }

                                Toast.makeText(AddBookActivity.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
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
        db.collection("DauSach").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                setID = setID+numberId;
                db.collection("DauSach").document(String.valueOf(setID)).set(addBook).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
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
                                    Toast.makeText(AddBookActivity.this, setID, Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(AddBookActivity.this,ListBookAdminActivity.class);
                                    startActivity(i);
                                }
                            });
                        }
                    }
                });
            }
        });
    }


    void CheckSoLuong(String phanLoai){

        AggregateQuery queryTheoPL = db.collection("DauSach").whereEqualTo("loai",phanLoai).count();
        queryTheoPL.get(AggregateSource.SERVER).addOnCompleteListener(new OnCompleteListener<AggregateQuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<AggregateQuerySnapshot> task) {
                if (task.isSuccessful()) {
                    // Count fetched successfully
                    AggregateQuerySnapshot snapshot = task.getResult();
                    numberId = (int) snapshot.getCount();
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
}