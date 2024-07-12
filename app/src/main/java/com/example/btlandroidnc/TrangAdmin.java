package com.example.btlandroidnc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class TrangAdmin extends AppCompatActivity {
    Button btUpdateSP, btXemTTKhachHang, btDuyetDonHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_admin);

        btXemTTKhachHang = findViewById(R.id.btXemTTKhachHang);
        btDuyetDonHang =findViewById(R.id.btDuyetDonHang);
        btDuyetDonHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(TrangAdmin.this, TrangDuyetDonHang.class);
                startActivity(i);
            }
        });
        btXemTTKhachHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TrangAdmin.this, TrangXemThongTinKH.class);
                startActivity(i);
            }
        });

        Button btDangXuatAdmin = findViewById(R.id.btDangXuatAdmin);
        btDangXuatAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xóa dữ liệu người dùng đã lưu (nếu có)
                // Ví dụ: clear SharedPreferences
                // SharedPreferences sharedpreferences = getSharedPreferences("com.example.sharedprerences", Context.MODE_PRIVATE);
                // sharedpreferences.edit().clear().apply();

                Intent intent = new Intent(TrangAdmin.this, TrangDangNhap.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });


        mapping_client();

        this.btUpdateSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TrangAdmin.this, TrangUpdateSP.class);
                startActivity(i);
            }
        });
    }


    private void mapping_client() {
        this.btUpdateSP = findViewById(R.id.btUpdateSp);
    }
}