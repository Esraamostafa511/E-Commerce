package com.example.e_commerceapp;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
public class CartFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        final ArrayList<list_item> list_arr=new ArrayList<list_item>();
        final DatabaseHelper db=new DatabaseHelper(getContext());
        final int user_id = getArguments().getInt("id");
        final ArrayList<String>names=new ArrayList<String>();
        final ArrayList<Integer>imgs=new ArrayList<Integer>();
        final ArrayList<Double>prices=new ArrayList<Double>();
        final ArrayList<String>date=new ArrayList<String>();
        final ArrayList<Integer>qun=new ArrayList<Integer>();
        final ArrayList<Integer>order_id=new ArrayList<Integer>();
        final ArrayList<Integer>product_id=new ArrayList<Integer>();
        ListView list=view.findViewById(R.id.listView);
        final TextView total_price_order=view.findViewById(R.id.total_prc);
        Button buy=view.findViewById(R.id.buy_btn);
        double total_price=0;
        //get listview
        final list_item_adapter adapter=new list_item_adapter(getContext(),R.layout.listitem_item,list_arr);
        //set listview adapter
        list.setAdapter(adapter);
        //get user orders from database
        Cursor cursor=db.get_user_orders(user_id);
        //user has orders in database
        if(cursor.getCount()!=0)
        {
            int i=0;
            //get data about all orders to this user
            while (cursor.moveToNext())
            {
                product_id.add(Integer.parseInt(cursor.getString(8)));
                order_id.add(Integer.parseInt(cursor.getString(0)));
                imgs.add(Integer.parseInt(cursor.getString(6)));
                names.add(cursor.getString(3));
                prices.add(Double.parseDouble(cursor.getString(4)));
                qun.add(Integer.parseInt(cursor.getString(7)));
                date.add(cursor.getString(9));
                list_arr.add(new list_item(product_id.get(i),imgs.get(i),names.get(i),Double.parseDouble(prices.get(i).toString()),Integer.parseInt(qun.get(i).toString()),date.get(i)));
                //calculate the total price
                total_price+=qun.get(i)*prices.get(i);
                i++;
            }
            //set total price
            total_price_order.setText(String.valueOf(total_price));
        }
        //buy order
        final double finalTotal_price = total_price;
        //buy button
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check that exist orders
                if(!adapter.isEmpty())
                {
                    //get data of buy order
                    String curr_date = java.text.DateFormat.getDateTimeInstance().format(new Date());
                    final Intent intent = new Intent(getActivity(), MapsActivity.class);
                    intent.putExtra("id",user_id);
                    intent.putExtra("price",finalTotal_price);
                    intent.putExtra("date",curr_date);
                    //start actinity maps
                    startActivity(intent);
                }
            }
        });
        //click on item in list
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), productActivity.class);
                //send data to product activity
                intent.putExtra("name",names.get(position));
                intent.putExtra("img",imgs.get(position));
                intent.putExtra("price",prices.get(position));
                intent.putExtra("pro_id",product_id.get(position));
                intent.putExtra("cat_id",2);
                intent.putExtra("qun",qun.get(position));
                intent.putExtra("user_id",user_id);
                intent.putExtra("come_from","cartfragment");
                startActivity(intent);
                //refresh fragment
                getFragmentManager().beginTransaction().detach(CartFragment.this).attach(CartFragment.this).commit();
            }
        });
        //lon click on item to delete item
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                //show alter to ensure to delete
                new AlertDialog.Builder(getContext()).setIcon(android.R.drawable.ic_delete).setTitle("Delete Item From Order")
                        .setMessage("You Want To Delete This Item?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //remove item from list
                                list_arr.remove(position);
                                adapter.notifyDataSetChanged();
                                //get id of product to delete from database
                                int order=order_id.get(position);
                                //delete order from database
                                db.delete_order(order);
                                //db.delete_item_from_user_order(user_id,product_id.get(position));
                                //update total price of order in cart
                                double new_price=Double.parseDouble(total_price_order.getText().toString());
                                total_price_order.setText(String.valueOf(new_price-prices.get(position)));
                                //get current quntity of product in database
                                int curr_q=db.get_new_item_quantity(product_id.get(position));
                                //add current quntity and quntity in database to update quntity of product
                                int new_q=curr_q+qun.get(position);
                                //update quntity of product
                                db.update_product_quantity(product_id.get(position),new_q);
                                //refresh the fragment
                                getFragmentManager().beginTransaction().detach(CartFragment.this).attach(CartFragment.this).commit();
                            }
                        })
                        .setNegativeButton("No",null).show();
                return true;
            }
        });
        return view;
    }
}