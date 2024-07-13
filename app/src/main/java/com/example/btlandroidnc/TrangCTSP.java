package com.example.btlandroidnc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.btlandroidnc.Model.Product;
import com.example.btlandroidnc.Model.User;
import com.example.btlandroidnc.Reference.Reference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;

public class TrangCTSP extends AppCompatActivity {
    private final Reference reference = new Reference();

    private final DatabaseReference products_ref = reference.getProducts();

    private final DatabaseReference user_ref = reference.getUsers();

    private ImageView image_view_product_image, add_action, minus_action;

    TextView text_view_product_name, text_view_price, text_view_product_description, text_view_product_quantity;
//    text_view_price_sale
    private Button button_add_to_cart, button_buy_now;

    ImageButton bt_KhuyenMai, bt_ThongBao, bt_GioHang, bt_TTCaNhan, btTrangChu;
    private ImageButton home_button, cart_button;

    private String product_id;

    private int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_trang_ctsp);

        mapping_client();

        bt_TTCaNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TrangCTSP.this, TrangCaNhan.class);
                startActivity(i);
            }
        });

        bt_ThongBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TrangCTSP.this,TrangThongBao.class);
                startActivity(i);
            }
        });


        bt_KhuyenMai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TrangCTSP.this, TrangKhuyenMai.class);
                startActivity(i);
            }
        });

// Xử lý khi người dùng click vào nút Giỏ hàng
        bt_GioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrangCTSP.this, TrangGioHang.class);
                startActivity(intent);
            }
        });

        btTrangChu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrangCTSP.this, TrangChu.class);
                startActivity(intent);
            }
        });



        this.product_id = getIntent().getStringExtra("product_id");

        this.button_buy_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Integer> products = new HashMap<String, Integer>();

                products.put(product_id, quantity);

                Intent intent = new Intent(TrangCTSP.this, TrangMuaHang.class);

                intent.putExtra("products", products);

                startActivity(intent);
            }
        });

        this.button_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAddToCart();
            }
        });

        products_ref.child(product_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot data_snapshot) {
                Product product = data_snapshot.getValue(Product.class);

                text_view_product_name.setText(product.getName());
                NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                format.setCurrency(Currency.getInstance("VND"));
                text_view_price.setText(format.format(product.getPrice()));
//                text_view_price.setText(Float.toString(product.getPrice()));
//                text_view_price_sale.setText(Float.toString(product.getPrice() - product.getPrice_sale()));
                text_view_product_description.setText(product.getDescription());
                Glide.with(TrangCTSP.this).load(product.getImage()).into(image_view_product_image);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        add_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity += 1;
                text_view_product_quantity.setText(Integer.toString(quantity));
            }
        });

        minus_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity == 1) {
                    return;
                }

                quantity -= 1;
                text_view_product_quantity.setText(Integer.toString(quantity));
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void mapping_client() {
        image_view_product_image = findViewById(R.id.image_view_product_image);
        text_view_product_name = findViewById(R.id.text_view_product_name);
        text_view_price = findViewById(R.id.text_view_price);
//        text_view_price_sale = findViewById(R.id.text_view_price_sale);
        text_view_product_description = findViewById(R.id.text_view_product_description);
        button_add_to_cart = findViewById(R.id.button_add_to_cart);
        button_buy_now = findViewById(R.id.button_buy_now);
//        home_button = findViewById(R.id.button1);
//        cart_button = findViewById(R.id.button4);

        bt_ThongBao = findViewById(R.id.button3);
        bt_TTCaNhan = findViewById(R.id.button5);
        bt_KhuyenMai = findViewById(R.id.button2);
        bt_GioHang = findViewById(R.id.button4);
        btTrangChu = findViewById(R.id.button1);

        add_action = findViewById(R.id.add_action);
        minus_action = findViewById(R.id.minus_action);
        text_view_product_quantity = findViewById(R.id.product_quantity);
        text_view_product_quantity.setText(Integer.toString(quantity));
    }

    private void handleAddToCart() {
        SharedPreferences sharedpreferences = getSharedPreferences("com.example.sharedprerences",
                Context.MODE_PRIVATE);

        String user_id = sharedpreferences.getString("id", "");

        user_ref.child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                assert user != null;

                for (int i = 0; i < quantity; i++) {
                    user.add_to_cart(product_id);
                }

                user_ref.child(user_id).child("cart").setValue(user.getCart());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //Toast toast = Toast.makeText(this, user_id, Toast.LENGTH_LONG);

        //toast.show();

    }

}