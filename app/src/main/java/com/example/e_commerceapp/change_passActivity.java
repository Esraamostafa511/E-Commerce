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

public class change_passActivity extends AppCompatActivity {
    Intent intent;
    EditText new_pass;
    EditText c_new_pass;
    EditText old_pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        // log in button
        Button log_btn=(Button)findViewById(R.id.changepass_btn);
        new_pass=(EditText)findViewById(R.id.newpass_txt);
        c_new_pass=(EditText)findViewById(R.id.cnewpass_txt);
        old_pass=(EditText)findViewById(R.id.oldpass_txt);
        //get user id
        final DatabaseHelper db=new DatabaseHelper(this);
        // show password checkbox
        final CheckBox show_pass=findViewById(R.id.show_pass);
        show_pass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    //for show password
                    old_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    new_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    c_new_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else
                {
                    //for hide password
                    old_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    new_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    c_new_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        //click on log in button
        log_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=getIntent();
                String val=intent.getStringExtra("id");
                final int id=Integer.parseInt(val);
                //get old password
                final String old_password=db.get_password(id);
                if (!new_pass.getText().toString().equals("") && !c_new_pass.getText().toString().equals("") && !old_pass.getText().toString().equals(""))
                {
                    if (new_pass.getText().toString().equals(c_new_pass.getText().toString()) && old_password.equals(old_pass.getText().toString())) {
                        db.change_Pass(id, new_pass.getText().toString());
                        Toast.makeText(change_passActivity.this, "Password Changed successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(change_passActivity.this, "Password Not Matching", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(change_passActivity.this, "Complete data first", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}