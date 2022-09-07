package com.example.e_commerceapp;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
public class scann extends AppCompatActivity {
    Button btn;
    public static String scann;
    DatabaseHelper database;
    int user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scann);
        database=new DatabaseHelper(getApplicationContext());
        Bundle extras = getIntent().getExtras();
        user_id= extras.getInt("id",0);
        btn=findViewById(R.id.scan);
        //click on scann button
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //set the text of scanning result
                IntentIntegrator intentIntegrator=new IntentIntegrator(scann.this);
                intentIntegrator.setCaptureActivity(captureImage.class);
                intentIntegrator.setOrientationLocked(false);
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                intentIntegrator.setPrompt("Scanning Code");
                intentIntegrator.initiateScan();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                // set title
                alertDialogBuilder.setTitle("Scanning Result");
                // set dialog message
                alertDialogBuilder
                        .setMessage(result.getContents())
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, final int id) {
                                // if this button is clicked, close the dialog box
                                dialog.cancel();
                                scann=result.getContents();
                                Cursor cursor=database.get_product_data(scann);
                                if(cursor==null)
                                {
                                    Toast.makeText(getApplicationContext(),"No items",Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Intent intent = new Intent(scann.this, productActivity.class);
                                    //send data to product activity
                                    intent.putExtra("pro_id", Integer.parseInt(cursor.getString(0)));
                                    intent.putExtra("name", cursor.getString(1));
                                    intent.putExtra("img", Integer.parseInt(cursor.getString(2)));
                                    intent.putExtra("price", Double.parseDouble(cursor.getString(3)));
                                    intent.putExtra("qun", Integer.parseInt(cursor.getString(5)));
                                    intent.putExtra("user_id", user_id);
                                    intent.putExtra("come_from", "search");
                                    startActivity(intent);
                                }
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show the message
                alertDialog.show();
            }
        }
    }
}