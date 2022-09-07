package com.example.e_commerceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class Registration_Activity extends AppCompatActivity {
    DatePickerDialog.OnDateSetListener dateset;
    DatePickerDialog.OnDateSetListener onDateSetListener ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_);
        //access data in xml file
        final DatabaseHelper db=new DatabaseHelper(this);
        final EditText user_name=(EditText)findViewById(R.id.Name);
        final EditText user_eamil=(EditText)findViewById(R.id.Email);
        final EditText  user_birthdate=(EditText)findViewById(R.id.birth);
        final EditText  user_location=(EditText)findViewById(R.id.location);
        final EditText  user_password=(EditText)findViewById(R.id.Password);
        final EditText  user_phone=(EditText)findViewById(R.id.phone);
        // show calender to user
        final Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int days=calendar.get(Calendar.DAY_OF_MONTH);
        user_birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(Registration_Activity.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,onDateSetListener,year,month,days);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        //on click on date
        onDateSetListener= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //handle input
                if(year>=2020)
                {
                    Toast.makeText(getApplicationContext(), "Enter valid birth date ", Toast.LENGTH_SHORT).show();
                }
                //take the input in the text box
                else
                {
                    month=month+1;
                    String date=dayOfMonth +"/"+ month +"/"+ year;
                    user_birthdate.setText(date);
                }
            }
        };
        // show password checkbox
        final CheckBox show_pass=findViewById(R.id.show_pass);
        show_pass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    //for show password
                    user_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else
                {
                    // for hide password
                    user_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        //sign up button
        Button button=(Button)findViewById((R.id.sign_btn));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get date from edittexts
                String name = user_name.getText().toString().trim();
                String email = user_eamil.getText().toString().trim();
                String birthdate = user_birthdate.getText().toString();
                String location = user_location.getText().toString().trim();
                String phone=user_phone.getText().toString().trim();
                String passsword = user_password.getText().toString().trim();
                //check if edittext is null
                if (name.equals("") || email.equals("") || birthdate.equals("")||location.equals("")||phone.equals("")||passsword.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Please complenete data first...", Toast.LENGTH_SHORT).show();
                }
                else {
                    //check validation of phone number
                    if(phone.length()==11 && phone.charAt(0)=='0' && phone.charAt(1)=='1'){
                        if(Patterns.EMAIL_ADDRESS.matcher(email).matches())
                        {
                            //check email exist or not
                            if(db.chech_email_if_exist(email))
                            {
                                Toast.makeText(getApplicationContext(), "This Email Already Exist...Enter Another One ", Toast.LENGTH_SHORT).show();
                            }
                            //all data is correct
                            else
                            {
                                //store data in database and strat main activity
                                db.addUser(name,email,birthdate, location,phone, passsword );
                                Toast.makeText(getApplicationContext(), "Congratulation....Successfully registered ", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Registration_Activity.this, MainActivity.class);
                                finish();
                                startActivity(intent);
                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Enter validate Email ", Toast.LENGTH_SHORT).show();
                        }
                    }
                    //phone is incorrect
                    else
                    {
                        Toast.makeText(Registration_Activity.this,"enter valid phone number",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}