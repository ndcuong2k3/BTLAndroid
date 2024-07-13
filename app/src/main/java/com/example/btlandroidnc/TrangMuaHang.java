//package com.example.btlandroidnc;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.activity.EdgeToEdge;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import com.example.btlandroidnc.Model.Invoice;
//import com.example.btlandroidnc.Model.Order;
//import com.example.btlandroidnc.Model.Product;
//import com.example.btlandroidnc.Model.User;
//import com.example.btlandroidnc.Model.User_Notification;
//import com.example.btlandroidnc.Model.Voucher;
//import com.example.btlandroidnc.Reference.Reference;
//import com.example.btlandroidnc.adapters.ProductCheckoutAdapter;
//import com.example.btlandroidnc.adapters.VoucherCheckoutAdapter;
//import com.example.btlandroidnc.common.ProductCartOrCheckout;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.text.NumberFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Currency;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Locale;
//
//public class TrangMuaHang extends AppCompatActivity {
//    final Reference reference = new Reference();
//
//    final DatabaseReference products_ref = reference.getProducts();
//
//    final DatabaseReference invoices_ref = reference.getInvoices();
//
//    final DatabaseReference users_ref = reference.getUsers();
//
//    ArrayList<ProductCartOrCheckout> _products_checkout = new ArrayList<>();
//
//    double total_price = 0;
//
//    ListView list_view_products, list_view_vouchers;
//
//    ProductCheckoutAdapter adapter;
//
//    TextView text_view_sub_total_price, text_view_total_price;
//
//    EditText edit_text_address;
//
//    Button button_order;
//
//    ImageButton image_button_home, image_button_cart;
//
//    ImageButton bt_KhuyenMai, bt_ThongBao, bt_GioHang, bt_TTCaNhan, btTrangChu;
//
//    HashMap<String, Product> products = new HashMap<>();
//
//    boolean is_from_cart;
//
//    SharedPreferences sharedPreferences;
//    ArrayList<Voucher> vouchers = new ArrayList<Voucher>();
//
//    VoucherCheckoutAdapter adapter_voucher;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_trang_mua_hang);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//        sharedPreferences = getSharedPreferences("com.example.sharedprerences", Context.MODE_PRIVATE);
//        mapping_client();
//
//        Intent intent = getIntent();
//
//        HashMap<String, Integer> _h_products = (HashMap<String, Integer>) intent.getSerializableExtra("products");
//
//        is_from_cart = intent.getBooleanExtra("is_from_cart", false);
//
//        assert _h_products != null;
//        for (String key : _h_products.keySet()) {
//            int _quantity = _h_products.get(key);
//
//            products_ref.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    Log.d("A", "A");
//                    Product product = snapshot.getValue(Product.class);
//
//                    total_price += product.getPrice() * _quantity;
//
//                    products.put(key, product);
//
//                    _products_checkout.add(new ProductCartOrCheckout(product, _quantity));
//
//                    adapter.notifyDataSetChanged();
//
//                    NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
//                    format.setCurrency(Currency.getInstance("VND"));
//                    text_view_sub_total_price.setText(format.format(total_price));
////                    text_view_sub_total_price.setText(Double.toString(total_price) + " đ");
//                    text_view_total_price.setText(format.format(total_price));
////                    text_view_total_price.setText(Double.toString(total_price) + " đ");
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//        }
//        //
//        if (_products_checkout != null) {
//
//            adapter = new ProductCheckoutAdapter(TrangMuaHang.this, _products_checkout);
//
//            list_view_products.setAdapter(adapter);
//
//        }
//
//        button_order.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                handle_checkout();
//            }
//        });
//
//        bt_TTCaNhan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(TrangMuaHang.this, TrangCaNhan.class);
//                startActivity(i);
//            }
//        });
//
//        bt_ThongBao.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(TrangMuaHang.this,TrangThongBao.class);
//                startActivity(i);
//            }
//        });
//
//
//        bt_KhuyenMai.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(TrangMuaHang.this, TrangKhuyenMai.class);
//                startActivity(i);
//            }
//        });
//
//// Xử lý khi người dùng click vào nút Giỏ hàng
//        bt_GioHang.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(TrangMuaHang.this, TrangGioHang.class);
//                startActivity(intent);
//            }
//        });
//
//        btTrangChu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(TrangMuaHang.this, TrangChu.class);
//                startActivity(intent);
//            }
//        });
//
//
//
//        adapter_voucher = new VoucherCheckoutAdapter(TrangMuaHang.this, vouchers, new HandleCheck());
//
//        SharedPreferences sharedpreferences = getSharedPreferences("com.example.sharedprerences", Context.MODE_PRIVATE);
//
//        String user_id = sharedpreferences.getString("id", "");
//
//        users_ref.child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                User user = snapshot.getValue(User.class);
//                for (Voucher _v : user.getVouchers()) {
//                    if (total_price > _v.getCondition()) {
//                        vouchers.add(_v);
//                    }
//                }
//                adapter_voucher = new VoucherCheckoutAdapter(TrangMuaHang.this, vouchers, new HandleCheck());
//
//                list_view_vouchers.setAdapter(adapter_voucher);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
//
//
//    private void mapping_client() {
//        this.list_view_products = findViewById(R.id.list_view_products);
//        this.text_view_sub_total_price = findViewById(R.id.TongTien);
//        this.text_view_total_price = findViewById(R.id.ThanhTien);
//        this.edit_text_address = findViewById(R.id.address);
//        this.button_order = findViewById(R.id.order);
//
//        this.list_view_vouchers = findViewById(R.id.vouchers);
//        bt_ThongBao = findViewById(R.id.button3);
//        bt_TTCaNhan = findViewById(R.id.button5);
//        bt_KhuyenMai = findViewById(R.id.button2);
//        bt_GioHang = findViewById(R.id.button4);
//        btTrangChu = findViewById(R.id.button1);
//
//    }
//
//    private void handle_checkout() {
//        String address = edit_text_address.getText().toString();
//
//        if (address.isEmpty()) {
//            Toast toast = Toast.makeText(TrangMuaHang.this, "Vui lòng nhập địa chỉ", Toast.LENGTH_SHORT);
//            toast.show();
//        } else {
//            //Tạo mới hóa đơn, tạo key trên firebase
//            String new_invoice_id = invoices_ref.push().getKey();
//            //Order: list sp
//            ArrayList<Order> orders = new ArrayList<Order>();
//
//            float total_price = 0;
//
//            SharedPreferences sharedpreferences = getSharedPreferences("com.example.sharedprerences", Context.MODE_PRIVATE);
//
//            String user_id = sharedpreferences.getString("id", "");
//
//            for (ProductCartOrCheckout _p : _products_checkout) {
//
//                Order order = new Order(_p.product.getId(), _p.quantity, _p.quantity * _p.product.getPrice(), 0);
//
//                total_price += _p.quantity * _p.product.getPrice();
//
//                orders.add(order);
//            }
//            //giảm giá cho hóa đơn
//            float discount = (adapter_voucher != null && adapter_voucher.position_selected != null)
//                    ? vouchers.get(adapter_voucher.position_selected).getValue()
//                    : 0;
//
//            Invoice new_invoice = new Invoice(new_invoice_id, user_id, address, total_price, discount, orders);
//            //xóa sau khi mua xong
//            users_ref.child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    User user = snapshot.getValue(User.class);
//
//                    if (adapter_voucher.position_selected != null) {
//                        vouchers.remove(vouchers.get(Integer.valueOf(adapter_voucher.position_selected)));
//                    }
//                    Log.d("leng", "onDataChange: " + vouchers.size());
//
//
//                    users_ref.child(user.getId()).child("vouchers").setValue(vouchers);
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//            //thêm invoice vào firebase
//            invoices_ref.child(new_invoice_id).setValue(new_invoice);
//            //xóa cart sau khi mua xong
//            if (is_from_cart) {
//                users_ref.child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        User user = snapshot.getValue(User.class);
//
//                        for (ProductCartOrCheckout _p : _products_checkout) {
//                            user.delete_product(_p.product.getId());
//                        }
//
//                        users_ref.child(user.getId()).child("cart").setValue(user.getCart());
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//            }
//
//            // Gọi hàm để lấy maxNotificationId và xử lý kết quả
//            getMaxID(new MaxNotificationIdCallBack() {
//                @Override
//                public void onMaxNotificationIdReceived(long maxNotificationId) {
//
//                    String myId = sharedPreferences.getString("id", "-1");
//                    DatabaseReference drnoti = FirebaseDatabase.getInstance().getReference("Users").child(myId).child("user_notifications");
//
//                    String notificationId = String.valueOf(maxNotificationId + 1);
//                    String name = "Chờ phê duyệt";
//                    String content = "Đơn hàng có mã " + new_invoice_id + " đã được đặt thành công và đang chờ phê duyệt.";
//                    Date date = Calendar.getInstance().getTime();
//                    User_Notification userNotification = new User_Notification(notificationId, name, content, date);
//                    drnoti.child(notificationId).setValue(userNotification);
//                }
//            });
//
//
//
//            Toast.makeText(this, "Ordered Successfully", Toast.LENGTH_SHORT).show();
//
//            Intent intent = new Intent(TrangMuaHang.this, TrangChu.class);
//
//            startActivity(intent);
//        }
//    }
//
//    public class HandleCheck {
//
//        public HandleCheck() {
//
//        }
//
//        public void handle_check(Integer position, Integer position_selected) {
//            if (position_selected == null || !position_selected.equals(position)) {
//                adapter_voucher.position_selected = position;
//                adapter_voucher.notifyDataSetChanged();
//                NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
//                format.setCurrency(Currency.getInstance("VND"));
//                text_view_total_price.setText(format.format(total_price - vouchers.get(position).getValue()));
//                //text_view_total_price.setText(Double.toString(total_price - vouchers.get(position).getValue()) + " đ");
//            } else {
//                adapter_voucher.position_selected = null;
//                adapter_voucher.notifyDataSetChanged();
//                NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
//                format.setCurrency(Currency.getInstance("VND"));
//                text_view_total_price.setText(format.format(total_price - vouchers.get(position).getValue()));
//                //text_view_total_price.setText(Double.toString(total_price) + " đ");
//            }
//        }
//    }
//
//    public void getMaxID(MaxNotificationIdCallBack callback) {
//        String myId = sharedPreferences.getString("id", "-1");
//        DatabaseReference drnoti = FirebaseDatabase.getInstance().getReference("Users").child(myId).child("user_notifications");
//
//        // Lấy danh sách các notificationId hiện có và tìm giá trị lớn nhất
//        drnoti.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                long maxNotificationId = 0;
//
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    String notificationId = snapshot.getKey();
//                    if (notificationId != null) {
//                        long id = Long.parseLong(notificationId);
//                        if (id > maxNotificationId) {
//                            maxNotificationId = id;
//                        }
//                    }
//                }
//
//                // Gọi callback để trả về giá trị maxNotificationId
//                callback.onMaxNotificationIdReceived(maxNotificationId);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                // Xử lý khi truy vấn bị hủy
//            }
//        });
//    }
//
//
//
//
//
//
//}
package com.example.btlandroidnc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.btlandroidnc.Model.Invoice;
import com.example.btlandroidnc.Model.Order;
import com.example.btlandroidnc.Model.Product;
import com.example.btlandroidnc.Model.User;
import com.example.btlandroidnc.Model.User_Notification;
import com.example.btlandroidnc.Model.Voucher;
import com.example.btlandroidnc.Reference.Reference;
import com.example.btlandroidnc.adapters.ProductCheckoutAdapter;
import com.example.btlandroidnc.adapters.VoucherCheckoutAdapter;
import com.example.btlandroidnc.common.ProductCartOrCheckout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class TrangMuaHang extends AppCompatActivity {
    final Reference reference = new Reference();

    final DatabaseReference products_ref = reference.getProducts();

    final DatabaseReference invoices_ref = reference.getInvoices();

    final DatabaseReference users_ref = reference.getUsers();

    ArrayList<ProductCartOrCheckout> _products_checkout = new ArrayList<>();

    double total_price = 0;

    ListView list_view_products, list_view_vouchers;

    ProductCheckoutAdapter adapter;

    TextView text_view_sub_total_price, text_view_total_price;

    EditText edit_text_address;

    Button button_order;

    ImageButton image_button_home, image_button_cart;

    ImageButton bt_KhuyenMai, bt_ThongBao, bt_GioHang, bt_TTCaNhan, btTrangChu;

    HashMap<String, Product> products = new HashMap<>();

    boolean is_from_cart;

    SharedPreferences sharedPreferences;
    ArrayList<Voucher> vouchers = new ArrayList<Voucher>();

    VoucherCheckoutAdapter adapter_voucher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_trang_mua_hang);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        sharedPreferences = getSharedPreferences("com.example.sharedprerences", Context.MODE_PRIVATE);
        mapping_client();

        Intent intent = getIntent();

        HashMap<String, Integer> _h_products = (HashMap<String, Integer>) intent.getSerializableExtra("products");

        is_from_cart = intent.getBooleanExtra("is_from_cart", false);

        assert _h_products != null;
        for (String key : _h_products.keySet()) {
            int _quantity = _h_products.get(key);

            products_ref.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.d("A", "A");
                    Product product = snapshot.getValue(Product.class);

                    total_price += product.getPrice() * _quantity;

                    products.put(key, product);

                    _products_checkout.add(new ProductCartOrCheckout(product, _quantity));

                    adapter.notifyDataSetChanged();

                    NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                    format.setCurrency(Currency.getInstance("VND"));
                    text_view_sub_total_price.setText(format.format(total_price));
                    text_view_total_price.setText(format.format(total_price));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        if (_products_checkout != null) {
            adapter = new ProductCheckoutAdapter(TrangMuaHang.this, _products_checkout);
            list_view_products.setAdapter(adapter);
        }

        button_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handle_checkout();
            }
        });

        bt_TTCaNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TrangMuaHang.this, TrangCaNhan.class);
                startActivity(i);
            }
        });

        bt_ThongBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TrangMuaHang.this, TrangThongBao.class);
                startActivity(i);
            }
        });

        bt_KhuyenMai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TrangMuaHang.this, TrangKhuyenMai.class);
                startActivity(i);
            }
        });

        bt_GioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrangMuaHang.this, TrangGioHang.class);
                startActivity(intent);
            }
        });

        btTrangChu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrangMuaHang.this, TrangChu.class);
                startActivity(intent);
            }
        });

        adapter_voucher = new VoucherCheckoutAdapter(TrangMuaHang.this, vouchers, new HandleCheck());

        SharedPreferences sharedpreferences = getSharedPreferences("com.example.sharedprerences", Context.MODE_PRIVATE);
        String user_id = sharedpreferences.getString("id", "");

        users_ref.child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                for (Voucher _v : user.getVouchers()) {
                    if (total_price > _v.getCondition()) {
                        vouchers.add(_v);
                    }
                }
                adapter_voucher = new VoucherCheckoutAdapter(TrangMuaHang.this, vouchers, new HandleCheck());
                list_view_vouchers.setAdapter(adapter_voucher);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void mapping_client() {
        this.list_view_products = findViewById(R.id.list_view_products);
        this.text_view_sub_total_price = findViewById(R.id.TongTien);
        this.text_view_total_price = findViewById(R.id.ThanhTien);
        this.edit_text_address = findViewById(R.id.address);
        this.button_order = findViewById(R.id.order);
        this.list_view_vouchers = findViewById(R.id.vouchers);
        bt_ThongBao = findViewById(R.id.button3);
        bt_TTCaNhan = findViewById(R.id.button5);
        bt_KhuyenMai = findViewById(R.id.button2);
        bt_GioHang = findViewById(R.id.button4);
        btTrangChu = findViewById(R.id.button1);
    }

    private void handle_checkout() {
        String address = edit_text_address.getText().toString();

        if (address.isEmpty()) {
            Toast toast = Toast.makeText(TrangMuaHang.this, "Vui lòng nhập địa chỉ", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            // Tạo mới hóa đơn và tạo key trên Firebase
            String new_invoice_id = invoices_ref.push().getKey();
            // Danh sách sản phẩm trong đơn hàng
            ArrayList<Order> orders = new ArrayList<Order>();

            float total_price = 0;

            SharedPreferences sharedpreferences = getSharedPreferences("com.example.sharedprerences", Context.MODE_PRIVATE);
            String user_id = sharedpreferences.getString("id", "");

            for (ProductCartOrCheckout _p : _products_checkout) {
                Order order = new Order(_p.product.getId(), _p.quantity, _p.quantity * _p.product.getPrice(), 0);
                total_price += _p.quantity * _p.product.getPrice();
                orders.add(order);
            }

            // Áp dụng giảm giá cho hóa đơn
            float discount = (adapter_voucher != null && adapter_voucher.position_selected != null)
                    ? vouchers.get(adapter_voucher.position_selected).getValue()
                    : 0;

            // Tính toán tổng giá sau khi giảm giá
            float discounted_total_price = total_price - discount;

            Invoice new_invoice = new Invoice(new_invoice_id, user_id, address, total_price, discount, orders);
            new_invoice.setTotal(discounted_total_price); // Đặt tổng giá sau khi giảm giá

            // Xóa mã giảm giá sau khi mua
            users_ref.child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = snapshot.getValue(User.class);

                    if (adapter_voucher.position_selected != null) {
                        vouchers.remove(vouchers.get(Integer.valueOf(adapter_voucher.position_selected)));
                    }
                    Log.d("length", "onDataChange: " + vouchers.size());
                    users_ref.child(user.getId()).child("vouchers").setValue(vouchers);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

            // Thêm hóa đơn vào Firebase
            invoices_ref.child(new_invoice_id).setValue(new_invoice);

            // Xóa giỏ hàng sau khi mua nếu người dùng đến từ giỏ hàng
            if (is_from_cart) {
                users_ref.child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);
                        for (ProductCartOrCheckout _p : _products_checkout) {
                            user.delete_product(_p.product.getId());
                        }
                        users_ref.child(user.getId()).child("cart").setValue(user.getCart());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }

            // Xử lý thông báo
            getMaxID(new MaxNotificationIdCallBack() {
                @Override
                public void onMaxNotificationIdReceived(long maxNotificationId) {
                    String myId = sharedPreferences.getString("id", "-1");
                    DatabaseReference drnoti = FirebaseDatabase.getInstance().getReference("Users").child(myId).child("user_notifications");
                    String notificationId = String.valueOf(maxNotificationId + 1);
                    String name = "Chờ phê duyệt";
                    String content = "Đơn hàng có mã " + new_invoice_id + " đã được đặt thành công và đang chờ phê duyệt.";
                    Date date = Calendar.getInstance().getTime();
                    User_Notification userNotification = new User_Notification(notificationId, name, content, date);
                    drnoti.child(notificationId).setValue(userNotification);
                }
            });

            Toast.makeText(this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(TrangMuaHang.this, TrangChu.class);
            startActivity(intent);
        }
    }

    private void getMaxID(MaxNotificationIdCallBack callBack) {
        String myId = sharedPreferences.getString("id", "-1");
        DatabaseReference dr = FirebaseDatabase.getInstance().getReference("Users").child(myId).child("user_notifications");

        dr.orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long maxId = 0;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String key = ds.getKey();
                    long id = Long.parseLong(key);
                    if (id > maxId) {
                        maxId = id;
                    }
                }
                callBack.onMaxNotificationIdReceived(maxId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle possible errors.
            }
        });
    }

    private interface MaxNotificationIdCallBack {
        void onMaxNotificationIdReceived(long maxNotificationId);
    }

    public class HandleCheck {

        public HandleCheck() {
        }

        public void handle_check(Integer position, Integer position_selected) {
            if (position_selected == null || !position_selected.equals(position)) {
                adapter_voucher.position_selected = position;
                adapter_voucher.notifyDataSetChanged();
                float discount = vouchers.get(position).getValue();
                float discounted_price = (float) (total_price - discount);
                NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                format.setCurrency(Currency.getInstance("VND"));
                text_view_total_price.setText(format.format(discounted_price));
            } else {
                adapter_voucher.position_selected = null;
                adapter_voucher.notifyDataSetChanged();
                NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                format.setCurrency(Currency.getInstance("VND"));
                text_view_total_price.setText(format.format(total_price));
            }
        }
    }
}
