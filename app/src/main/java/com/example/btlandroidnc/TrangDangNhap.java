package com.example.btlandroidnc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TrangDangNhap extends AppCompatActivity {
    TextView txtCheck;
    String ID; int frontId;
    EditText edTK,edMK;


    private DatabaseReference usersRef;
    @Override



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_dang_nhap);
        edTK = findViewById(R.id.editTextUsername) ;
        edMK = findViewById(R.id.editTextPassword);
        Button btDangNhap = findViewById(R.id.buttonLogin);
        Button btDangKy = findViewById(R.id.btnRegister);


        txtCheck = findViewById(R.id.textViewcheckDN);
        usersRef = FirebaseDatabase.getInstance().getReference("Users");
        btDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(TrangDangNhap.this, TrangDangKy.class);
                startActivity(i);
            }
        });
        btDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkUserExists(edTK.getText().toString());
            }
        });
    }

    private void checkUserExists( String userId) {
        frontId= edTK.getText().toString().indexOf('@');
        //ID = edTK.getText().toString().substring(0,frontId);
        ID = edTK.getText().toString().replace(".","");
        //lắng nghe sự kiện 1 lần
        usersRef.addListenerForSingleValueEvent(new  ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(ID).exists()) {

                    //   if (dataSnapshot.hasChild(userId)) {
                    //    txtCheck.setText("Tai khoản đã tồn tại");
                    //      Log.d("TAG", "Item exists!");
//                    } else {
//                        txtCheck.setText("Tai khoản chưa tồn tại");
//                        Log.d("TAG", "Item does not exist!");
//                    }

                    if(dataSnapshot.child(ID).child("password").getValue(String.class).equals(edMK.getText().toString())) {
                        txtCheck.setText("Đăng nhâp thành công");

                        SharedPreferences sharedPreferences = getSharedPreferences("com.example.sharedprerences", Context.MODE_PRIVATE);

// Mở Editor để chỉnh sửa SharedPreferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();

// Đặt giá trị cho key "my_key" là "my_value"
                        editor.putString("id",ID );

// Lưu thay đổi
                        editor.commit();
                        if(edTK.getText().toString().equals("-1")){
                            Intent i =new Intent(TrangDangNhap.this, TrangAdmin.class);
                            //i.putExtra("id",ID);
                            startActivity(i);
                        }
                        else {
                            Intent i = new Intent(TrangDangNhap.this, TrangChu.class);
                            i.putExtra("id", ID);
                            startActivity(i);
                        }

                    }
                    else {
                        //SharedPreferences sharedPreferences = getSharedPreferences("com.example.sharedprerences", Context.MODE_PRIVATE);
                        //txtCheck.setText(sharedPreferences.getString("id", "-1"));
                        txtCheck.setText("Sai mật khẩu");
                    }

                } else {
                    // Danh sách mặt hàng trống
                    txtCheck.setText("Tai khoản chưa tồn tại");
                    Log.d("TAG", "No items found!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi
                Log.e("TAG", "DatabaseError: " + databaseError.getMessage());
            }
        });
    }

}