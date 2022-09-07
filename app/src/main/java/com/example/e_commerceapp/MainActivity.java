package com.example.e_commerceapp;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {
    Intent intent;
    public int test_id;
    private SharedPreferences mprefs;
    private static  final String pref_name="PrefsFile";
    private SharedPreferences.Editor editor;
    private Boolean saveLogin;
    private String useremail,password;
    private Button ok;
    final DatabaseHelper db=new DatabaseHelper(this);
    private EditText u_email;
    private EditText  u_pass;
    private CheckBox remember_check;
    private Button btn;
    private CheckBox show_pass;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        u_email=(EditText)findViewById(R.id.Email);
        u_pass=(EditText)findViewById(R.id.Password);
        btn=(Button)findViewById(R.id.log_btn);
        remember_check=(CheckBox)findViewById(R.id.remember_me);
        show_pass=findViewById(R.id.show_pass);
        //sign up text
        TextView sign_up=findViewById(R.id.sign);
        //forget pass text
        TextView forget_pass=findViewById(R.id.forget_pass);
        //remeber me action
        mprefs = getSharedPreferences(pref_name, MODE_PRIVATE);
        sp=getSharedPreferences(pref_name,MODE_PRIVATE);
        if(sp.contains("useremail"))
        {
            String e=sp.getString("useremail","not_found");
            u_email.setText(e.toString());
        }
        if(sp.contains("userpass"))
        {
            String p=sp.getString("userpass","not_found");
            u_pass.setText(p.toString());
        }
        if(sp.contains("pref_check"))
        {
            boolean c=sp.getBoolean("pref_check",false);
            remember_check.setChecked(c);
        }
        //click on sign up
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this , Registration_Activity.class);
                startActivity(intent);
            }});
        //click on forget password
        forget_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this , Pass_activity.class);
                startActivity(intent);
            }});
        //log in and remember action
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=u_email.getText().toString().trim();
                String password=u_pass.getText().toString().trim();
                String test= db.check(email,password);
                //check if user name and pass is correct
                if(test.equals(password))
                {
                    //store data in prefs if remember me chicked
                   if( remember_check.isChecked())
                    {
                                boolean check=remember_check.isChecked();
                                editor = mprefs.edit();
                                editor.putString("useremail",u_email.getText().toString());
                                editor.putString("userpass",u_pass.getText().toString());
                                editor.putBoolean("pref_check",check);
                                editor.apply();
                    }
                    else {
                                mprefs.edit().clear().apply();
                    }
                    //if data is correct go to main activity
                    intent = new Intent(MainActivity.this , HomeActivity.class);
                    test_id=db.getid(email);
                    String val=Integer.toString(test_id);
                    intent.putExtra("id",val);
                    finish();
                    startActivity(intent);
                }
                else Toast.makeText(MainActivity.this,"failed to log in.....try again ",Toast.LENGTH_SHORT).show();
                u_pass.setText("");
            }
        });
        // show password checkbox
        show_pass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    //for show password
                    u_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else
                {
                    //for hide password
                    u_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }
}