package com.example.btlandroidnc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.btlandroidnc.Model.Product;
import com.example.btlandroidnc.Reference.Reference;
import com.example.btlandroidnc.adapters.ProductIndexAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TrangChu extends AppCompatActivity {
    ImageButton bt_KhuyenMai, bt_ThongBao, bt_GioHang, bt_TTCaNhan, image_button_search;
    ImageView imageView;
    private EditText edit_text_search_value;
    SharedPreferences sharedPreferences;
    TextView textView;
    //private final Reference reference = new Reference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);

        imageView = findViewById(R.id.imgview);
        textView = findViewById(R.id.profileName);
        bt_ThongBao = findViewById(R.id.button3);
        bt_TTCaNhan = findViewById(R.id.button5);
        bt_KhuyenMai = findViewById(R.id.button2);
        bt_GioHang = findViewById(R.id.button4);
        image_button_search = findViewById(R.id.image_button_search);
        edit_text_search_value = findViewById(R.id.edit_text_search_value);
        ListView list_view_products = findViewById(R.id.products);
        sharedPreferences = getSharedPreferences("com.example.sharedprerences", Context.MODE_PRIVATE);
        String myId = sharedPreferences.getString("id", "-1");
//        DatabaseReference products_ref = reference.getProducts();
        DatabaseReference products_ref = FirebaseDatabase.getInstance().getReference("Products");
        DatabaseReference users_ref = FirebaseDatabase.getInstance().getReference("Users").child(myId);

        // Xử lý khi người dùng click vào ô tìm kiếm
        image_button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search_value = edit_text_search_value.getText().toString();

                // Lọc sản phẩm theo từ khóa tìm kiếm
                products_ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        ArrayList<Product> products = new ArrayList<>();
                        for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                            Product product = productSnapshot.getValue(Product.class);
                            if (search_value.isEmpty() || product.getName().contains(search_value)) {
                                products.add(product);
                            }
                        }
                        ProductIndexAdapter productIndexAdapter = new ProductIndexAdapter(TrangChu.this, products);
                        list_view_products.setAdapter(productIndexAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });

        // Hiển thị thông tin người dùng
        users_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                textView.setText(snapshot.child("name").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        // Lấy danh sách sản phẩm từ Firebase và hiển thị trên ListView
        products_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                ArrayList<Product> products = new ArrayList<>();
                for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                    Product product = productSnapshot.getValue(Product.class);
                    products.add(product);
                }
                ProductIndexAdapter productIndexAdapter = new ProductIndexAdapter(TrangChu.this, products);
                list_view_products.setAdapter(productIndexAdapter);


                // Xử lý khi người dùng click vào một sản phẩm trong danh sách
                list_view_products.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Product selectedProduct = products.get(position);
                        String productId = selectedProduct.getId();

                        // Chuyển sang trang chi tiết sản phẩm và gửi product_id qua intent
                        Intent intent = new Intent(TrangChu.this, TrangCTSP.class);
                        intent.putExtra("product_id", productId);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        // Xử lý khi người dùng click vào nút Thông báo
        bt_ThongBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TrangChu.this, TrangThongBao.class);
                startActivity(i);
            }
        });

        // Xử lý khi người dùng click vào nút Thông tin cá nhân
        bt_TTCaNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TrangChu.this, TrangCaNhan.class);
                startActivity(i);
            }
        });

        // Xử lý khi người dùng click vào nút Khuyến mãi
        bt_KhuyenMai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TrangChu.this, TrangKhuyenMai.class);
                startActivity(i);
            }
        });

        // Xử lý khi người dùng click vào nút Giỏ hàng
        bt_GioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrangChu.this, TrangGioHang.class);
                startActivity(intent);
            }
        });
    }
}
