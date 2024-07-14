package com.example.btlandroidnc;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.btlandroidnc.Model.User;
import com.example.btlandroidnc.Model.Voucher;
import com.example.btlandroidnc.adapters.UserAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TrangXemThongTinKH extends AppCompatActivity {
    DatabaseReference usersRef;
    List<String> Emaillist;
    List<User> userList;
    UserAdapter adapter;
    ListView listViewUsers;
    ImageButton btnCaNhan;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_xem_thong_tin_kh);
        // getSupportActionBar().hide();
        usersRef = FirebaseDatabase.getInstance().getReference("Users");
        btnCaNhan = findViewById(R.id.button5);
        btnCaNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrangXemThongTinKH.this, TrangAdmin.class);
                startActivity(intent);

            }
        });

        listViewUsers = findViewById(R.id.listViewKH);

        // Khởi tạo danh sách người dùng và adapter
        userList = new ArrayList<>();
        adapter = new UserAdapter(this, R.layout.item_kh_admin, userList);
        listViewUsers.setAdapter(adapter);
        listViewUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(TrangXemThongTinKH.this, userList.get(i).getId(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TrangXemThongTinKH.this, TrangXemThongTinMotKH.class);
                intent.putExtra("id", userList.get(i).getId());
                startActivity(intent);
            }
        });


        // Lắng nghe sự thay đổi trên Firebase Database
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Xóa dữ liệu cũ trước khi cập nhật mới
                userList.clear();

                // Duyệt qua tất cả các người dùng trong dataSnapshot
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.child("email").getValue(String.class).equals("-1"))
                        continue;

                    // Lấy thông tin của người dùng
                    String email = snapshot.child("email").getValue(String.class);
                    String sdt = snapshot.child("phone").getValue(String.class);
                    String name = snapshot.child("name").getValue(String.class);
                    String password = snapshot.child("password").getValue(String.class);

                    // Kiểm tra xem có thông tin về ngày sinh không
                    DataSnapshot dobSnapshot = snapshot.child("date_of_birth");
                    if (dobSnapshot.exists()) {
                        Long dayValue = dobSnapshot.child("day").getValue(Long.class);
                        Long monthValue = dobSnapshot.child("month").getValue(Long.class);
                        Long yearValue = dobSnapshot.child("year").getValue(Long.class);

                        if (dayValue != null && monthValue != null && yearValue != null) {
                            int day = Math.toIntExact(dayValue);
                            int month = Math.toIntExact(monthValue) - 1; // Tháng trong Calendar là 0-11
                            int year = Math.toIntExact(yearValue);

                            // Sử dụng Calendar để tạo đối tượng Date
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year, month, day);
                            Date date = calendar.getTime();

                            String id = snapshot.getKey();
                            ArrayList<Voucher> arrayList = new ArrayList<>();
                            Voucher voucher = new Voucher("1", "New User", "Chào mừng bạn mới", "image", 100f, 0f);
                            arrayList.add(voucher);

                            User user = new User(id, name, email, password, date, sdt, arrayList);
                            userList.add(user);
                        } else {
                            Log.e("TrangXemThongTinKH", "Giá trị ngày sinh không đầy đủ");
                        }
                    } else {
                        // Xử lý khi không có thông tin ngày sinh
                        String id = snapshot.getKey();
                        ArrayList<Voucher> arrayList = new ArrayList<>();
                        Voucher voucher = new Voucher("1", "New User", "Chào mừng bạn mới", "image", 100f, 0f);
                        arrayList.add(voucher);

                        User user = new User(id, name, email, password, null, sdt, arrayList); // Truyền null vào ngày sinh
                        userList.add(user);
                    }
                }

                // Cập nhật adapter khi có dữ liệu mới
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi nếu có
            }
        });

    }
}
