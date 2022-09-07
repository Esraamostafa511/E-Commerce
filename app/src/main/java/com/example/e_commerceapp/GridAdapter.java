package com.example.e_commerceapp;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.e_commerceapp.R;
import java.util.ArrayList;
public class GridAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> item_name=new ArrayList<String>();
    ArrayList<Double> item_price=new ArrayList<Double>();
    ArrayList<Integer> image=new ArrayList<Integer>();
    LayoutInflater inflater;
    public GridAdapter(Context context, ArrayList<String> item_name, ArrayList<Double> item_price, ArrayList<Integer> image) {
        this.context = context;
        this.item_name = item_name;
        this.item_price = item_price;
        this.image = image;
    }
    @Override
    public int getCount() {
        return item_name.size();
    }
    @Override
    public Object getItem(int position) {
        return null;
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null){
            convertView = inflater.inflate(R.layout.grid_item,null);
        }
        //set data of products in the grid view
        ImageView imageView = convertView.findViewById(R.id.grid_image);
        TextView nameview = convertView.findViewById(R.id.item_name);
        TextView priceview = convertView.findViewById(R.id.item_price);
        imageView.setImageResource(image.get(position));
        nameview.setText(item_name.get(position));
        priceview.setText(String.valueOf(item_price.get(position)));
        return convertView;
    }
}