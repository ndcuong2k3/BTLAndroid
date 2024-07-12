package com.example.btlandroidnc;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.btlandroidnc.Model.Product;
import com.example.btlandroidnc.Reference.Reference;
import com.example.btlandroidnc.common.HandleFileUploadListener;
import com.example.btlandroidnc.services.FirebaseStorageUploader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class TrangThemSPMoi extends AppCompatActivity {
    private final Reference reference = new Reference();
    private final DatabaseReference products_ref = reference.getProducts();
    private Button btn_action;

    private EditText edit_text_product_name, edit_text_product_price, edit_text_product_quantity, edit_text_product_description;
    private ImageView image_view_product_image;

    private String buff_image = "";

    private boolean is_updated_image = false;

    private boolean isUpdateProduct;

    private FirebaseStorageUploader uploader = new FirebaseStorageUploader(this);

    ImageButton image_button_home;

    private HandleFileUploadListener handle_upload = new HandleFileUploadListener() {
        @Override
        public void on_upload_success(String download_url) {
            buff_image = download_url;
            is_updated_image = true;
        }

        @Override
        public void on_upload_failure(String error_message) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_them_spmoi);


        mapping_client();

        String product_id = getIntent().getStringExtra("product_id");

        isUpdateProduct = product_id != null;

        if (!isUpdateProduct) {
            btn_action.setText("Thêm");
        } else {
            btn_action.setText("Lưu");

            products_ref.child(product_id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot data_snapshot) {
                    Product product = data_snapshot.getValue(Product.class);

                    edit_text_product_name.setText(product.getName());
                    edit_text_product_price.setText(Float.toString(product.getPrice()));
                    edit_text_product_quantity.setText(Integer.toString(product.getWarehouse()));
                    edit_text_product_description.setText(product.getDescription());
                    Glide.with(TrangThemSPMoi.this).load(product.getImage()).into(image_view_product_image);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }

        btn_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOnClickAction(product_id);
            }
        });
        //khai báo để chọn ảnh
        ActivityResultLauncher<PickVisualMediaRequest> pick_media = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
            if (uri != null) {
                Glide.with(TrangThemSPMoi.this).load(uri).into(image_view_product_image);

                uploader.upload_file(uri, handle_upload);

            } else {
                Log.d("PhotoPicker", "No media selected");
            }
        });
        //click vào cái chọn ảnh
        image_view_product_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pick_media.launch(new PickVisualMediaRequest.Builder().setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE).build());
            }
        });

        image_button_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrangThemSPMoi.this, TrangAdmin.class);

                startActivity(intent);
            }
        });
    }

    private void handleOnClickAction(String product_id) {
        //truyền sang trang UpdaeSP
        if (isUpdateProduct) {
            products_ref.child(product_id).child("name").setValue(String.valueOf(edit_text_product_name.getText()));

            String price_update = String.valueOf(edit_text_product_price.getText());
            products_ref.child(product_id).child("price").setValue(Float.valueOf(price_update));

            String warehouse_update = String.valueOf(edit_text_product_quantity.getText());
            products_ref.child(product_id).child("warehouse").setValue(Integer.valueOf(warehouse_update));

            products_ref.child(product_id).child("description").setValue(String.valueOf(edit_text_product_description.getText()));

            if (is_updated_image)
                products_ref.child(product_id).child("image").setValue(buff_image);

            Toast.makeText(this, "Updated Successfully", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(TrangThemSPMoi.this, TrangUpdateSP.class);

            startActivity(intent);
        } else {
            String new_product_id = products_ref.push().getKey();

            String product_name = edit_text_product_name.getText().toString();

            String product_description = edit_text_product_description.getText().toString();

            int product_warehouse = Integer.parseInt(edit_text_product_quantity.getText().toString());

            float product_price = Float.parseFloat(edit_text_product_price.getText().toString());

            String product_image = is_updated_image ? buff_image : "";

            Product new_product = new Product(new_product_id, product_name, product_description, 0, product_warehouse, product_price, 0, product_image, "", "", "");

            products_ref.child(new_product.getId()).setValue(new_product);

            Toast.makeText(this, "Created Successfully", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(TrangThemSPMoi.this, TrangUpdateSP.class);

            startActivity(intent);
        }
    }

    private void mapping_client() {
        this.btn_action = findViewById(R.id.btn_action);
        this.edit_text_product_name = findViewById(R.id.editTextProductName);
        this.edit_text_product_price = findViewById(R.id.editTextPrice);
        this.edit_text_product_quantity = findViewById(R.id.editTextQuantity);
        this.edit_text_product_description = findViewById(R.id.editTextDescription);
        this.image_view_product_image = findViewById(R.id.imageView);
        this.image_button_home = findViewById(R.id.button1);
    }
}