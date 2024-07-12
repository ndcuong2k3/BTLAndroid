package com.example.btlandroidnc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.btlandroidnc.Model.User;
import com.example.btlandroidnc.R;

import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {
    private Context mContext;
    private int mResource;
    public UserAdapter(@NonNull Context context, int resource, @NonNull List<User> objects) {
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

        TextView textViewName = itemView.findViewById(R.id.txtItemKH);
        User user = getItem(position);

        if (user != null) {
            textViewName.setText(user.getEmail());
            // textViewName.setText("user.getName()");
        }

        return itemView;
    }
}
