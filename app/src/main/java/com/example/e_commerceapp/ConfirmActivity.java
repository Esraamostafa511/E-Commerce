package com.example.e_commerceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Date;

public class ConfirmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        final DatabaseHelper db=new DatabaseHelper(this);
        //get current date
        String date = java.text.DateFormat.getDateTimeInstance().format(new Date());
        //get data from intent
        Intent intent=getIntent();
        final int user_id=intent.getIntExtra("id",0);
        final double price=intent.getDoubleExtra("price",00);
        String address=intent.getStringExtra("address");
        String []user_data=db.getData(user_id);
        //proper email to user
        String message = "Our dear client,"+user_data[0]+"\nYou make an order at "+date+"  with total price  "+price+" to an address\n"+address;
        String subject = "Confirm order";
        String email=db.get_email(user_id);
        //Send Mail
        JavaMailApi javaMailAPI = new JavaMailApi(this,email,subject,message);
        javaMailAPI.execute();
        //put id in intent to go to home fragment
        intent=new Intent(ConfirmActivity.this,HomeActivity.class);
        intent.putExtra("id",String.valueOf(user_id));
        Toast.makeText(getApplicationContext(),"The Order has been confirmed and paid...Let's make a new order",Toast.LENGTH_SHORT).show();
        startActivity(intent);
        finish();
    }
}