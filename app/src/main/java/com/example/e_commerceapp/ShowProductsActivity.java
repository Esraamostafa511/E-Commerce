package com.example.e_commerceapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ShowProductsActivity extends AppCompatActivity {
    Intent intent;
    GridView gridView;
    final DatabaseHelper db=new DatabaseHelper(this);
    //data in products activity
    ArrayList<String>names=new ArrayList<String>();
    ArrayList<Integer>imgs=new ArrayList<Integer>();
    ArrayList<Double>prices=new ArrayList<Double>();
    ArrayList<Integer>pro_id=new ArrayList<Integer>();
    ArrayList<Integer>qun=new ArrayList<Integer>();
    String user_id;
    String activity_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_women);
        Bundle extras=getIntent().getExtras();
        //get user id and activity id
        if(extras!=null)
        {
            user_id= extras.getString("id");
            activity_id=extras.getString("activity_id");
        }
        else {
            Toast.makeText(getApplicationContext(), "cant get id", Toast.LENGTH_SHORT).show();
        }
        //get data from table
        Cursor cursor;
        if(Integer.parseInt(activity_id)==1)
        {
            cursor=db.getWomenCategory_data();
            if(cursor.getCount()==0)
            {
                Toast.makeText(getApplicationContext(),"no data to show",Toast.LENGTH_LONG).show();
            }
        }
        else if(Integer.parseInt(activity_id)==2)
        {
            cursor=db.getMenCategory_data();
            if(cursor.getCount()==0)
            {
                Toast.makeText(getApplicationContext(),"no data to show",Toast.LENGTH_LONG).show();
            }
        }
       else
        {
            cursor=db.getChildrenCategory_data();
            if(cursor.getCount()==0)
            {
                Toast.makeText(getApplicationContext(),"no data to show",Toast.LENGTH_LONG).show();
            }
        }
       //stroe data in the array lists
       while (cursor.moveToNext())
       {
                pro_id.add(Integer.parseInt(cursor.getString(0)));
                imgs.add(Integer.parseInt(cursor.getString(2)));
                names.add(cursor.getString(1));
                prices.add(Double.parseDouble(cursor.getString(3)));
                qun.add(Integer.parseInt(cursor.getString(4)));
       }
       //get grid view from xml and put products in it
        gridView=findViewById(R.id.gridView);
       //send array lists to gridview adapter
        GridAdapter gridAdapter = new GridAdapter(ShowProductsActivity.this,names,prices,imgs);
        gridView.setAdapter(gridAdapter);
        //click on item in grid view
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent=new Intent(ShowProductsActivity.this, productActivity.class);
                //send data to product activity and start product activity
                intent.putExtra("name",names.get(position));
                intent.putExtra("img",imgs.get(position));
                intent.putExtra("price",prices.get(position));
                intent.putExtra("pro_id",pro_id.get(position));
                intent.putExtra("cat_id",1);
                intent.putExtra("qun",qun.get(position));
                intent.putExtra("user_id",Integer.parseInt(user_id));
                intent.putExtra("come_from","activity");
                startActivity(intent);
            }
        });

    }
}