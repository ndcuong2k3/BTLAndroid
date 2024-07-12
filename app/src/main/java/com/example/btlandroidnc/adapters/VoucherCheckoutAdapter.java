package com.example.btlandroidnc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.btlandroidnc.Model.Voucher;
import com.example.btlandroidnc.R;
import com.example.btlandroidnc.TrangMuaHang;

import java.util.List;


public class VoucherCheckoutAdapter extends ArrayAdapter<Voucher> {
    private Context context;

    private List<Voucher> vouchers;

    public Integer position_selected = null;

    TrangMuaHang.HandleCheck handle;

    public VoucherCheckoutAdapter(Context context, List<Voucher> vouchers, TrangMuaHang.HandleCheck handle) {
        super(context, 0, vouchers);

        this.context = context;
        this.vouchers = vouchers;
        this.handle = handle;
    }

    public View getView(int position, View convert_view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View item_view = inflater.inflate(R.layout.item_voucher_checkout, parent, false);

        Voucher item = vouchers.get(position);

        TextView title = item_view.findViewById(R.id.title);
        CheckBox check = item_view.findViewById(R.id.check);
        TextView value = item_view.findViewById(R.id.value_voucher);


        title.setText(item.getTitle());
        value.setText(Float.toString(item.getValue()) + " đ");

        if (position_selected != null && position_selected.equals(position)) {
            check.setChecked(true);
        }

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handle.handle_check(position, position_selected);
            }
        });

        return item_view;
    }

}
