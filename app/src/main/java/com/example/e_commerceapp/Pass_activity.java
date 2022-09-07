package com.example.e_commerceapp;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;
public class Pass_activity extends AppCompatActivity {
    Intent intent;
    EditText email_txt;
    public int test_id;
    static int code=145279;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_activity);
        //generate random number
        final Random myRandom=new Random();
        email_txt=(EditText)findViewById(R.id.Email_txt);
        final DatabaseHelper db=new DatabaseHelper(this);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // log in button
        Button res_btn=(Button)findViewById(R.id.sendcode_btn);
        //click on log in button
        res_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String email=email_txt.getText().toString().trim();
                //check if edittext is null
                if(!email.isEmpty()) {
                    //send email with random code to user email
                    final String subject = "Confirm Password";
                    final String message = String.valueOf(myRandom.nextInt(100000));
                    final EditText input = new EditText(Pass_activity.this);
                    sendemail(email, subject, message);
                    input.setInputType(InputType.TYPE_CLASS_NUMBER);
                    //show alert to user and get the code
                    alertDialogBuilder.setView(input);
                    alertDialogBuilder.setTitle("ًWrite The Code");
                    alertDialogBuilder
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(final DialogInterface dialog, final int id) {
                                    // if this button is clicked, close the dialog box
                                    String user_code = input.getText().toString();
                                    test_id = db.getid(email);
                                    //check if user write the correct code
                                    if (user_code.equals(message)) {
                                        //start new pass activity to change password
                                        Toast.makeText(Pass_activity.this, "Successfully Done", Toast.LENGTH_SHORT).show();
                                        intent = new Intent(Pass_activity.this, Newpass_Activity.class);
                                        String val = Integer.toString(test_id);
                                        intent.putExtra("id", val);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(Pass_activity.this, "Code Is Incorrect", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    // show the message
                    alertDialog.show();
                }
            }
        });
    }
    //send email function
    private void sendemail(String email,String subject,String message)
    {
        JavaMailApi javaMailAPI = new JavaMailApi(this,email,subject,message);
        javaMailAPI.execute();
    }
}