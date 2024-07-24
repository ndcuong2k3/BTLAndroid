package com.example.btlandroidnc.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.btlandroidnc.Model.Invoice;
import com.example.btlandroidnc.R;
import com.example.btlandroidnc.TrangDuyetMotDonHang;
import com.example.btlandroidnc.TrangKHXemCTHoaDon;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class InvoiceAdapter extends ArrayAdapter<Invoice> {
    private Context mContext;
    public ArrayList<Boolean> isCheckedList =new ArrayList<>();;
    private int mResource;
    public InvoiceAdapter(@NonNull Context context, int resource, @NonNull List<Invoice> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource=resource;
        //  isCheckedList =
        for (int i = 0; i < 100; i++) {
            isCheckedList.add(false); // Khởi tạo tất cả các checkbox là không được chọn ban đầu
        }
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            itemView = inflater.inflate(mResource, parent, false);

        }

        if(mResource == R.layout.item_ad_duyet){

            TextView textViewName = itemView.findViewById(R.id.invoice_description);
            TextView tv = itemView.findViewById(R.id.ngaydat);

            Invoice  invoice = getItem(position);

            if (invoice != null) {
                NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                format.setCurrency(Currency.getInstance("VND"));
                textViewName.setText(format.format(invoice.getTotal()));
                tv.setText(String.valueOf(invoice.getCreate_at().getDate())+"/"+
                                String.valueOf(invoice.getCreate_at().getMonth() + 1));
//                textViewName.setText(String.valueOf( invoice.getTotal()));
                // textViewName.setText("user.getName()");
            };
            CheckBox checkBox = itemView.findViewById(R.id.check);
            checkBox.setChecked(isCheckedList.get(position));
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isCheckedList.set(position,checkBox.isChecked());
                    Toast.makeText(mContext,String.valueOf( isCheckedList.size()), Toast.LENGTH_SHORT).show();
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(mContext, "hfsgv", Toast.LENGTH_SHORT).show();
                    Intent i =new Intent(mContext, TrangDuyetMotDonHang.class);
                    i.putExtra("id",invoice.getId());
                    if (mContext != null) {
                        mContext.startActivity(i);
                    } else {
                        Toast.makeText(getContext(), "Context is null", Toast.LENGTH_SHORT).show();
                    }

                }
            });}
        if(mResource == R.layout.item_kh_xemdhdamua) {
            TextView textViewTongTien = itemView.findViewById(R.id.tongtien);
            TextView textViewNgayDat = itemView.findViewById(R.id.ngaydat);

            Invoice  invoice = getItem(position);

            if (invoice != null) {
                NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                format.setCurrency(Currency.getInstance("VND"));
                textViewTongTien.setText(format.format(invoice.getTotal()));
//                textViewTongTien.setText(String.valueOf( invoice.getTotal()));
                textViewNgayDat.setText(String.valueOf(invoice.getCreate_at().getDate())+"/"+
                        String.valueOf(invoice.getCreate_at().getMonth() + 1)
                );

            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(mContext, "hfsgv", Toast.LENGTH_SHORT).show();
                    Intent i =new Intent(mContext, TrangKHXemCTHoaDon.class);
                    i.putExtra("id",invoice.getId());
                    if (mContext != null) {
                        mContext.startActivity(i);
                    } else {
                        Toast.makeText(getContext(), "Context is null", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }


        return itemView;
    }
    public void checkAll(boolean isChecked,int a) {
        for (int i = 0; i < a; i++) {
            isCheckedList.set(i, isChecked);
        }
        notifyDataSetChanged(); // Cập nhật giao diện người dùng
    }
}
