package com.example.btlandroidnc.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.btlandroidnc.Model.Product;
import com.example.btlandroidnc.R;
import com.example.btlandroidnc.TrangCTSP;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class ProductIndexAdapter extends ArrayAdapter<Product> {
    private List<Product> products;

    private Context context;

    public ProductIndexAdapter(Context context, List<Product> products){
        super(context, 0, products);

        this.products = products;
        this.context = context;
    }

    @Override
    public View getView(int position, View convert_view, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View row_view = inflater.inflate(R.layout.item_sp_index, parent, false);

        Product product_first = products.get(position * 2);
        Product product_sercond = null;
        if (position * 2 + 1 < products.size()) {
            product_sercond = products.get(position * 2 + 1);
        }

        ImageView image_product_first = row_view.findViewById(R.id.image_product_first);
        TextView name_product_first = row_view.findViewById(R.id.name_product_first);
        TextView price_product_first = row_view.findViewById(R.id.price_product_first);
        FrameLayout scope_product_first = row_view.findViewById(R.id.scope_product_first);

        scope_product_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TrangCTSP.class);

                intent.putExtra("product_id", product_first.getId());

                context.startActivity(intent);
            }
        });

        Glide.with(getContext()).
                load(product_first.getImage())
                .into(image_product_first);

        name_product_first.setText(product_first.getName());
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        format.setCurrency(Currency.getInstance("VND"));
        price_product_first.setText(format.format(product_first.getPrice()));
//        price_product_first.setText(String.valueOf(product_first.getPrice()));

        if(product_sercond != null){
            ImageView image_product_second = row_view.findViewById(R.id.image_product_second);
            TextView name_product_second = row_view.findViewById(R.id.name_product_second);
            TextView price_product_second = row_view.findViewById(R.id.price_product_second);
            FrameLayout scope_product_second = row_view.findViewById(R.id.scope_product_second);

            Product _second = product_sercond;
            scope_product_second.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, TrangCTSP.class);

                    intent.putExtra("product_id", _second.getId());

                    context.startActivity(intent);
                }
            });

            Glide.with(getContext()).
                    load(product_sercond.getImage())
                    .into(image_product_second);
            name_product_second.setText(product_sercond.getName());

            NumberFormat format2 = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            format2.setCurrency(Currency.getInstance("VND"));
            price_product_second.setText(format2.format(product_sercond.getPrice()));
//            price_product_second.setText(String.valueOf(product_sercond.getPrice()));
        }

        return row_view;
    }

    public int getCount() {
        return (products.size() + 1) / 2;
    }
}