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

import com.example.cmpm.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.api.Authentication;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText edEmail,edPassword,edRepassword,edTenND,edSDT;
    Button btnRegister;
    FirebaseFirestore db;
    TextView tvBackLogin;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        edEmail  = findViewById(R.id.edTextUserNameRg);
        edPassword = findViewById(R.id.edTextPasswordRg);
        edRepassword = findViewById(R.id.edTextRePasswordRg);
        edTenND = findViewById(R.id.edTenKhach);
        edSDT = findViewById(R.id.edSDT);

        tvBackLogin = findViewById(R.id.tvBackLogin);

        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edEmail.getText().toString().trim();
                String password = edPassword.getText().toString().trim();
                String repassword = edRepassword.getText().toString().trim();
                String ten = edTenND.getText().toString().trim();
                String sdt = edSDT.getText().toString().trim();

                // kiem tra co trong ko
                if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(repassword) || TextUtils.isEmpty(ten) || TextUtils.isEmpty(sdt))
                {
                    Toast.makeText(RegisterActivity.this, "Vui Lòng Nhập Đầy Đủ Thông Tin", Toast.LENGTH_SHORT).show();
                }else{
                    if(password.equals(repassword))
                    {
                        //dang ky tai khoan firebase
                        auth.createUserWithEmailAndPassword(username,password)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful())
                                        {
                                            HashMap<String,Object> user = new HashMap<>();
                                            user.put("gmail",username);
                                            user.put("vaiTro",0);
                                            user.put("ten",ten);
                                            user.put("sdt",sdt);

                                            db.collection("User").document(auth.getUid()).set(user);

                                            Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                        else
                                        {
                                            Toast.makeText(RegisterActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                    }
                    else
                    {
                        Toast.makeText(RegisterActivity.this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


        tvBackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });
    }
}