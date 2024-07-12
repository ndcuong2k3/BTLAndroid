package com.example.btlandroidnc;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.btlandroidnc.Model.Voucher;
import com.example.btlandroidnc.adapters.VoucherAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TrangKhuyenMai extends AppCompatActivity {
    DatabaseReference usersRef;

    List<Voucher> vouchers;
    VoucherAdapter adapter;
    SharedPreferences sharedPreferences;

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_trang_khuyen_mai);
        sharedPreferences= getSharedPreferences("com.example.sharedprerences", Context.MODE_PRIVATE);
        String myId = sharedPreferences.getString("id", "-1");

        usersRef = FirebaseDatabase.getInstance().getReference("Vouchers");
        listView=findViewById(R.id.vouchers);
        vouchers =new ArrayList<>();
        adapter=new VoucherAdapter(this,R.layout.item_voucher,vouchers,myId);
        listView.setAdapter(adapter);

        usersRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                vouchers.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    String title = snapshot.child("title").getValue(String.class);

                    String description = snapshot.child("description").getValue(String.class);
                    String id = snapshot.child("id").getValue(String.class);
                    String image = snapshot.child("image").getValue(String.class);
                    float value = snapshot.child("value").getValue(Float.class);
                    String id1 = snapshot.getKey();
                    float condition = snapshot.child("condition").getValue(Float.class);
                    ArrayList<Voucher> arrayList =new ArrayList<>();

                    Voucher voucher = new Voucher(id,title,description,image,value,condition);
                    // User user = new User("1",Name,Email,PassWord,date,SDT,arrayList);
                    vouchers.add(voucher);

                    //User user = dataSnapshot.getValue(User.class);
                    // userList.add(user);

                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
}