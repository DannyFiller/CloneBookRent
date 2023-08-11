package com.example.cmpm.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmpm.AdminPage.ListBookAdminActivity;
import com.example.cmpm.MainActivity;
import com.example.cmpm.Model.User;
import com.example.cmpm.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    EditText edEmail, edPassword;
    Button btnLogin;
    TextView tvReAcc;

    public static FirebaseAuth auth;
    public static User curUser;
    FirebaseFirestore db;
    String id;


    @Override
    protected void onStart() {
        super.onStart();
         FirebaseUser currentUser = auth.getCurrentUser();;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        edEmail = findViewById(R.id.edTextUserNameLg);
        edPassword = findViewById(R.id.edTextPasswordLg);

        tvReAcc = findViewById(R.id.tvReAcc);

        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edEmail.getText().toString().trim();
                String password = edPassword.getText().toString().trim();


                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password))
                {
                    Toast.makeText(LoginActivity.this, "Vui Lòng Nhập Đầy Đủ Thông Tin", Toast.LENGTH_SHORT).show();
                }else{
                    auth.signInWithEmailAndPassword(email,password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                        id = auth.getCurrentUser().getUid();
                                        db.collection("User").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                curUser = documentSnapshot.toObject(User.class);
                                                if(curUser.getVaiTro() == 1){
                                                    Intent i = new Intent(LoginActivity.this, ListBookAdminActivity.class);
                                                    startActivity(i);
                                                }else{
                                                    Intent i = new Intent(LoginActivity.this,MainActivity.class);
                                                    startActivity(i);
                                                }
                                            }
                                        });
                                    }
                                    else
                                    {
                                        Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        tvReAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });

    }
}