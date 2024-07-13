package com.example.btlandroidnc;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.btlandroidnc.Model.Voucher;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class TrangThemVoucher extends AppCompatActivity {
    private EditText editTextTitle, editTextDescription, editTextImage, editTextValue, editTextCondition;
    private Button buttonSave;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_them_voucher);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextImage = findViewById(R.id.editTextImage);
        editTextValue = findViewById(R.id.editTextValue);
        editTextCondition = findViewById(R.id.editTextCondition);
        buttonSave = findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveVoucher();
            }
        });
    }

    private void saveVoucher() {
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String image = editTextImage.getText().toString().trim();
        float value = Float.parseFloat(editTextValue.getText().toString().trim());
        float condition = Float.parseFloat(editTextCondition.getText().toString().trim());
        Log.d("VoucherData", "Title: " + title);
        Log.d("VoucherData", "Description: " + description);
        Log.d("VoucherData", "Image: " + image);
        Log.d("VoucherData", "Value: " + value);
        Log.d("VoucherData", "Condition: " + condition);
        // Tạo đối tượng Voucher mới
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Vouchers");
        String voucherId = databaseReference.push().getKey();
        Voucher voucher = new Voucher(voucherId,title, description, image, value, condition);
        databaseReference.child(voucherId).setValue(voucher);




        // Create a reference to the child node for the current voucher
//        DatabaseReference voucherReference = databaseReference.child(voucherId);
//
//        // Save each property of the voucher as a separate child node
//        voucherReference.child("title").setValue(voucher.getTitle());
//        voucherReference.child("description").setValue(voucher.getDescription());
//        voucherReference.child("image").setValue(voucher.getImage());
//        voucherReference.child("value").setValue(voucher.getValue());
//        voucherReference.child("condition").setValue(voucher.getCondition());

        Toast.makeText(this, "Voucher added successfully", Toast.LENGTH_SHORT).show();
        finish(); // Close Activity after saving
//        DatabaseReference voucherReference = databaseReference.child(voucherId);
//        voucherReference.child("condition").setValue(condition);
//        voucherReference.child("value").setValue(value);
//        voucherReference.child("title").setValue(voucher.getTitle());
//        voucherReference.child("description").setValue(voucher.getDescription());
//        voucherReference.child("image").setValue(voucher.getImage());
//        databaseReference.child(voucherId).setValue(voucher);


    }
}