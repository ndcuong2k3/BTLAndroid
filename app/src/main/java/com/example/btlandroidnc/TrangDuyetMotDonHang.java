////package com.example.btl;
////
////import android.annotation.SuppressLint;
////import android.content.Context;
////import android.content.Intent;
////import android.content.SharedPreferences;
////import android.os.Bundle;
////import android.util.Log;
////import android.view.View;
////import android.widget.Button;
////import android.widget.ListView;
////import android.widget.TextView;
////
////import androidx.activity.EdgeToEdge;
////import androidx.annotation.NonNull;
////import androidx.appcompat.app.AppCompatActivity;
////import androidx.core.graphics.Insets;
////import androidx.core.view.ViewCompat;
////import androidx.core.view.WindowInsetsCompat;
////
////import com.example.btl.Model.Product;
////import com.example.btl.Model.User_Notification;
////import com.example.btl.adapters.ProductCheckoutAdapter;
////import com.example.btl.common.ProductCartOrCheckout;
////import com.google.firebase.database.DataSnapshot;
////import com.google.firebase.database.DatabaseError;
////import com.google.firebase.database.DatabaseReference;
////import com.google.firebase.database.FirebaseDatabase;
////import com.google.firebase.database.ValueEventListener;
////
////import java.util.ArrayList;
////import java.util.Calendar;
////import java.util.Date;
////import java.util.List;
////
////public class TrangDuyetMotDonHang extends AppCompatActivity {
////    DatabaseReference usersRef;
////    DatabaseReference usersRefOrder;
////    TextView txtHoTen ,txtSDT,txtNgayDat,txtDiaChi;
////    ListView listView;
////    SharedPreferences sharedPreferences;
////    ArrayList <String> product_id_list;
////    //  List<Order> orderList;
////    List<ProductCartOrCheckout> orderList;
////    // OrderAdapter orderAdapter;
////    TextView txtTongTien,txtGiamGia,txtThanhTien;
////    ProductCheckoutAdapter orderAdapter;
////    Button btDuyet,btXoa;
////    int ix=0;
////    @SuppressLint("MissingInflatedId")
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        EdgeToEdge.enable(this);
////        setContentView(R.layout.activity_trang_duyet_mot_don_hang);
////        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
////            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
////            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
////            return insets;
////        });
////        sharedPreferences = getSharedPreferences("com.example.sharedprerences", Context.MODE_PRIVATE);
////        txtThanhTien =findViewById(R.id.ThanhTien);
////        txtTongTien = findViewById(R.id.TongTien);
////        txtGiamGia = findViewById(R.id.GiamGia);
////        btDuyet = findViewById(R.id.btDuyetDonHang);
////        btXoa=findViewById(R.id.btXoaDonHang);
////        txtHoTen =findViewById(R.id.textViewSuaTen);
////        txtSDT=findViewById(R.id.textViewSDT);
////        txtDiaChi=findViewById(R.id.textViewDiaChiMĐ);
////        txtNgayDat=findViewById(R.id.textViewNgayĐH);
////        Intent i =getIntent();
////        String id = i.getStringExtra("id");
////        usersRef = FirebaseDatabase.getInstance().getReference("Invoices").child(id);
////        listView = findViewById(R.id.list);
////        product_id_list=new ArrayList<>();
////        orderList =new ArrayList<>();
////        orderAdapter =new ProductCheckoutAdapter(TrangDuyetMotDonHang.this,orderList);
////        listView.setAdapter(orderAdapter);
////
////
////        usersRef.addValueEventListener(new ValueEventListener() {
////            @Override
////            public void onDataChange(@NonNull DataSnapshot snapshot) {
////                txtTongTien.setText(String.valueOf(snapshot.child("total").getValue(Long.class)+snapshot.child("discount").getValue(Long.class))+"đ");
////                txtThanhTien.setText(String.valueOf(snapshot.child("total").getValue(Long.class))+"đ");
////                txtGiamGia.setText(String.valueOf(snapshot.child("discount").getValue(Long.class))+"đ");
////                String user_id = snapshot.child("user_id").getValue(String.class);
////                DataSnapshot dateOfBirthSnapshot = snapshot.child("create_at");
////
////                Calendar calendar = Calendar.getInstance();
////                int year = calendar.get(Calendar.YEAR);
////                int month = calendar.get(Calendar.MONTH) + 1; // Calendar.MONTH bắt đầu từ 0, nên cộng thêm 1
////                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
////                txtNgayDat.setText(dayOfMonth + "/" + month + "/" + year);
////                txtDiaChi.setText(snapshot.child("address").getValue(String.class));
////                DataSnapshot dataSnapshot = snapshot.child("orders");
////                for (DataSnapshot order_child : dataSnapshot.getChildren()){
////
////                    String  product_id =order_child.child("product_id").getValue(String.class);
////
////                    //    product_id_list.add(product_id);
////
////
////
////
////
//////                   float price =order_child.child("price").getValue(long.class);
//////                  float price_sale =order_child.child("price_sale").getValue(long.class);
////
////                    //   String  product_id =order_child.child("product_id").getValue(String.class);
////                    int quantity =order_child.child("quantity").getValue(Integer.class);
////                    DatabaseReference usersRef3 = FirebaseDatabase.getInstance().getReference("Products").child(product_id);
////                    usersRef3.addValueEventListener(new ValueEventListener() {
////                        @Override
////                        public void onDataChange(@NonNull DataSnapshot snapshot) {
////                            if(snapshot.exists()) {
////                                Product product = snapshot.getValue(Product.class);
////                                ProductCartOrCheckout productCartOrCheckout = new ProductCartOrCheckout(product, quantity);
////                                orderList.add(productCartOrCheckout);
////                            }
////                            orderAdapter.notifyDataSetChanged();
////
////                        }
////
////                        @Override
////                        public void onCancelled(@NonNull DatabaseError error) {
////
////                        }
////                    });
////
////
////                    //           Order o =new Order(product_id,quantity,price,price_sale);
//////                                  Order o =new Order();
//////                                  o = order_child.getValue(Order.class);
//////                   orderList.add(o);
////
////                }
////
//////                              for(String x :product_id_list){
//////
//////                                  DatabaseReference  usersRef3 = FirebaseDatabase.getInstance().getReference("Products").child(x);
//////                                  usersRef3.addValueEventListener(new ValueEventListener() {
//////                                      @Override
//////                                      public void onDataChange(@NonNull DataSnapshot snapshot) {
//////                                          if(snapshot.exists())
//////                                          {
//////                                              ix++;
//////
//////                                              Product p =snapshot.getValue(Product.class);
//////                                            //  Toast.makeText(TrangDuyetMotDonHang.this, p.getName().toString(), Toast.LENGTH_SHORT).show();
//////                                              ProductCartOrCheckout productCartOrCheckout =new ProductCartOrCheckout(p,100);
//////                                              orderList.add(productCartOrCheckout);
//////
//////                                          }
//////                                          else  Toast.makeText(TrangDuyetMotDonHang.this, "sdfg: "+String.valueOf(ix), Toast.LENGTH_SHORT).show();
//////                                          orderAdapter.notifyDataSetChanged();
//////                                      }
//////
//////                                      @Override
//////                                      public void onCancelled(@NonNull DatabaseError error) {
//////
//////                                      }
//////                                  });
//////
//////
//////
//////
//////                              }
////
////
////
////                try {
////                    DatabaseReference usersRef2 = FirebaseDatabase.getInstance().getReference("Users").child(user_id);
////                    usersRef2.addValueEventListener(new ValueEventListener() {
////                        @Override
////                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                            txtHoTen.setText(dataSnapshot.child("name").getValue(String.class));
////                            txtSDT.setText(dataSnapshot.child("phone").getValue(String.class));
////
////                        }
////
////                        @Override
////                        public void onCancelled(@NonNull DatabaseError error) {
////
////                        }
////                    });}
////                catch (NullPointerException e) {
////                    // Xử lý khi product_id là null
////                    Log.e("TrangDuyetMotDonHang", "product_id is null: " + e.getMessage());
////                }
////                //  orderAdapter.notifyDataSetChanged();
////
////            }
////
////            @Override
////            public void onCancelled(@NonNull DatabaseError error) {
////
////            }
////
////        });
////
//////       usersRefOrder = FirebaseDatabase.getInstance().getReference("Invoices").child(id).child("orders");
//////       usersRefOrder.addValueEventListener(new ValueEventListener() {
//////           @Override
//////           public void onDataChange(@NonNull DataSnapshot snapshot) {
//////               for (DataSnapshot order_child : snapshot.getChildren()){
//////                   float price =order_child.child("price").getValue(float.class);
//////                  float price_sale =order_child.child("price_sale").getValue(Float.class);
//////                   String product_id =order_child.child("product_id").getValue(String.class);
//////                   int quantity =Integer.parseInt( order_child.child("quantity").getValue(String.class));
//////                   Order o =new Order(product_id,quantity,price,price_sale);
//////                   orderList.add(o);
//////                   Toast.makeText(getApplicationContext(), "sô lương: "+String.valueOf(ix), Toast.LENGTH_SHORT).show();
//////               }
//////           }
//////
//////           @Override
//////           public void onCancelled(@NonNull DatabaseError error) {
//////
//////           }
//////       });
////        btDuyet.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                usersRef.child("is_validate").setValue(true);
////                getMaxID(new MaxNotificationIdCallBack() {
////                    @Override
////                    public void onMaxNotificationIdReceived(long maxNotificationId) {
////
////                        String myId = sharedPreferences.getString("id", "-1");
////                        DatabaseReference drnoti = FirebaseDatabase.getInstance().getReference("Users").child(myId).child("user_notifications");
////
////                        String notificationId = String.valueOf(maxNotificationId + 1);
////                        String name = "Chờ phê duyệt";
////                        String content = "Đơn hàng có mã " + id + " đã được phê duyệt.";
////                        Date date = Calendar.getInstance().getTime();
////                        User_Notification userNotification = new User_Notification(notificationId, name, content, date);
////                        drnoti.child(notificationId).setValue(userNotification);
////                    }
////                });
////                Intent intent =new Intent(TrangDuyetMotDonHang.this, TrangDuyetDonHang.class);
////                startActivity(intent);
////            }
////        });
////        btXoa.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                Intent intent =new Intent(TrangDuyetMotDonHang.this, TrangDuyetDonHang.class);
////                intent.putExtra("delete",true);
////                intent.putExtra("id_invoice",id);
////                startActivity(intent);
////                finish();
////                //usersRef.removeValue();
////
////
////
////
////            }
////        });
////
////    }
////
////    public void getMaxID(MaxNotificationIdCallBack callback) {
////        String myId = sharedPreferences.getString("id", "-1");
////        DatabaseReference drnoti = FirebaseDatabase.getInstance().getReference("Users").child(myId).child("user_notifications");
////
////        // Lấy danh sách các notificationId hiện có và tìm giá trị lớn nhất
////        drnoti.addListenerForSingleValueEvent(new ValueEventListener() {
////            @Override
////            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                long maxNotificationId = 0;
////
////                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
////                    String notificationId = snapshot.getKey();
////                    if (notificationId != null) {
////                        long id = Long.parseLong(notificationId);
////                        if (id > maxNotificationId) {
////                            maxNotificationId = id;
////                        }
////                    }
////                }
////
////                // Gọi callback để trả về giá trị maxNotificationId
////                callback.onMaxNotificationIdReceived(maxNotificationId);
////            }
////
////            @Override
////            public void onCancelled(@NonNull DatabaseError error) {
////                // Xử lý khi truy vấn bị hủy
////            }
////        });
////    }
////
////}
//package com.example.btlandroidnc;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import androidx.activity.EdgeToEdge;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import com.example.btlandroidnc.Model.Product;
//import com.example.btlandroidnc.Model.User_Notification;
//import com.example.btlandroidnc.adapters.ProductCheckoutAdapter;
//import com.example.btlandroidnc.common.ProductCartOrCheckout;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//
//public class TrangDuyetMotDonHang extends AppCompatActivity {
//    DatabaseReference usersRef;
//    DatabaseReference usersRefOrder;
//    TextView txtHoTen ,txtSDT,txtNgayDat,txtDiaChi;
//    ListView listView;
//    SharedPreferences sharedPreferences;
//    ArrayList <String> product_id_list;
//    List<ProductCartOrCheckout> orderList;
//    TextView txtTongTien,txtGiamGia,txtThanhTien;
//    ProductCheckoutAdapter orderAdapter;
//    Button btDuyet,btXoa;
//    int ix=0;
//
//    @SuppressLint("MissingInflatedId")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_trang_duyet_mot_don_hang);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//        sharedPreferences = getSharedPreferences("com.example.sharedprerences", Context.MODE_PRIVATE);
//        txtThanhTien =findViewById(R.id.ThanhTien);
//        txtTongTien = findViewById(R.id.TongTien);
//        txtGiamGia = findViewById(R.id.GiamGia);
//        btDuyet = findViewById(R.id.btDuyetDonHang);
//        btXoa=findViewById(R.id.btXoaDonHang);
//        txtHoTen =findViewById(R.id.textViewSuaTen);
//        txtSDT=findViewById(R.id.textViewSDT);
//        txtDiaChi=findViewById(R.id.textViewDiaChiMĐ);
//        txtNgayDat=findViewById(R.id.textViewNgayĐH);
//        Intent i =getIntent();
//        String id = i.getStringExtra("id");
//        usersRef = FirebaseDatabase.getInstance().getReference("Invoices").child(id);
//        listView = findViewById(R.id.list);
//        product_id_list=new ArrayList<>();
//        orderList =new ArrayList<>();
//        orderAdapter =new ProductCheckoutAdapter(TrangDuyetMotDonHang.this,orderList);
//        listView.setAdapter(orderAdapter);
//
//        usersRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                txtTongTien.setText(String.valueOf(snapshot.child("total").getValue(Long.class) + snapshot.child("discount").getValue(Long.class)) + "đ");
//                txtThanhTien.setText(String.valueOf(snapshot.child("total").getValue(Long.class)) + "đ");
//                txtGiamGia.setText(String.valueOf(snapshot.child("discount").getValue(Long.class)) + "đ");
//                String user_id = snapshot.child("user_id").getValue(String.class);
//
//                Calendar calendar = Calendar.getInstance();
//                int year = calendar.get(Calendar.YEAR);
//                int month = calendar.get(Calendar.MONTH) + 1; // Calendar.MONTH bắt đầu từ 0, nên cộng thêm 1
//                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
//                txtNgayDat.setText(dayOfMonth + "/" + month + "/" + year);
//                txtDiaChi.setText(snapshot.child("address").getValue(String.class));
//                DataSnapshot dataSnapshot = snapshot.child("orders");
//                for (DataSnapshot order_child : dataSnapshot.getChildren()) {
//                    String product_id = order_child.child("product_id").getValue(String.class);
//                    int quantity = order_child.child("quantity").getValue(Integer.class);
//                    DatabaseReference usersRef3 = FirebaseDatabase.getInstance().getReference("Products").child(product_id);
//                    usersRef3.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            if (snapshot.exists()) {
//                                Product product = snapshot.getValue(Product.class);
//                                ProductCartOrCheckout productCartOrCheckout = new ProductCartOrCheckout(product, quantity);
//                                orderList.add(productCartOrCheckout);
//                            }
//                            orderAdapter.notifyDataSetChanged();
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//                }
//
//                try {
//                    DatabaseReference usersRef2 = FirebaseDatabase.getInstance().getReference("Users").child(user_id);
//                    usersRef2.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            txtHoTen.setText(dataSnapshot.child("name").getValue(String.class));
//                            txtSDT.setText(dataSnapshot.child("phone").getValue(String.class));
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//                } catch (NullPointerException e) {
//                    Log.e("TrangDuyetMotDonHang", "product_id is null: " + e.getMessage());
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        btDuyet.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                usersRef.child("is_validate").setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Log.d("TrangDuyetMotDonHang", "Hóa đơn đã được duyệt");
//
//                            // Sau khi hóa đơn được duyệt, lấy maxNotificationId và tạo thông báo
//                            getMaxID(new MaxNotificationIdCallBack() {
//                                @Override
//                                public void onMaxNotificationIdReceived(long maxNotificationId) {
//                                    Log.d("t", String.valueOf(maxNotificationId));
//                                    // Lấy user_id từ hóa đơn để gửi thông báo đến người dùng
//                                    usersRef.child("user_id").addListenerForSingleValueEvent(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                            String userId = snapshot.getValue(String.class);
//                                            if (userId != null) {
//                                                DatabaseReference drnoti = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("user_notifications");
//
//                                                String notificationId = String.valueOf(maxNotificationId + 1);
//                                                Log.d("notif", notificationId);
//                                                String name = "Đơn hàng đã được phê duyệt";
//                                                String content = "Đơn hàng có mã " + id + " đã được phê duyệt.";
//                                                Date date = Calendar.getInstance().getTime();
//                                                Log.d("Date", String.valueOf(date));
//                                                User_Notification userNotification = new User_Notification(notificationId, name, content, date);
//                                                drnoti.child(notificationId).setValue(userNotification).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                    @Override
//                                                    public void onComplete(@NonNull Task<Void> task) {
//                                                        if (task.isSuccessful()) {
//                                                            Log.d("TrangDuyetMotDonHang", "Thông báo đã được gửi thành công");
//                                                        } else {
//                                                            Log.e("TrangDuyetMotDonHang", "Lỗi khi gửi thông báo: " + task.getException().getMessage());
//                                                        }
//                                                    }
//                                                });
//                                            }
//                                        }
//
//                                        @Override
//                                        public void onCancelled(@NonNull DatabaseError error) {
//                                            Log.e("TrangDuyetMotDonHang", "Lỗi khi lấy user_id: " + error.getMessage());
//                                        }
//                                    });
//                                }
//                            });
//                        } else {
//                            Log.e("TrangDuyetMotDonHang", "Lỗi khi đánh dấu hóa đơn: " + task.getException().getMessage());
//                        }
//                    }
//                });
//
//                Intent intent = new Intent(TrangDuyetMotDonHang.this, TrangDuyetDonHang.class);
//                startActivity(intent);
//            }
//        });
//
//        btXoa.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(TrangDuyetMotDonHang.this, TrangDuyetDonHang.class);
//                intent.putExtra("delete", true);
//                intent.putExtra("id_invoice", id);
//                startActivity(intent);
//                finish();
//            }
//        });
//    }
//
////    public void getMaxID(MaxNotificationIdCallBack callback) {
////        String myId = sharedPreferences.getString("id", "-1");
////        DatabaseReference drnoti = FirebaseDatabase.getInstance().getReference("Users").child(myId).child("user_notifications");
////
////        drnoti.addListenerForSingleValueEvent(new ValueEventListener() {
////            @Override
////            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                long maxNotificationId = 0;
////
////                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
////                    String notificationId = snapshot.getKey();
////                    if (notificationId != null) {
////                        try {
////                            long id = Long.parseLong(notificationId);
////                            if (id > maxNotificationId) {
////                                maxNotificationId = id;
////                            }
////                        } catch (NumberFormatException e) {
////                            Log.e("TrangDuyetMotDonHang", "Không thể chuyển đổi notificationId: " + notificationId);
////                        }
////                    }
////                }
////
////                callback.onMaxNotificationIdReceived(maxNotificationId);
////            }
////
////            @Override
////            public void onCancelled(@NonNull DatabaseError error) {
////                Log.e("TrangDuyetMotDonHang", "Lỗi khi truy vấn: " + error.getMessage());
////            }
////        });
//
//    public void getMaxID(String userId, MaxNotificationIdCallBack callback) {
//        DatabaseReference drnoti = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("user_notifications");
//
//        drnoti.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                long maxNotificationId = 0;
//
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    String notificationId = snapshot.getKey();
//                    if (notificationId != null) {
//                        try {
//                            long id = Long.parseLong(notificationId);
//                            if (id > maxNotificationId) {
//                                maxNotificationId = id;
//                            }
//                        } catch (NumberFormatException e) {
//                            Log.e("TrangDuyetMotDonHang", "Không thể chuyển đổi notificationId: " + notificationId);
//                        }
//                    }
//                }
//
//                callback.onMaxNotificationIdReceived(maxNotificationId);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.e("TrangDuyetMotDonHang", "Lỗi khi truy vấn: " + error.getMessage());
//            }
//        });
//    }
//
//}
//
//    public interface MaxNotificationIdCallBack {
//        void onMaxNotificationIdReceived(long maxNotificationId);
//    }
//}
//
package com.example.btlandroidnc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.btlandroidnc.Model.Product;
import com.example.btlandroidnc.Model.User_Notification;
import com.example.btlandroidnc.adapters.ProductCheckoutAdapter;
import com.example.btlandroidnc.common.ProductCartOrCheckout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TrangDuyetMotDonHang extends AppCompatActivity {
    DatabaseReference usersRef;
    DatabaseReference usersRefOrder;
    TextView txtHoTen, txtSDT, txtNgayDat, txtDiaChi;
    ListView listView;
    SharedPreferences sharedPreferences;
    ArrayList<String> product_id_list;
    List<ProductCartOrCheckout> orderList;
    TextView txtTongTien, txtGiamGia, txtThanhTien;
    ProductCheckoutAdapter orderAdapter;
    Button btDuyet, btXoa;
    ImageButton btnCaNhan;
    int ix = 0;
    String user_id;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_trang_duyet_mot_don_hang);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        sharedPreferences = getSharedPreferences("com.example.sharedprerences", Context.MODE_PRIVATE);
        txtThanhTien = findViewById(R.id.ThanhTien);
        btnCaNhan = findViewById(R.id.button5);
        txtTongTien = findViewById(R.id.TongTien);
        txtGiamGia = findViewById(R.id.GiamGia);
        btDuyet = findViewById(R.id.btDuyetDonHang);
        btXoa = findViewById(R.id.btXoaDonHang);
        txtHoTen = findViewById(R.id.textViewSuaTen);
        txtSDT = findViewById(R.id.textViewSDT);
        txtDiaChi = findViewById(R.id.textViewDiaChiMĐ);
        txtNgayDat = findViewById(R.id.textViewNgayĐH);
        Intent i = getIntent();
        String id = i.getStringExtra("id");
        usersRef = FirebaseDatabase.getInstance().getReference("Invoices").child(id);
        listView = findViewById(R.id.list);
        product_id_list = new ArrayList<>();
        orderList = new ArrayList<>();
        orderAdapter = new ProductCheckoutAdapter(TrangDuyetMotDonHang.this, orderList);
        listView.setAdapter(orderAdapter);

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Long total = snapshot.child("total").getValue(Long.class);
                Long discount = snapshot.child("discount").getValue(Long.class);

                if (total == null) {
                    total = 0L; // Giá trị mặc định nếu total là null
                }
                if (discount == null) {
                    discount = 0L; // Giá trị mặc định nếu discount là null
                }

                txtTongTien.setText(String.valueOf(total + discount) + "đ");
                txtThanhTien.setText(String.valueOf(total) + "đ");
                txtGiamGia.setText(String.valueOf(discount) + "đ");
                user_id = snapshot.child("user_id").getValue(String.class); // Gán giá trị cho user_id ở đây

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1; // Calendar.MONTH bắt đầu từ 0, nên cộng thêm 1
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                txtNgayDat.setText(dayOfMonth + "/" + month + "/" + year);
                txtDiaChi.setText(snapshot.child("address").getValue(String.class));
                DataSnapshot dataSnapshot = snapshot.child("orders");
                for (DataSnapshot order_child : dataSnapshot.getChildren()) {
                    String product_id = order_child.child("product_id").getValue(String.class);
                    int quantity = order_child.child("quantity").getValue(Integer.class);
                    DatabaseReference usersRef3 = FirebaseDatabase.getInstance().getReference("Products").child(product_id);
                    usersRef3.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                Product product = snapshot.getValue(Product.class);
                                ProductCartOrCheckout productCartOrCheckout = new ProductCartOrCheckout(product, quantity);
                                orderList.add(productCartOrCheckout);
                                orderAdapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e("TrangDuyetMotDonHang", "Lỗi khi truy vấn sản phẩm: " + error.getMessage());
                        }
                    });
                }

                try {
                    DatabaseReference usersRef2 = FirebaseDatabase.getInstance().getReference("Users").child(user_id);
                    usersRef2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            txtHoTen.setText(dataSnapshot.child("name").getValue(String.class));
                            txtSDT.setText(dataSnapshot.child("phone").getValue(String.class));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e("TrangDuyetMotDonHang", "Lỗi khi truy vấn người dùng: " + error.getMessage());
                        }
                    });
                } catch (NullPointerException e) {
                    Log.e("TrangDuyetMotDonHang", "Lỗi khi lấy user_id: " + e.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TrangDuyetMotDonHang", "Lỗi khi đọc dữ liệu hóa đơn: " + error.getMessage());
            }

        });

        btnCaNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrangDuyetMotDonHang.this,TrangAdmin.class);
                startActivity(intent);
            }
        });

        btDuyet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usersRef.child("is_validate").setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("TrangDuyetMotDonHang", "Hóa đơn đã được duyệt");

                            // Sau khi hóa đơn được duyệt, lấy maxNotificationId và tạo thông báo
                            getMaxID(user_id, new MaxNotificationIdCallBack() {
                                @Override
                                public void onMaxNotificationIdReceived(long maxNotificationId) {
                                    Log.d("TrangDuyetMotDonHang", "Max Notification ID: " + maxNotificationId);
                                    // Tạo thông báo
                                    DatabaseReference drnoti = FirebaseDatabase.getInstance().getReference("Users").child(user_id).child("user_notifications");
                                    String notificationId = String.valueOf(maxNotificationId + 1);
                                    String name = "Đơn hàng đã được phê duyệt";
                                    String content = "Đơn hàng có mã " + id + " đã được phê duyệt.";
                                    Date date = Calendar.getInstance().getTime();
                                    User_Notification userNotification = new User_Notification(notificationId, name, content, date);
                                    drnoti.child(notificationId).setValue(userNotification).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d("TrangDuyetMotDonHang", "Thông báo đã được gửi thành công");
                                            } else {
                                                Log.e("TrangDuyetMotDonHang", "Lỗi khi gửi thông báo: " + task.getException().getMessage());
                                            }
                                        }
                                    });
                                }
                            });
                        } else {
                            Log.e("TrangDuyetMotDonHang", "Lỗi khi đánh dấu hóa đơn: " + task.getException().getMessage());
                        }
                    }
                });

                Intent intent = new Intent(TrangDuyetMotDonHang.this, TrangDuyetDonHang.class);
                startActivity(intent);
            }
        });

        btXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrangDuyetMotDonHang.this, TrangDuyetDonHang.class);
                intent.putExtra("delete", true);
                intent.putExtra("id_invoice", id);
                startActivity(intent);
                finish();
            }
        });
    }

    public void getMaxID(String userId, MaxNotificationIdCallBack callback) {
        DatabaseReference drnoti = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("user_notifications");

        drnoti.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long maxNotificationId = 0;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String notificationId = snapshot.getKey();
                    if (notificationId != null) {
                        try {
                            long id = Long.parseLong(notificationId);
                            if (id > maxNotificationId) {
                                maxNotificationId = id;
                            }
                        } catch (NumberFormatException e) {
                            Log.e("TrangDuyetMotDonHang", "Không thể chuyển đổi notification ID sang số nguyên: " + e.getMessage());
                        }
                    }
                }

                callback.onMaxNotificationIdReceived(maxNotificationId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("TrangDuyetMotDonHang", "Lỗi khi đọc dữ liệu thông báo: " + databaseError.getMessage());
            }
        });
    }

//    private interface MaxNotificationIdCallBack {
//        void onMaxNotificationIdReceived(long maxNotificationId);
//    }
}
