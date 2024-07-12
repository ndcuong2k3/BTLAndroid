//package com.example.phonestoreapplication;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.ListView;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import com.example.phonestoreapplication.Model.User_Notification;
//import com.example.phonestoreapplication.adapters.User_NotificationAdapter;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//
//public class TrangThongBao extends AppCompatActivity {
//    DatabaseReference notificationRef;
//
//    List<User_Notification> userNotifications;
//    User_NotificationAdapter adapter;
//    SharedPreferences sharedPreferences;
//    ListView listViewNotifications;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_trang_thong_bao);
//
//        SharedPreferences sharedPreferences = getSharedPreferences("com.example.sharedprerences", Context.MODE_PRIVATE);
//
//        String myId = sharedPreferences.getString("id", "-1");
//        Log.d("SharedPreferences", "myId: " + myId);
//        if (!"-1".equals(myId)) {
//            Log.e("SharedPreferences", "ID retrieved correctly from SharedPreferences.");
//        } else {
//            Log.e("SharedPreferences", "ID not retrieved correctly from SharedPreferences.");
//        }
//
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().hide();
//        }
//
//        notificationRef = FirebaseDatabase.getInstance().getReference("Notifications");
//        listViewNotifications = findViewById(R.id.thongbaocanhan);
//
//        userNotifications = new ArrayList<>();
//        adapter = new User_NotificationAdapter(this, R.layout.item_thongbao, userNotifications);
//        listViewNotifications.setAdapter(adapter);
//
//        notificationRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                try {
//                    userNotifications.clear();
//                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                        String content = snapshot.child("content").getValue(String.class);
//                        if (content != null) {
//                            Log.e("tesst", content);
//                        } else {
//                            Log.e("tesst", "Content is null");
//                        }
//                        String name = snapshot.child("name").getValue(String.class);
//                        String id = snapshot.child("id").getValue(String.class);
//                        String iduser = snapshot.child("userid").getValue(String.class);
////                        long timestamp = snapshot.child("timestamp").getValue(Long.class);
////
////                        Date date = new Date(timestamp); // Chuyển đổi timestamp thành Date
//                        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
//                        Date date = format.parse("04/11/2003");
//
//
//                            User_Notification notification = new User_Notification(id, name, content, date,iduser);
//                            userNotifications.add(0, notification); // Thêm vào đầu danh sách để hiển thị mới nhất đầu tiên
//                            listViewNotifications.setAdapter(adapter);
//                            // Thêm log để kiểm tra dữ liệu của mỗi thông báo
//                            Log.d("FirebaseData", "Notification: " + notification.getName() + " - " + notification.getContent());
//
//                    }
//                    adapter.notifyDataSetChanged();
//                } catch (Exception e) {
//                    Log.e("FirebaseError", "Exception in onDataChange: " + e.getMessage());
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                // Xử lý lỗi nếu có
//                Log.e("FirebaseError", "Error fetching notifications: " + error.getMessage());
//                // Log hoặc hiển thị thông báo cho người dùng
//            }
//        });
//
//
//
//    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        navigateToTrangChu();
//    }
//
//    private void navigateToTrangChu() {
//        Intent intent = new Intent(TrangThongBao.this, TrangChu.class);
//        startActivity(intent);
//        finish(); // Kết thúc hoạt động hiện tại để ngăn không quay lại TrangThongBao khi nhấn nút back
//    }
//}

package com.example.btlandroidnc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.btlandroidnc.Model.User_Notification;
import com.example.btlandroidnc.adapters.User_NotificationAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TrangThongBao extends AppCompatActivity {
    DatabaseReference notificationRef;

    List<User_Notification> userNotifications;
    User_NotificationAdapter adapter;
    SharedPreferences sharedPreferences;
    ListView listViewNotifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_thong_bao);

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.sharedprerences", Context.MODE_PRIVATE);

        String myId = sharedPreferences.getString("id", "-1");
        Log.d("SharedPreferences", "myId: " + myId);
        if (!"-1".equals(myId)) {
            Log.e("SharedPreferences", "ID retrieved correctly from SharedPreferences.");
        } else {
            Log.e("SharedPreferences", "ID not retrieved correctly from SharedPreferences.");
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Thay đổi tham chiếu đến bảng Users và user_notifications
        notificationRef = FirebaseDatabase.getInstance().getReference("Users").child(myId).child("user_notifications");
        listViewNotifications = findViewById(R.id.thongbaocanhan);

        userNotifications = new ArrayList<>();
        adapter = new User_NotificationAdapter(this, R.layout.item_thongbao, userNotifications);
        listViewNotifications.setAdapter(adapter);

        notificationRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    userNotifications.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String content = snapshot.child("content").getValue(String.class);
                        String name = snapshot.child("name").getValue(String.class);
                        String id = snapshot.child("id").getValue(String.class);
                        // Bạn có thể thêm userid vào model nếu cần thiết
                        // String iduser = snapshot.child("userid").getValue(String.class);

//                        Long timestamp = snapshot.child("date").getValue(Long.class);
//                        Date date = new Date(timestamp);
//                        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
//                        Date date = format.parse("04/11/2003");
                        DataSnapshot dateSnapshot = snapshot.child("date");
                        int year = dateSnapshot.child("year").getValue(Integer.class);
                        int month = dateSnapshot.child("month").getValue(Integer.class);
                        int day = dateSnapshot.child("date").getValue(Integer.class);

                        int hours = dateSnapshot.child("hours").getValue(Integer.class);
                        int minutes = dateSnapshot.child("minutes").getValue(Integer.class);
                        int seconds = dateSnapshot.child("seconds").getValue(Integer.class);

                        // Tạo đối tượng Date từ các trường trên
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, day, hours, minutes, seconds);

                        Date date = calendar.getTime();
                        Log.d("date", String.valueOf(date));
                        User_Notification notification = new User_Notification(id, name, content, date);
                        userNotifications.add(0, notification); // Thêm vào đầu danh sách để hiển thị mới nhất đầu tiên
                        listViewNotifications.setAdapter(adapter);
                        // Thêm log để kiểm tra dữ liệu của mỗi thông báo
                        Log.d("FirebaseData", "Notification: " + notification.getName() + " - " + notification.getContent());
                    }
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    Log.e("FirebaseError", "Exception in onDataChange: " + e.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý lỗi nếu có
                Log.e("FirebaseError", "Error fetching notifications: " + error.getMessage());
                // Log hoặc hiển thị thông báo cho người dùng
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        navigateToTrangChu();
    }

    private void navigateToTrangChu() {
        Intent intent = new Intent(TrangThongBao.this, TrangChu.class);
        startActivity(intent);
        finish(); // Kết thúc hoạt động hiện tại để ngăn không quay lại TrangThongBao khi nhấn nút back
    }
}
