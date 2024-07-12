package com.example.btlandroidnc.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.btlandroidnc.R;
import com.example.btlandroidnc.common.ProductCartOrCheckout;

import java.util.List;

public class ProductCheckoutAdapter extends ArrayAdapter<ProductCartOrCheckout> {
    private Context context;

    private List<ProductCartOrCheckout> products_checkout;

    public ProductCheckoutAdapter(@NonNull Context context, List<ProductCartOrCheckout> product_checkout) {
        super(context, 0, product_checkout);

        this.context = context;
        this.products_checkout = product_checkout;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convert_view, ViewGroup parent) {
        SharedPreferences sharedpreferences = this.context.getSharedPreferences("com.example.sharedprerences", Context.MODE_PRIVATE);

        String user_id = sharedpreferences.getString("id", "");

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View item_view = inflater.inflate(R.layout.item_product_checkout, parent, false);

        ProductCartOrCheckout item = products_checkout.get(position);

        ImageView image_item = item_view.findViewById(R.id.image);
        TextView name_item = item_view.findViewById(R.id.name), quantity_item = item_view.findViewById(R.id.quantity);

        Glide.with(getContext()).
                load(item.product.getImage())
                .into(image_item);

        name_item.setText(item.product.getName());

        quantity_item.setText(Integer.toString(item.quantity));

        return item_view;
    }
}
