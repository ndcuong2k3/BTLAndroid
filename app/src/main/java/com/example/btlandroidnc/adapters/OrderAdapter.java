package com.example.btlandroidnc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.btlandroidnc.Model.Order;
import com.example.btlandroidnc.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


public class OrderAdapter extends ArrayAdapter<Order> {
    private Context mContext;
    private int mResource;
    DatabaseReference usersRef;

    public OrderAdapter(@NonNull Context context, int resource, @NonNull List<Order> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource=resource;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            itemView = inflater.inflate(mResource, parent, false);
        }

        TextView textViewName = itemView.findViewById(R.id.name_pr);
        TextView txtSL = itemView.findViewById(R.id.quantity_pr);
        ImageView image_item = itemView.findViewById(R.id.image);

        Order order = getItem(position);

        if (order != null) {
            textViewName.setText(order.getProduct_id().toString());
            txtSL.setText(String.valueOf(order.getQuantity()));
            String id_product =order.getProduct_id().toString();
            usersRef= FirebaseDatabase.getInstance().getReference("Products").child(id_product);
            final TextView finalTextViewName = textViewName;
            usersRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    finalTextViewName.setText(snapshot.child("name").getValue(String.class));
//                    String link = snapshot.child("image").getValue(String.class);
//                    Glide.with(getContext()).
//                            load(link)
//                            .into(image_item);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            // textViewName.setText("user.getName()");
        }

        return itemView;
    }

}
