package com.example.e_commerceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class Newpass_Activity extends AppCompatActivity {
    Intent intent;
    EditText new_pass;
    EditText c_new_pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpass_);
        // log in button
        Button log_btn=(Button)findViewById(R.id.changepass_btn);
        new_pass=(EditText)findViewById(R.id.newpass_txt);
        c_new_pass=(EditText)findViewById(R.id.cnewpass_txt);
        final DatabaseHelper db=new DatabaseHelper(this);
        intent=getIntent();
        // show password checkbox
        final CheckBox show_pass=findViewById(R.id.show_pass);
        show_pass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    //for show password
                    new_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    c_new_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else
                {
                    //for hide password
                    new_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    c_new_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        //click on log in button
        log_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check if two edittext is matching
                if(new_pass.getText().toString().equals(c_new_pass.getText().toString()))
                {
                    String val=intent.getStringExtra("id");
                    int id=Integer.parseInt(val);
                    //update password in database
                    db.change_Pass(id,new_pass.getText().toString());
                    Toast.makeText(Newpass_Activity.this,"Password Changed successfully",Toast.LENGTH_SHORT).show();
                    intent = new Intent(Newpass_Activity.this , MainActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(Newpass_Activity.this,"Password Not Matching",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}