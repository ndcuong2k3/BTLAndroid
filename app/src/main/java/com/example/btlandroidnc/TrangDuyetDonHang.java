package com.example.btlandroidnc;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

public class TrangDuyetDonHang extends AppCompatActivity {

    DatabaseReference usersRef;
    List<Invoice> invoiceList;
    CheckBox checkBox;
    ListView listView;
    Button btDuyet,btXoa;
    InvoiceAdapter invoiceAdapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_trang_duyet_don_hang);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent i =getIntent();
        if(i.getBooleanExtra("delete",false)){
            DatabaseReference delete = FirebaseDatabase.getInstance().getReference("Invoices").child(i.getStringExtra("id_invoice"));
            delete.removeValue();
        }
        btDuyet= findViewById(R.id.btDuyet);
        btXoa = findViewById(R.id.btXoa);
        usersRef = FirebaseDatabase.getInstance().getReference("Invoices");
        listView=findViewById(R.id.invoices);
        checkBox = findViewById(R.id.checktong);
        invoiceList =new ArrayList<>();
        invoiceAdapter =new InvoiceAdapter(this,R.layout.item_ad_duyet,invoiceList);
        listView.setAdapter(invoiceAdapter);

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                invoiceList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if(snapshot.child("is_validate").getValue(Boolean.class))
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
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                invoiceAdapter.checkAll(checkBox.isChecked(),invoiceAdapter.isCheckedList.size());
            }
        });

//        btDuyet.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                usersRef.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        //    invoiceList.clear();
//                        int count=0;
//                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                            if(snapshot.child("is_validate").getValue(Boolean.class))
//                                continue;
//
//                            if(invoiceAdapter.isCheckedList.get(count)){
//                                snapshot.child("is_validate").getRef().setValue(true);
//                                invoiceAdapter.isCheckedList.remove(count);
//
//                            }
//                            count++;
//                        }
//                        invoiceAdapter.checkAll(false,invoiceAdapter.isCheckedList.size());
//                        invoiceAdapter.notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//            }
//        });


        btXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                usersRef.addValueEventListener(new ValueEventListener() {
                    @SuppressLint("SuspiciousIndentation")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //    invoiceList.clear();
                        int count=0;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            if(snapshot.child("is_validate").getValue(Boolean.class))
                                continue;

                            if(invoiceAdapter.isCheckedList.get(count)){
                                snapshot.getRef().removeValue();
                                invoiceAdapter.isCheckedList.remove(count);

                            }
                            count++;
                        }
                        invoiceAdapter.checkAll(false,invoiceAdapter.isCheckedList.size());
                        invoiceAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}