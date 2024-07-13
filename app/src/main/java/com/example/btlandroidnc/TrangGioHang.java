package com.example.btlandroidnc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

import com.example.btlandroidnc.Model.Product;
import com.example.btlandroidnc.Model.User;
import com.example.btlandroidnc.Reference.Reference;
import com.example.btlandroidnc.adapters.ProductCartAdapter;
import com.example.btlandroidnc.common.ProductCartOrCheckout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;


public class TrangGioHang extends AppCompatActivity {
    private final Reference reference = new Reference();

    private ListView list_view_cart;

    ImageButton button1;
    ImageButton bt_KhuyenMai, bt_ThongBao, bt_GioHang, bt_TTCaNhan, btTrangChu;

    ProductCartAdapter adapter;

    ArrayList<ProductCartOrCheckout> products_cart = new ArrayList<>();

    CheckBox total_check;

    TextView text_view_selected_count, text_view_total_price;

    Button button_buy_now;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_trang_gio_hang);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mapping_client();
        bt_TTCaNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TrangGioHang.this, TrangCaNhan.class);
                startActivity(i);
            }
        });

        bt_ThongBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TrangGioHang.this,TrangThongBao.class);
                startActivity(i);
            }
        });


        bt_KhuyenMai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TrangGioHang.this, TrangKhuyenMai.class);
                startActivity(i);
            }
        });

// Xử lý khi người dùng click vào nút Giỏ hàng
        bt_GioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrangGioHang.this, TrangGioHang.class);
                startActivity(intent);
            }
        });

        btTrangChu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrangGioHang.this, TrangChu.class);
                startActivity(intent);
            }
        });




        DatabaseReference users_ref = reference.getUsers();

        DatabaseReference products_ref = reference.getProducts();

        SharedPreferences sharedpreferences = getSharedPreferences("com.example.sharedprerences", Context.MODE_PRIVATE);

        String user_id = sharedpreferences.getString("id", "");

        list_view_cart = findViewById(R.id.list_view_cart);




        users_ref.child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                if (user != null && user.getCart() != null) {

                    HashMap<String, Integer> hash_products = user.getCart().get_products();

                    if (adapter == null) {
                        products_ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                products_cart = new ArrayList<>();

                                for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                                    Product product = productSnapshot.getValue(Product.class);

                                    String product_id = product.getId();

                                    if (hash_products.containsKey(product_id)) {
                                        if (hash_products.get(product_id) > 0) {
                                            ProductCartOrCheckout product_cart = new ProductCartOrCheckout(product, hash_products.get(product_id));

                                            products_cart.add(product_cart);
                                        }
                                    }
                                }
                                HandleProductCart hander = new HandleProductCart();
                                adapter = new ProductCartAdapter(TrangGioHang.this, products_cart, hander);
                                list_view_cart.setAdapter(adapter);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        total_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HandleProductCart _h = new HandleProductCart();

                if (total_check.isChecked()) {
                    for (int i = 0; i < products_cart.size(); i++) {
                        String id = products_cart.get(i).product.getId();
                        adapter.id_products_selected.put(i, id);
                    }

                    adapter.notifyDataSetChanged();
                    _h.update_total_price();

                } else {
                    adapter.id_products_selected.clear();
                    adapter.notifyDataSetChanged();
                    _h.update_total_price();
                }
            }
        });


        button_buy_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter.id_products_selected.size() > 0) {

                    HashMap<String, Integer> _h_p = new HashMap<String, Integer>();

                    for (int i = 0; i < adapter.id_products_selected.size(); i++) {
                        int key = adapter.id_products_selected.keyAt(i);
                        String value = adapter.id_products_selected.valueAt(i);

                        ProductCartOrCheckout _p = products_cart.get(key);

                        _h_p.put(value, _p.quantity);
                    }

                    Log.d("selected", _h_p.toString());
                    Intent intent = new Intent(TrangGioHang.this, TrangMuaHang.class);

                    intent.putExtra("products", _h_p);

                    intent.putExtra("is_from_cart", true);

                    startActivity(intent);

                    return;
                }
                Toast.makeText(TrangGioHang.this, "Vui lòng chọn sản phẩm", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void mapping_client() {
        bt_ThongBao = findViewById(R.id.button3);
        bt_TTCaNhan = findViewById(R.id.button5);
        bt_KhuyenMai = findViewById(R.id.button2);
        bt_GioHang = findViewById(R.id.button4);
        btTrangChu = findViewById(R.id.button1);

        total_check = findViewById(R.id.total_check);
        text_view_selected_count = findViewById(R.id.selected_count);
        text_view_total_price = findViewById(R.id.total);
        button_buy_now = findViewById(R.id.button_buy_now);
    }

    public class HandleProductCart {

        public HandleProductCart() {
        }

        public void remove_product_cart(int position) {
            products_cart.remove(position);
            adapter.notifyDataSetChanged();
        }

        public void update_products_cart(int position, ProductCartOrCheckout product) {
            products_cart.set(position, product);
        }

        public void update_change_check_box() {
            if (products_cart != null) {

                int selected_count = adapter.id_products_selected.size();

                total_check.setChecked(selected_count == products_cart.size());

                text_view_selected_count.setText("(" + selected_count + ")");

                update_total_price();
            }
        }

        public void update_total_price() {
            int price = 0;

            for (int i = 0; i < adapter.id_products_selected.size(); i++) {
                int key = adapter.id_products_selected.keyAt(i);
                String value = adapter.id_products_selected.valueAt(i);

                ProductCartOrCheckout _p = products_cart.get(key);

                if (_p.product.getId() == value) {
                    price += _p.quantity * _p.product.getPrice();
                }
            }
            NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            format.setCurrency(Currency.getInstance("VND"));
            text_view_total_price.setText(format.format(price));
//            text_view_total_price.setText(Integer.toString(price));
        }
    }
}
