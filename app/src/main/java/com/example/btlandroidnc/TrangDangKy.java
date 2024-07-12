package com.example.btlandroidnc;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.btlandroidnc.Model.User;
import com.example.btlandroidnc.Model.User_Notification;
import com.example.btlandroidnc.Model.Voucher;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TrangDangKy extends AppCompatActivity {
    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_dang_ky);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        EditText edUser = findViewById(R.id.editTextUsername);
        EditText edHoTen = findViewById(R.id.editHoTen);
        EditText edNgaySinh = findViewById(R.id.editChonNgaysinh);
        EditText edMatKhau = findViewById(R.id.editTextPassword);
        EditText edMatKhau2 = findViewById(R.id.editTextPassword2);
        EditText edSDT = findViewById(R.id.editSDT);
        ImageButton btDatePicker = findViewById(R.id.buttonDatePicker);

        Button btDangKy = findViewById(R.id.buttonDangKy);
        Button btDangnhap = findViewById(R.id.buttonDangNhap);

        btDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                // Khởi tạo DatePickerDialog và thiết lập sự kiện lắng nghe
                datePickerDialog = new DatePickerDialog(TrangDangKy.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                // Xử lý khi người dùng chọn ngày
                                String selectedDate = "";
                                String m = "", d = "";
                                if (month < 9) {
                                    m = "0" + String.valueOf(month + 1);
                                } else {
                                    m = String.valueOf(month + 1);
                                }
                                if (dayOfMonth <= 9) {
                                    d = "0" + String.valueOf(dayOfMonth);
                                } else {
                                    d = String.valueOf(dayOfMonth);
                                }
                                selectedDate = d + "/" + m + "/" + year;
                                Toast.makeText(TrangDangKy.this, "Ngày đã chọn: " + selectedDate, Toast.LENGTH_SHORT).show();
                                edNgaySinh.setText(selectedDate);
                            }
                        }, year, month, dayOfMonth);

                // Hiển thị DatePickerDialog
                datePickerDialog.show();
                Button positiveButton = datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE);
                positiveButton.setTextColor(Color.RED);
            }
        });

        btDangnhap.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(new Intent(TrangDangKy.this, TrangDangNhap.class));
                startActivity(i);
            }
        });

//        btDangKy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String username = edUser.getText().toString().trim();
//                int frontId = username.indexOf('@');
//                String idUser = username.replace(".", "");
//                String hoTen = edHoTen.getText().toString().trim();
//                String ngaySinh = edNgaySinh.getText().toString().trim();
//                String matKhau = edMatKhau.getText().toString().trim();
//                String SDT = edSDT.getText().toString().trim();
//                String matKhau2 = edMatKhau2.getText().toString().trim();
//                Date convertedDate;
//                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
//
//                try {
//                    // Chuyển đổi chuỗi thành đối tượng Date
//                    convertedDate = dateFormat.parse(ngaySinh);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                    // Xử lý khi có lỗi trong quá trình chuyển đổi
//                }
//
//                Date date_create_user = new Date();
//                ArrayList<User_Notification> user_notifications = new ArrayList<>();
//                User_Notification u_ntf = new User_Notification("1", "Bạn mới", "Chào Mừng bạn đến với ứng dụng của chúng tôi", date_create_user);
//                user_notifications.add(u_ntf);
//                User_Notification u_ntf2 = new User_Notification("2", "Giảm 100k", "Chúc Mừng bạn nhận được voucher giảm 100K cho đơn từ 0đ", date_create_user);
//                user_notifications.add(u_ntf2);
//                Date date = new Date(Integer.parseInt(ngaySinh.substring(6, 10)), Integer.parseInt(ngaySinh.substring(3, 5)), Integer.parseInt(ngaySinh.substring(0, 2)));
//                Log.d("DateDK", String.valueOf(date));
//                ArrayList<Voucher> arrayList = new ArrayList<>();
//                Voucher voucher = new Voucher("1", "New User", "Chao mung ban moi", "image", 100f, 0f);
//                arrayList.add(voucher);
//                User user = new User(idUser, hoTen, username, matKhau, date, SDT, arrayList, user_notifications);
//
//                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
//                databaseReference.child(idUser).setValue(user);
//
//                Toast.makeText(getApplicationContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
//            }
//        });
        btDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edUser.getText().toString().trim();
                int frontId = username.indexOf('@');
                String idUser = username.replace(".", "");
                String hoTen = edHoTen.getText().toString().trim();
                String ngaySinh = edNgaySinh.getText().toString().trim();
                String matKhau = edMatKhau.getText().toString().trim();
                String SDT = edSDT.getText().toString().trim();
                String matKhau2 = edMatKhau2.getText().toString().trim();

                Date convertedDate = null;
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

                try {
                    // Chuyển đổi chuỗi thành đối tượng Date
                    convertedDate = dateFormat.parse(ngaySinh);
                } catch (ParseException e) {
                    e.printStackTrace();
                    // Xử lý khi có lỗi trong quá trình chuyển đổi
                }

                Date date_create_user = new Date();
                ArrayList<User_Notification> user_notifications = new ArrayList<>();
                User_Notification u_ntf = new User_Notification("1", "Bạn mới", "Chào Mừng bạn đến với ứng dụng của chúng tôi", date_create_user);
                user_notifications.add(u_ntf);
                User_Notification u_ntf2 = new User_Notification("2", "Giảm 100k", "Chúc Mừng bạn nhận được voucher giảm 100K cho đơn từ 0đ", date_create_user);
                user_notifications.add(u_ntf2);

                // Sử dụng Calendar để tạo ngày sinh
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(convertedDate);
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                calendar.set(year, month, day);
                Date date = calendar.getTime();
                Log.d("DateDK", String.valueOf(date));

                ArrayList<Voucher> arrayList = new ArrayList<>();
                Voucher voucher = new Voucher("1", "New User", "Chao mung ban moi", "image", 100f, 0f);
                arrayList.add(voucher);
                User user = new User(idUser, hoTen, username, matKhau, date, SDT, arrayList, user_notifications);

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
                databaseReference.child(idUser).setValue(user);

                Toast.makeText(getApplicationContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
