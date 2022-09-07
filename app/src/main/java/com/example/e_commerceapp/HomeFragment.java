package com.example.e_commerceapp;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class HomeFragment extends Fragment {
      ImageView img_women;
    ImageView img_men;
    ImageView img_children;
    Intent intent;
      Button btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //get id from main activity pase
        final int user_id = getArguments().getInt("id");
        //get women image
        img_women=view.findViewById(R.id.women_img);
        //start activity women case women click
        img_women.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //put user id and activty id
                intent = new Intent(getActivity() , ShowProductsActivity.class);
                intent.putExtra("id",String.valueOf(user_id));
                intent.putExtra("activity_id",String.valueOf(1));
                startActivity(intent);
            }
        });
        //start activity men case men click
        img_men=view.findViewById(R.id.men_img);
        img_men.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //put user id and activty id
                intent = new Intent(getActivity() , ShowProductsActivity.class);
                intent.putExtra("id",String.valueOf(user_id));
                intent.putExtra("activity_id",String.valueOf(2));
                startActivity(intent);
                }
        });
        //start activity children case children click
        img_children=view.findViewById(R.id.children_img);
        img_children.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //put user id and activty id
                intent = new Intent(getActivity() , ShowProductsActivity.class);
                intent.putExtra("id",String.valueOf(user_id));
                intent.putExtra("activity_id",String.valueOf(3));
                startActivity(intent);
            }
        });
        return view;
    }
}