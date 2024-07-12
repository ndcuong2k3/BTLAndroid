package com.example.btlandroidnc;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.btlandroidnc.Model.Product;
import com.example.btlandroidnc.adapters.ProductCheckoutAdapter;
import com.example.btlandroidnc.common.ProductCartOrCheckout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TrangKHXemCTHoaDon extends AppCompatActivity {
    DatabaseReference usersRef;
    ListView listView;
    TextView txtTTGiaoHang,txtTongTien,txtGiamGia,txtThanhTien;
    List<ProductCartOrCheckout> orderList;
    ProductCheckoutAdapter orderAdapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_trang_khxem_cthoa_don);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent i =getIntent();
        String id = i.getStringExtra("id");
        txtTTGiaoHang=findViewById(R.id.ThongTinGiaoHang);
        listView = findViewById(R.id.products);
        txtThanhTien =findViewById(R.id.ThanhTien);
        txtTongTien = findViewById(R.id.TongTien);
        txtGiamGia = findViewById(R.id.GiamGia);
        orderList =new ArrayList<>();
        orderAdapter =new ProductCheckoutAdapter(TrangKHXemCTHoaDon.this,orderList);
        listView.setAdapter(orderAdapter);
        usersRef = FirebaseDatabase.getInstance().getReference("Invoices").child(id);
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.child("is_validate").getValue(Boolean.class))
                    txtTTGiaoHang.setText("Đơn hàng đang được phê duyệt");
                txtTongTien.setText(String.valueOf(snapshot.child("total").getValue(Long.class)+snapshot.child("discount").getValue(Long.class))+"đ");
                txtThanhTien.setText(String.valueOf(snapshot.child("total").getValue(Long.class))+"đ");
                txtGiamGia.setText(String.valueOf(snapshot.child("discount").getValue(Long.class))+"đ");
                DataSnapshot dataSnapshot = snapshot.child("orders");
                for (DataSnapshot order_child : dataSnapshot.getChildren()){
                    String  product_id =order_child.child("product_id").getValue(String.class);
                    int quantity =order_child.child("quantity").getValue(Integer.class);
                    DatabaseReference usersRef3 = FirebaseDatabase.getInstance().getReference("Products").child(product_id);
                    usersRef3.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()) {
                                Product product = snapshot.getValue(Product.class);
                                ProductCartOrCheckout productCartOrCheckout = new ProductCartOrCheckout(product, quantity);
                                orderList.add(productCartOrCheckout);
                            }
                            orderAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}