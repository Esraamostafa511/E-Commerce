package com.example.e_commerceapp;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
public class searchFragment extends Fragment {
    int user_id;
    final int Voicecode=1;
    ImageView parcode;
    private ArrayAdapter<String> arr;
    String search_txt;
    EditText txt;
    DatabaseHelper database;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_search, container, false);
        user_id = getArguments().getInt("id");
        //to make keyboard avialable
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        database=new DatabaseHelper(getContext());
        final ListView list=view.findViewById(R.id.search_list);
        txt=(EditText) view.findViewById(R.id.get_text);
        ImageView voice=(ImageView)view.findViewById(R.id.search_by_voice);
        parcode=(ImageView)view.findViewById(R.id.search_by_parcode);
        search_txt=txt.getText().toString();
        final ArrayList<String> list_arr=new ArrayList<String>();
        arr=new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_list_item_1,list_arr);
        //get all products in a list
        final Cursor cursor = database.search_by_text();
        if (cursor.getCount() == 0) {
            Toast.makeText(getContext(), "no items", Toast.LENGTH_SHORT).show();
        } else {
            do {
                list_arr.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }
        //search by text
        txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            //on text is changed from null to value
            @Override
            public void onTextChanged(final CharSequence s, int start, int before,
                                      int count) {
                //filter data with value in edit text
                (searchFragment.this).arr.getFilter().filter(s);
                list.setAdapter(arr);
                arr.notifyDataSetChanged();
            }
            @Override
            public void afterTextChanged(final Editable s) {
            }
        });
        //search by voice
        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                try {
                    startActivityForResult(intent,Voicecode);
                }catch (ActivityNotFoundException ex)
                {
                    Toast.makeText(getContext(), "Your Device Not Support Voice Speech", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //search by barcode
        parcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //put user id and start activity scann
                Intent intent=new Intent(getContext(), scann.class);
                intent.putExtra("id",user_id);
                startActivity(intent);
            }
        });
        //click on item is the list=> go to prodcut activity with id of selected product
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView item=(TextView)view;
                Cursor cursor=database.get_product_data(item.getText().toString());
                Intent intent=new Intent(getActivity(), productActivity.class);
                //send data to product activity
                intent.putExtra("pro_id",Integer.parseInt(cursor.getString(0)));
                intent.putExtra("name",cursor.getString(1));
                intent.putExtra("img",Integer.parseInt(cursor.getString(2)));
                intent.putExtra("price",Double.parseDouble(cursor.getString(3)));
                intent.putExtra("qun",Integer.parseInt(cursor.getString(5)));
                intent.putExtra("user_id",user_id);
                intent.putExtra("come_from","search");
                startActivity(intent);
            }
        });
        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==Voicecode&&resultCode==getActivity().RESULT_OK);
        {
            //convert from speech to text
            try {
                ArrayList<String> text=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                txt.setText(text.get(0));
            }catch (RuntimeException ex)
            {
                return;
            }
        }
    }
}