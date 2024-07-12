package com.example.btlandroidnc.Reference;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.btlandroidnc.Model.Product;
import com.example.btlandroidnc.Model.User;
import com.example.btlandroidnc.Model.Voucher;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Reference {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private DatabaseReference users = database.getReference("Users");

    private DatabaseReference vouchers = database.getReference("Vouchers");

    private DatabaseReference products = database.getReference("Products");

    private DatabaseReference invoices = database.getReference("Invoices");

//    private DatabaseReference orders = database.getReference("Orders");

    public Reference() {
        products.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Product product_removed = snapshot.getValue(Product.class);

                users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot data_snapshot) {
                        for (DataSnapshot userSnapshot : data_snapshot.getChildren()) {
                            User user = userSnapshot.getValue(User.class);

                            assert user != null;
                            user.delete_product(product_removed.getId());

                            users.child(user.getId()).child("cart").setValue(user.getCart());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        vouchers.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(DataSnapshot snapshot, String prevChildKey) {
                Voucher updatedVoucher = snapshot.getValue(Voucher.class);
                String voucherId = snapshot.getKey();

                users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            User user = userSnapshot.getValue(User.class);
                            ArrayList<Voucher> vouchers = user.getVouchers();
                            for (int i = 0; i < vouchers.size(); i++) {
                                if (vouchers.get(i).getId().equals(voucherId)) {
                                    vouchers.set(i, updatedVoucher);
                                    userSnapshot.getRef().child("vouchers").setValue(vouchers);
                                    break;
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                String removedVoucherId = snapshot.getKey();

                users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            User user = userSnapshot.getValue(User.class);
                            ArrayList<Voucher> vouchers = user.getVouchers();
                            for (int i = 0; i < vouchers.size(); i++) {
                                if (vouchers.get(i).getId().equals(removedVoucherId)) {
                                    vouchers.remove(i);
                                    userSnapshot.getRef().child("vouchers").setValue(vouchers);
                                    break;
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public DatabaseReference getUsers() {
        return users;
    }

    public DatabaseReference getVouchers() {
        return vouchers;
    }

    public DatabaseReference getProducts() {
        return products;
    }

    public DatabaseReference getInvoices() {
        return invoices;
    }
}
