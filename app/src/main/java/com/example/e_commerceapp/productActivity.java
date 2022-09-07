package com.example.e_commerceapp;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.e_commerceapp.R;
import com.example.e_commerceapp.DatabaseHelper;
import com.example.e_commerceapp.CartFragment;

import java.util.Date;
public class productActivity extends AppCompatActivity {
    Button inc,dec,add_to_order;
    TextView quntity, item_price,item_name,item_status;
    Intent intent;
    ImageView item_img;
    int product_id=0;
    int category_id=0;
    int quntity_num=0;
    int user_id=0;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        inc=(Button)findViewById(R.id.inc);
        dec=(Button)findViewById(R.id.dic);
        add_to_order=(Button)findViewById(R.id.add_tocart_btn);
        quntity=(TextView)findViewById(R.id.quntity);
        item_img=(ImageView)findViewById(R.id.item_image);
        item_name=(TextView)findViewById(R.id.item_nam);
        item_price=(TextView)findViewById(R.id.item_cost);
        item_status=(TextView)findViewById(R.id.status_of_item);
        db=new DatabaseHelper(this);
        //get data about product from intent
        intent=this.getIntent();
        final String name=intent.getStringExtra("name");
        final double price=intent.getDoubleExtra("price",0.0);
        final int img=intent.getIntExtra("img",R.drawable.blouse);
        product_id=intent.getIntExtra("pro_id",1);
        category_id=intent.getIntExtra("cat_id",1);
        user_id=intent.getIntExtra("user_id",0);
        //get quntity of product from database
        quntity_num=db.get_new_item_quantity(product_id);
        item_img.setImageResource(img);
        item_name.setText(name);
        item_price.setText(String.valueOf(price));
        //check status of product
        if(quntity_num==0)
        {
                item_status.setText("Sold Out");
                quntity.setText(String.valueOf(0));
        }
        else {
            //increment quntity
            inc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int q = Integer.parseInt(quntity.getText().toString());
                    if (q < quntity_num) {
                        q = q + 1;
                        String res = String.valueOf(q);
                        quntity.setText(res.toString());
                    }
                }
            });
            //deccrement quntity
            dec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int q = Integer.parseInt(quntity.getText().toString());
                    if (q > 0) {
                        q = q - 1;
                        String res = String.valueOf(q);
                        quntity.setText(res.toString());
                    }
                }
            });
            //add to cart button
            add_to_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int qun = Integer.parseInt(quntity.getText().toString());
                    if (qun != 0) {
                        //get date of lap
                        String curr_data = java.text.DateFormat.getDateTimeInstance().format(new Date());
                        //check if item already in the shopping cart of the user
                        boolean find = db.check_item_if_exist_inorder(user_id, product_id);
                        int x = 10;
                        if (find == true) {

                                //get quntity of product if item already exit in cart
                                int old_q_inorder = db.get_quantity_or_item_inuser_order(product_id, user_id);
                                //update new quntity of item to sotre
                                int q = quntity_num - qun + old_q_inorder;
                                //update new quntity of item in database
                                db.update_product_quantity(product_id, q);
                                //add item to database if quntity !=0
                                x = db.add_item_toorder(user_id, product_id, qun, name, price, img, category_id, curr_data);
                        } else if (find == false) {
                            //update new quntity of item in database
                            int q = quntity_num - qun;
                            db.update_product_quantity(product_id, q);
                            //add item to database
                            x = db.add_item_toorder(user_id, product_id, qun, name, price, img, category_id, curr_data);
                        }
                        //Toast.makeText(getApplicationContext(),String.valueOf(old_q_inorder),Toast.LENGTH_SHORT).show();
                        final String come_from = intent.getStringExtra("come_from");
                        Fragment fragment = new CartFragment();
                        Bundle data3 = new Bundle();
                        data3.putInt("id", user_id);
                        fragment.setArguments(data3);
                        //exist and edited in cart
                        if (x == 0 && come_from.equals("cartfragment")) {
                            Toast.makeText(getApplicationContext(), "Item Edited In your shopping cart", Toast.LENGTH_SHORT).show();
                        }
                        //exist and edited in fragment
                        else if (x == 0 && come_from.equals("activity")) {
                            Toast.makeText(getApplicationContext(), "Item Edited In your shopping cart", Toast.LENGTH_SHORT).show();
                        }
                        //not exist
                        else {
                            Toast.makeText(getApplicationContext(), "Item Added to your shopping cart", Toast.LENGTH_SHORT).show();
                        }
                        //start activity product again to refresh
                        finish();
                        startActivity(intent);
                    }
                }
            });
        }
    }
}