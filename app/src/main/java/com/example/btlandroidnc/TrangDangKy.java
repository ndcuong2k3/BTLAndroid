package com.example.btlandroidnc;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.btlandroidnc.Model.User;
import com.example.btlandroidnc.Model.User_Notification;
import com.example.btlandroidnc.Model.Voucher;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

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
//                Button positiveButton = datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE);
//                positiveButton.setTextColor(Color.RED);
            }
        });

        btDangnhap.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(new Intent(TrangDangKy.this, TrangDangNhap.class));
                startActivity(i);
            }
        });


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

                if (username.isEmpty() || hoTen.isEmpty() || ngaySinh.isEmpty() || matKhau.isEmpty() || SDT.isEmpty() || matKhau2.isEmpty()) {
                    showToast("Vui lòng nhập đầy đủ thông tin");
                    return;
                }

                if (!isValidEmail(username)) {
                    showToast("Email không hợp lệ");
                    return;
                }

                if (!isValidPhoneNumber(SDT)) {
                    showToast("Số điện thoại không hợp lệ");
                    return;
                }


                if (!matKhau.equals(matKhau2)) {
                    showToast("Mật khẩu không khớp");
                    return;
                }

                DatabaseReference database = FirebaseDatabase.getInstance().getReference("Users");
                database.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean phoneExists = false;
                        boolean emailExists = false;

                        for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                            User user = userSnapshot.getValue(User.class);
                            if (user != null) {
                                if (user.getPhone().equals(SDT)) {
                                    phoneExists = true;
                                    break;
                                }
                                if (user.getEmail().equals(username)) {
                                    emailExists = true;
                                    break;
                                }
                            }
                        }

                        if (phoneExists) {
                            showToast("Đã tồn tại tài khoản với số điện thoại " + SDT);
                        } else if (emailExists) {
                            showToast("Đã tồn tại tài khoản với email " + username);
                        } else {
                            // Nếu không tồn tại số điện thoại và email, tiến hành đăng ký
                            registerUser(idUser, hoTen, username, matKhau, ngaySinh, SDT);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
//                Date convertedDate = null;
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
//                Calendar dob = Calendar.getInstance();
//                dob.setTime(convertedDate);
//
//                Calendar today = Calendar.getInstance();
//                int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
//                if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
//                    age--;
//                }
//
//                if (age < 18) {
//                    showToast("Bạn phải đủ 18 tuổi trở lên để đăng ký");
//                    return;
//                }
//
//                Date date_create_user = new Date();
//                ArrayList<User_Notification> user_notifications = new ArrayList<>();
//                User_Notification u_ntf = new User_Notification("1", "Bạn mới", "Chào Mừng bạn đến với ứng dụng của chúng tôi", date_create_user);
//                user_notifications.add(u_ntf);
//                User_Notification u_ntf2 = new User_Notification("2", "Giảm 100k", "Chúc Mừng bạn nhận được voucher giảm 100K cho đơn từ 0đ", date_create_user);
//                user_notifications.add(u_ntf2);
//
//                // Sử dụng Calendar để tạo ngày sinh
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime(convertedDate);
//                int year = calendar.get(Calendar.YEAR);
//                int month = calendar.get(Calendar.MONTH);
//                int day = calendar.get(Calendar.DAY_OF_MONTH);
//                calendar.set(year, month, day);
//                Date date = calendar.getTime();
//                Log.d("DateDK", String.valueOf(date));
//
//                ArrayList<Voucher> arrayList = new ArrayList<>();
//                Voucher voucher = new Voucher("1", "New User", "Chao mung ban moi", "image", 100f, 0f);
//                arrayList.add(voucher);
////                User user = new User(idUser, hoTen, username, matKhau, date, SDT, arrayList, user_notifications);
//                User user = new User(idUser, hoTen, username, matKhau, date, SDT, arrayList, user_notifications,arrayList);
//                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
////                databaseReference.child(idUser).setValue(user);
//                databaseReference.child(idUser).child("id").setValue(idUser);
//                databaseReference.child(idUser).child("name").setValue(user.getName());
//                databaseReference.child(idUser).child("email").setValue(user.getEmail());
//                databaseReference.child(idUser).child("phone").setValue(user.getPhone());
//                databaseReference.child(idUser).child("password").setValue(user.getPassword());
//                databaseReference.child(idUser).child("vouchers").setValue(arrayList);
//                databaseReference.child(idUser).child("user_notifications").setValue(user_notifications);
//                databaseReference.child(idUser).child("date_of_birth").child("day").setValue(day);
//                databaseReference.child(idUser).child("date_of_birth").child("month").setValue(month);
//                databaseReference.child(idUser).child("date_of_birth").child("year").setValue(year);
//                Toast.makeText(getApplicationContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(TrangDangKy.this, TrangDangNhap.class);
//                startActivity(intent);
            }
        });

    }

    private void registerUser(String idUser, String hoTen, String username, String matKhau, String ngaySinh, String SDT) {
        // Xử lý đăng ký người dùng
        Date convertedDate = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        try {
            // Chuyển đổi chuỗi thành đối tượng Date
            convertedDate = dateFormat.parse(ngaySinh);
        } catch (ParseException e) {
            e.printStackTrace();
            // Xử lý khi có lỗi trong quá trình chuyển đổi
        }

        Calendar dob = Calendar.getInstance();
        dob.setTime(convertedDate);

        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        if (age < 18) {
            showToast("Bạn phải đủ 18 tuổi trở lên để đăng ký");
            return;
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

        User user = new User(idUser, hoTen, username, matKhau, date, SDT, arrayList, user_notifications, arrayList);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
                databaseReference.child(idUser).setValue(user);
                databaseReference.child(idUser).child("id").setValue(idUser);
                databaseReference.child(idUser).child("name").setValue(user.getName());
                databaseReference.child(idUser).child("email").setValue(user.getEmail());
                databaseReference.child(idUser).child("phone").setValue(user.getPhone());
                databaseReference.child(idUser).child("password").setValue(user.getPassword());
                databaseReference.child(idUser).child("vouchers").setValue(arrayList);
                databaseReference.child(idUser).child("user_notifications").setValue(user_notifications);
                databaseReference.child(idUser).child("date_of_birth").child("day").setValue(day);
                databaseReference.child(idUser).child("date_of_birth").child("month").setValue(month);
                databaseReference.child(idUser).child("date_of_birth").child("year").setValue(year);
                Toast.makeText(getApplicationContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TrangDangKy.this, TrangDangNhap.class);
                startActivity(intent);

//        showToast("Đăng ký thành công");
//        Intent intent = new Intent(TrangDangKy.this, TrangDanNhap.class);
//        startActivity(intent);
    }


    private void showToast(String message) {
        Toast toast = Toast.makeText(TrangDangKy.this, message, Toast.LENGTH_SHORT);
//        toast.setGravity(Gravity.CENTER, 0, 0); // Tăng offset y
//
        toast.show();
    }

    private boolean isValidPhoneNumber(String phoneNumber) {

        return phoneNumber.matches("^0[0-9]{9,10}$");
    }



    public boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return Pattern.compile(emailPattern).matcher(email).matches();
    }


}
