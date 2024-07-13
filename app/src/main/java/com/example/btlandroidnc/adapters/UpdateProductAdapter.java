package com.example.btlandroidnc.adapters;


import android.content.Context;
import android.content.Intent;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.btlandroidnc.Model.Product;
import com.example.btlandroidnc.R;
import com.example.btlandroidnc.TrangThemSPMoi;

import java.util.List;

public class UpdateProductAdapter extends ArrayAdapter<Product> {

    private List<Product> products;

    private Context context;

    SparseArray<String> id_products_selected = new SparseArray<>();

    public SparseArray<String> get_id_products_selected() {
        return id_products_selected;
    }

    public void set_id_products_selected(SparseArray<String> id_products_selected) {
        this.id_products_selected = id_products_selected;
    }

    public UpdateProductAdapter(Context context, List<Product> products) {
        super(context, 0, products);

        this.products = products;
        this.context = context;
    }

    @Override
    public View getView(int position, View convert_view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View item_view = inflater.inflate(R.layout.item_sp_admin, parent, false);

        Product item = products.get(position);

        ImageView image_item = item_view.findViewById(R.id.anh);
        TextView description_item = item_view.findViewById(R.id.product_description);
        Button btn_update_item = item_view.findViewById(R.id.btn_update_product);
        CheckBox check_item = item_view.findViewById(R.id.check);

        Glide.with(getContext()).
                load(item.getImage())
                .into(image_item);

        description_item.setText(item.getName());

        btn_update_item.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, TrangThemSPMoi.class);

                i.putExtra("product_id", item.getId());

                context.startActivity(i);
            }
        }));

        check_item.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                id_products_selected.put(position, item.getId());
            }
            else {
                id_products_selected.remove(position);
            }
        });

        return item_view;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
