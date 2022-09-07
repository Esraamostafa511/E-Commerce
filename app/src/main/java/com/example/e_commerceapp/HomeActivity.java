package com.example.e_commerceapp;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.material.tabs.TabLayout;

public class HomeActivity extends AppCompatActivity {
         Intent intent;
         MeowBottomNavigation bottomNavigation;
         TabLayout tabLayout;
         ViewPager2 pager2;
         String value;
    final DatabaseHelper db=new DatabaseHelper(this);
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get id from main activity pase
        Bundle extras=getIntent().getExtras();
        if(extras!=null)
        {
            value= extras.getString("id");
        }
        else {
            Toast.makeText(getApplicationContext(), "cant get id", Toast.LENGTH_SHORT).show();
        }
        // bottom navigation
        setContentView(R.layout.activity_home);
        //assign variable
        bottomNavigation=findViewById(R.id.bottom_navigation);
        //add menu item to bottom navigation
        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_baseline_shopping_cart_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.ic_baseline_home_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.ic_baseline_search_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(4,R.drawable.ic_baseline_person_pin_24));
        //on click on item from bottom navigation
        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment fragment=null;
                switch (item.getId())
                {
                    //cart fragment
                    case 1:
                        fragment= new CartFragment();
                        Bundle data3=new Bundle();
                        data3.putInt("id", Integer.parseInt(value));
                        fragment.setArguments(data3);
                        break;
                        //home fragment
                    case 2:
                        fragment=new HomeFragment();
                        Bundle data1=new Bundle();
                        data1.putInt("id", Integer.parseInt(value));
                        fragment.setArguments(data1);
                        break;
                        //search fragment
                    case 3:
                        fragment=new searchFragment();
                        Bundle data2=new Bundle();
                        data2.putInt("id", Integer.parseInt(value));
                        fragment.setArguments(data2);
                        break;
                        //profile fragment
                    case 4:
                        fragment=new ProfileFragment();
                        Bundle data=new Bundle();
                        data.putInt("id", Integer.parseInt(value));
                        fragment.setArguments(data);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + item.getId());
                }
                //load the choiced fragment
                loadFragment(fragment);
            }
        });
        //handle bottom navigation
        bottomNavigation.setCount(1,"1");
        bottomNavigation.show(2,true);
        //click on bottom navigation
        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
            }
        });
        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
            }
        });
    }
    //load the fragment function
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame,fragment).commit();
    }
//create option menu in application
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_items,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //items of option menu
        switch (item.getItemId())
        {
            //show alert in case logout
            case R.id.logout:
                new AlertDialog.Builder(HomeActivity.this)
                        .setTitle("Log Out")
                        .setMessage("Are You Sure You Want To Log Out?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                intent = new Intent(HomeActivity.this , MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return true;
                //start change pass activity
            case R.id.changepass:
                intent = new Intent(HomeActivity.this , change_passActivity.class);
                intent.putExtra("id",value);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}