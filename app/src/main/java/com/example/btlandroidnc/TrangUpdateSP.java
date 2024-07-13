package com.example.btlandroidnc;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btlandroidnc.Model.Product;
import com.example.btlandroidnc.Reference.Reference;
import com.example.btlandroidnc.adapters.UpdateProductAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TrangUpdateSP extends AppCompatActivity {

    private final Reference reference = new Reference();
    private ListView list_view_products;
    ImageButton btnCaNhan;

    private Button button_add_product, button_remove_product;
    private CheckBox checkBox;

    UpdateProductAdapter update_product_adapter;

//    ImageButton image_button_home;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_update_sp);
        mapping_client();
        btnCaNhan = findViewById(R.id.button5);

        btnCaNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrangUpdateSP.this,TrangAdmin.class);
                startActivity(intent);
            }
        });

        DatabaseReference products_ref = reference.getProducts();
        products_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                ArrayList<Product> products = new ArrayList<>();
                for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                    Product product = productSnapshot.getValue(Product.class);

                    products.add(product);
                }

                update_product_adapter = new UpdateProductAdapter(TrangUpdateSP.this, products);

                list_view_products.setAdapter(update_product_adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        button_add_product.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TrangUpdateSP.this, TrangThemSPMoi.class);

                startActivity(i);
            }
        }));

        button_remove_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SparseArray<String> ids = update_product_adapter.get_id_products_selected();

                for (int i = 0; i < ids.size(); i++) {
                    String value = ids.valueAt(i);

                    products_ref.child(value).removeValue();
                }
            }
        });

//        image_button_home.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(TrangUpdateSP.this, TrangAdmin.class);
//
//                startActivity(intent);
//            }
//        });
    }

    private void mapping_client() {
        list_view_products = findViewById(R.id.products);
        button_add_product = findViewById(R.id.btn_add_product);
        button_remove_product = findViewById(R.id.btnxoa);

        checkBox = findViewById(R.id.checktong);
    }
}