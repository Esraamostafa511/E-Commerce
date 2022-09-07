package com.example.e_commerceapp;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;
public class ProfileFragment extends Fragment {
    Intent intent;
    String []user_data=new String[4];
    int user_id;
    DatePickerDialog.OnDateSetListener onDateSetListener ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        final Fragment me=this;
        final DatabaseHelper database;
        if(getArguments()!=null) {
            database = new DatabaseHelper(getActivity());
            user_id = getArguments().getInt("id");
            user_data=database.getData(user_id);
            final TextView name_txt =  view.findViewById(R.id.Name);
            final TextView phone_txt =  view.findViewById(R.id.phone);
            final TextView location_txt =  view.findViewById(R.id.location);
            final TextView birth_txt = view.findViewById(R.id.birth);
            name_txt.setText(user_data[0]);
            phone_txt.setText(user_data[1]);
            location_txt.setText(user_data[2]);
            birth_txt.setText(user_data[3]);
            final String name =user_data[0];  //name
            final String address =user_data[2];   //address
            final String phone =user_data[1];   //phone
            final String birth =user_data[3];   //birthdate
            // show calender to user
            final Calendar calendar=Calendar.getInstance();
            final int year=calendar.get(Calendar.YEAR);
            final int month=calendar.get(Calendar.MONTH);
            final int days=calendar.get(Calendar.DAY_OF_MONTH);
            birth_txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerDialog datePickerDialog=new DatePickerDialog(getContext(),android.R.style.Theme_Holo_Light_Dialog_MinWidth,onDateSetListener,year,month,days);
                    datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    datePickerDialog.show();
                }
            });
            onDateSetListener= new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    if(year>=2020)
                    {
                        Toast.makeText(getContext(),"please Enter Valid birthdate",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        month=month+1;
                        String date=dayOfMonth +"/"+ month +"/"+ year;
                        birth_txt.setText(date);
                    }
                }
            };
            Button edit_btn=(Button)view.findViewById(R.id.edit_btn);
            edit_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    //if data is equal to data in database
                    if(name_txt.getText().toString().equals(name) && birth_txt.getText().toString().equals(birth) &&location_txt.getText().toString().equals(address)&&phone_txt.getText().toString().equals(phone))
                    {
                        Toast.makeText(getContext(),"please Enter new data to update",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        //get new data from edittexts
                        if(phone_txt.getText().toString().length()==11 && phone_txt.getText().toString().charAt(0)=='0' && phone_txt.getText().toString().charAt(1)=='1'){
                            //code update user data
                            database.updtaed(Integer.parseInt(String.valueOf(user_id)),name_txt.getText().toString(),phone_txt.getText().toString(),location_txt.getText().toString(),birth_txt.getText().toString());
                            Toast.makeText(getContext(),"Updated Successfully",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(getContext(),"enter valid phone number",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
        //user id is null
        else
        {
            Toast.makeText(getContext(),"id is null",Toast.LENGTH_SHORT).show();
        }
        return view;
    }
}