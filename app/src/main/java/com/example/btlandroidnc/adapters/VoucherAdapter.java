package com.example.btlandroidnc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.btlandroidnc.Model.User;
import com.example.btlandroidnc.Model.Voucher;
import com.example.btlandroidnc.R;
import com.example.btlandroidnc.Reference.Reference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VoucherAdapter extends ArrayAdapter<Voucher> {
    private Context mContext;

    final Reference reference = new Reference();

    final DatabaseReference users_ref = reference.getUsers();

    DatabaseReference usersRef;
    String myID;
    private int mResource;

    public VoucherAdapter(@NonNull Context context, int resource, @NonNull List<Voucher> objects, String myId) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        myID = myId;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        usersRef = FirebaseDatabase.getInstance().getReference("Users").child(myID).child("vouchers");

        View itemView = convertView;
        if (itemView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            itemView = inflater.inflate(mResource, parent, false);
        }

        TextView textViewName = itemView.findViewById(R.id.title1);
        Button b = itemView.findViewById(R.id.btLuu1);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Thêm voucher thanh công", Toast.LENGTH_SHORT).show();
                users_ref.child(myID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);

                        ArrayList<Voucher> vouchers = user.getVouchers();

                        Voucher voucher = getItem(position);

                        vouchers.add(voucher);

                        usersRef.setValue(vouchers);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });


        Voucher voucher = getItem(position);

        if (voucher != null) {
            textViewName.setText(voucher.getDescription());

            // textViewName.setText("user.getName()");
        }

        return itemView;
    }
}
