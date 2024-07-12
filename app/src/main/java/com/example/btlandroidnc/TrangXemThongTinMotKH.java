package com.example.btlandroidnc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TrangXemThongTinMotKH extends AppCompatActivity {
    DatabaseReference usersRef;
    int frontId=-1;
    SharedPreferences sharedPreferences;
    TextView txtEmail,txtSDT,txtNgaySinh,txtMember,txtProFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        getSupportActionBar().hide();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        // sharedPreferences= getSharedPreferences("com.example.sharedprerences", Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        //  String myId = sharedPreferences.getString("id", "-1");

        // String x = "5";

        Intent i=getIntent();
        String id = i.getStringExtra("id");

        setContentView(R.layout.activity_trang_xem_thong_tin_mot_kh);

        usersRef = FirebaseDatabase.getInstance().getReference("Users").child(id);
        TextView txtHoTen = findViewById(R.id.textViewSuaTen);
        txtEmail=findViewById(R.id.textViewEmail);
        txtSDT=findViewById(R.id.textViewSDT);
        txtNgaySinh=findViewById(R.id.textViewNgaySinh);
        txtMember = findViewById(R.id.membershipLabel);
        txtProFile = findViewById(R.id.profileName);

        //if(id=="-1")
        txtMember.setText("USER");


        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    frontId = dataSnapshot.child("email").getValue(String.class).indexOf('@');
                    DataSnapshot dateOfBirthSnapshot = dataSnapshot.child("date_of_birth");
                    txtHoTen.setText("Họ tên: "+ dataSnapshot.child("name").getValue(String.class));
                    txtEmail.setText("Email: "+ dataSnapshot.child("email").getValue(String.class));
                    txtSDT.setText("SDT: "+ dataSnapshot.child("phone").getValue(String.class));
                    txtNgaySinh.setText("Ngày sinh: "+ String.valueOf( dateOfBirthSnapshot.child("date").getValue(Long.class)) +"/"+
                            String.valueOf( dateOfBirthSnapshot.child("month").getValue(Long.class)) +"/"+
                            String.valueOf( dateOfBirthSnapshot.child("year").getValue(Long.class))
                    );


                    if(frontId>=0)
                        txtProFile.setText(dataSnapshot.child("email").getValue(String.class).substring(0,frontId));
                    else
                        txtProFile.setText(dataSnapshot.child("email").getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}