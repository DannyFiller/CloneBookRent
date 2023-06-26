package com.example.cmpm.AdminPage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cmpm.MainActivity;
import com.example.cmpm.Model.Book;
import com.example.cmpm.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firestore.v1.StructuredQuery;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class AddBookActivity extends AppCompatActivity {

    EditText edTenSach,edTacGia,edPhanLoai,edGia,edSoLuong;
    ImageView imUpload;
    Button btnUpload;
    Uri ImageUri;

    FirebaseFirestore db ;
    FirebaseStorage firebaseStorage;


    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        db = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        edTenSach = findViewById(R.id.edTenSachAdd);
        edTacGia = findViewById(R.id.edTacGiaAdd);
        edPhanLoai = findViewById(R.id.edPhanLoaiAdd);
        edSoLuong = findViewById(R.id.edSoLuongAdd);
        edGia = findViewById(R.id.edGiaAdd);

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
                                book.setImage(uri.toString());
                                db.collection("DauSach").add(book).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        id = documentReference.getId();
                                        documentReference.update("id",id);

                                        // tạo ra số lượng sách
                                        for (int i = 0; i <book.getSoLuong();i++)
                                        {
                                            Book book1 = new Book();
                                            book1.setTenSach(edTenSach.getText().toString());
                                            book1.setTacGia(edTacGia.getText().toString());
                                            book1.setLoai(edPhanLoai.getText().toString());
                                            book1.setGia(Integer.valueOf(edGia.getText().toString()));
                                            book1.setImage(uri.toString());
                                            db.collection("DauSach").document(id).collection("SachTonKho").document(String.valueOf(i)).set(book1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(AddBookActivity.this, "Uploaded " +book.getSoLuong() , Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    }
                                });




                                Toast.makeText(AddBookActivity.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
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