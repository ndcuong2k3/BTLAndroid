package com.example.btlandroidnc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.btlandroidnc.Model.Invoice;
import com.example.btlandroidnc.adapters.InvoiceAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TrangXemDHDaMua extends AppCompatActivity {

    DatabaseReference usersRef;
    List<Invoice> invoiceList;
    ListView listView;
    InvoiceAdapter invoiceAdapter;
    SharedPreferences sharedPreferences;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_trang_xem_dhdamua);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
//        getSupportActionBar().hide();
        sharedPreferences= getSharedPreferences("com.example.sharedprerences", Context.MODE_PRIVATE);
        String myId = sharedPreferences.getString("id", "-1");

        usersRef = FirebaseDatabase.getInstance().getReference("Invoices");
        listView=findViewById(R.id.user_invoices);

        invoiceList =new ArrayList<>();
        invoiceAdapter =new InvoiceAdapter(this,R.layout.item_kh_xemdhdamua,invoiceList);
        listView.setAdapter(invoiceAdapter);

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                invoiceList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if(!snapshot.child("user_id").getValue(String.class).equals(myId))
                        continue;
                    Invoice invoice=snapshot.getValue(Invoice.class);
                    invoiceList.add(invoice);
                }
                invoiceAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}