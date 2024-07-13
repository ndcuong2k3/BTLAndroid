package com.example.btlandroidnc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.btlandroidnc.Model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChinhSuaThongTinCaNhan extends AppCompatActivity {
    DatabaseReference usersRef;
    Button btnLuu, btnHuy;
    EditText txtHovaTen, txtSDT, txtEmail, txtMK;
    TextView profileName;
    SharedPreferences sharedPreferences;
    ImageButton bt_KhuyenMai, bt_ThongBao, bt_GioHang, bt_TTCaNhan, btTrangChu;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinh_sua_thong_tin_ca_nhan);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        sharedPreferences = getSharedPreferences("com.example.sharedprerences", Context.MODE_PRIVATE);
        String myId = sharedPreferences.getString("id", "-1");
        usersRef = FirebaseDatabase.getInstance().getReference("Users").child(myId);
        profileName = findViewById(R.id.profileName);
        txtHovaTen = findViewById(R.id.edHoVaTen);
        txtEmail = findViewById(R.id.edEmail);
        txtSDT = findViewById(R.id.edSDT);
        btnLuu = findViewById(R.id.buttonSave);
        btnHuy = findViewById(R.id.buttonCancel);
        bt_ThongBao = findViewById(R.id.button3);
        bt_TTCaNhan = findViewById(R.id.button5);
        bt_KhuyenMai = findViewById(R.id.button2);
        bt_GioHang = findViewById(R.id.button4);
        btTrangChu = findViewById(R.id.button1);
        txtMK = findViewById(R.id.edMatKhau);

        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user != null) {
                    profileName.setText(user.getName());
                    txtHovaTen.setText(user.getName());
                    txtEmail.setText(user.getEmail());
                    txtSDT.setText(user.getPhone());
                    txtMK.setText(user.getPassword());

                } else {
                    Toast.makeText(ChinhSuaThongTinCaNhan.this, "User data is null", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ChinhSuaThongTinCaNhan.this, "Failed to retrieve user data", Toast.LENGTH_SHORT).show();
            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hoVaTen = txtHovaTen.getText().toString().trim();
                String sdt = txtSDT.getText().toString().trim();
                String email = txtEmail.getText().toString().trim();
                String mk = txtMK.getText().toString().trim();
                String id = email.replace(".","");

                if (hoVaTen.isEmpty() || sdt.isEmpty() || email.isEmpty() || mk.isEmpty()) {
                    Toast.makeText(ChinhSuaThongTinCaNhan.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                usersRef.getParent().addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        boolean emailExists = false;
                        boolean phoneExists = false;

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            User user = snapshot.getValue(User.class);
                            if (user != null) {
                                if (email.equals(user.getEmail()) && !snapshot.getKey().equals(myId)) {
                                    emailExists = true;
                                }
                                if (sdt.equals(user.getPhone()) && !snapshot.getKey().equals(myId)) {
                                    phoneExists = true;
                                }
                                if (emailExists || phoneExists) {
                                    break;
                                }
                            }
                        }

                        if (emailExists) {
                            Toast.makeText(ChinhSuaThongTinCaNhan.this, "Email đã tồn tại", Toast.LENGTH_SHORT).show();
                        } else if (phoneExists) {
                            Toast.makeText(ChinhSuaThongTinCaNhan.this, "Số điện thoại đã tồn tại", Toast.LENGTH_SHORT).show();
                        } else {
                            // Update the User object
                            usersRef.child("name").setValue(hoVaTen);
                            usersRef.child("phone").setValue(sdt);
                            usersRef.child("email").setValue(email);
                            usersRef.child("id").setValue(id);
                            usersRef.child("password").setValue(mk)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(ChinhSuaThongTinCaNhan.this, "Thông tin đã được cập nhật", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(ChinhSuaThongTinCaNhan.this, TrangCaNhan.class);
                                            startActivity(intent);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(ChinhSuaThongTinCaNhan.this, "Không thể cập nhật thông tin. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(ChinhSuaThongTinCaNhan.this, "Không thể kiểm tra email và số điện thoại. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChinhSuaThongTinCaNhan.this, TrangCaNhan.class);
                startActivity(intent);
            }
        });




        bt_TTCaNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ChinhSuaThongTinCaNhan.this, TrangCaNhan.class);
                startActivity(i);
            }
        });

        bt_ThongBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ChinhSuaThongTinCaNhan.this,TrangThongBao.class);
                startActivity(i);
            }
        });


        bt_KhuyenMai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ChinhSuaThongTinCaNhan.this, TrangKhuyenMai.class);
                startActivity(i);
            }
        });

        // Xử lý khi người dùng click vào nút Giỏ hàng
        bt_GioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChinhSuaThongTinCaNhan.this, TrangGioHang.class);
                startActivity(intent);
            }
        });

        btTrangChu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChinhSuaThongTinCaNhan.this, TrangChu.class);
                startActivity(intent);
            }
        });

    }
}
