package com.example.e_commerceapp;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.e_commerceapp.R;
import com.example.e_commerceapp.list_item;
import java.util.ArrayList;
//adapter of list in cart fragment
public class list_item_adapter extends ArrayAdapter<list_item> {
    private Context con;
    private int myros;
    public list_item_adapter(@NonNull Context context, int resource, @NonNull ArrayList<list_item> objects) {
        super(context, resource, objects);
        this.con=context;
        this.myros=resource;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=LayoutInflater.from(con);
        convertView=layoutInflater.inflate(myros,parent,false);
        ImageView img=convertView.findViewById(R.id.item_image);
        TextView name=convertView.findViewById(R.id.item_nam);
        TextView price=convertView.findViewById(R.id.item_cost);
        TextView qun=convertView.findViewById(R.id.item_qun);
        TextView date=convertView.findViewById(R.id.item_date);
        img.setImageResource(getItem(position).getImage());
        name.setText(getItem(position).getName());
        price.setText(getItem(position).getPrice().toString());
        qun.setText(String.valueOf(getItem(position).getQuantity()));
        date.setText(getItem(position).getDate());
        return  convertView;
    }
}
