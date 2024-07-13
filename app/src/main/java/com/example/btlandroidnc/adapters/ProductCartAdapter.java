package com.example.btlandroidnc.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.btlandroidnc.Model.User;
import com.example.btlandroidnc.R;
import com.example.btlandroidnc.Reference.Reference;
import com.example.btlandroidnc.TrangGioHang;
import com.example.btlandroidnc.common.ProductCartOrCheckout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ProductCartAdapter extends ArrayAdapter<ProductCartOrCheckout> {
    private Context context;

    private List<ProductCartOrCheckout> product_cards;

    TrangGioHang.HandleProductCart handler;

    public SparseArray<String> id_products_selected = new SparseArray<>();


    public ProductCartAdapter(@NonNull Context context, List<ProductCartOrCheckout> product_cards, TrangGioHang.HandleProductCart hander) {
        super(context, 0, product_cards);

        this.context = context;
        this.product_cards = product_cards;
        this.handler = hander;
    }

    private final Reference reference = new Reference();

    private final DatabaseReference users_ref = reference.getUsers();


    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convert_view, ViewGroup parent) {
        SharedPreferences sharedpreferences = this.context.getSharedPreferences("com.example.sharedprerences",
                Context.MODE_PRIVATE);

        String user_id = sharedpreferences.getString("id", "");

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View item_view = inflater.inflate(R.layout.item_sp_giohang, parent, false);

        ProductCartOrCheckout item = product_cards.get(position);

        if (item.quantity > 0) {
            ImageView image_item = item_view.findViewById(R.id.anh);
            TextView text_view_product_name = item_view.findViewById(R.id.name);
            TextView text_view_product_price = item_view.findViewById(R.id.product_price);
            TextView text_view_quantity = item_view.findViewById(R.id.product_quantity);
            CheckBox check_item = item_view.findViewById(R.id.check);
            ImageView add_action = item_view.findViewById(R.id.add_action),
                    minus_action = item_view.findViewById(R.id.minus_action);

            Glide.with(getContext()).
                    load(item.product.getImage())
                    .into(image_item);

            text_view_product_name.setText(item.product.getName());

            NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            format.setCurrency(Currency.getInstance("VND"));
            text_view_product_price.setText(format.format(item.product.getPrice()));
//            text_view_product_price.setText(Float.toString(item.product.getPrice()));

            text_view_quantity.setText(Integer.toString(item.quantity));

            if (Objects.equals(id_products_selected.get(position), item.product.getId())) {
                check_item.setChecked(true);
            }

            add_action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    users_ref.child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User user = snapshot.getValue(User.class);

                            user.add_to_cart(item.product.getId());

                            users_ref.child(user_id).child("cart").setValue(user.getCart());

                            text_view_quantity.setText(Integer.toString(user.getCart().get_products().get(item.product.getId())));

                            item.quantity = user.getCart().get_products().get(item.product.getId());

                            product_cards.set(position, item);

                            handler.update_products_cart(position, item);

                            handler.update_change_check_box();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            });

            minus_action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    users_ref.child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User user = snapshot.getValue(User.class);

                            user.minus_to_cart(item.product.getId());

                            users_ref.child(user_id).child("cart").setValue(user.getCart());

                            if (user.getCart().get_products().containsKey(item.product.getId())) {
                                text_view_quantity.setText(Integer.toString(user.getCart().get_products().get(item.product.getId())));

                                item.quantity = user.getCart().get_products().get(item.product.getId());

                                product_cards.set(position, item);

                                handler.update_products_cart(position, item);
                            } else {
                                id_products_selected.remove(position);
                                handler.remove_product_cart(position);
                            }
                            handler.update_change_check_box();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });

                }
            });

            check_item.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    id_products_selected.put(position, item.product.getId());
                } else {
                    id_products_selected.remove(position);
                }
                handler.update_change_check_box();
            });
        }

        return item_view;
    }
}
