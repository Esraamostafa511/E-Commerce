package com.example.e_commerceapp;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.e_commerceapp.R;

import java.util.Date;
import java.util.WeakHashMap;
public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper( Context con ) {
        super(con,"e-commerce.db",null,3);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // user table
        db.execSQL("CREATE TABLE Customers (ID INTEGER PRIMARY KEY  AUTOINCREMENT , name TEXT not null, " +
                "email TEXT not null ,birthdate TEXT not null, location TEXT not null,phone TEXT not null,password TEXT not null) ");
        //category table
        db.execSQL("create table Category (id integer primary key  autoincrement , name text not null )");
        //product table
        db.execSQL("create table Product (id integer primary key autoincrement, name text not null ,image blob ," +
                "price real not null , quantity integer not null , cate_id integer not null ," +
                "foreign key (cate_id)references category (id))");
        //shopping cart table
        db.execSQL("create table ShoppingCart (id integer primary key  autoincrement ,user_id integer not null,credit_card text not null," +
                "Order_price real not null,Order_address text not null,Order_date date not null)");
        //details of order table
        db.execSQL("create table User_Order (order_id integer primary key autoincrement ,cart_id integer not null , customer_id integer not null," +
                "product_name text not null,product_price real not null,product_category integer not null,product_image integer not null,product_quntity integer not null," +
                "product_id integer not null,purchase_data Date not null)");
        //women category
        db.execSQL("insert into Product values (1,'Boyfriend Jeans',2131231130,250,10,1)");
        db.execSQL("insert into Product values (2,'Green Blouse',2131230816,450,15,1)");
        db.execSQL("insert into Product values (3,'Green Jacket',2131231129,350,13,1)");
        db.execSQL("insert into Product values (4,'Cardigan',2131230833,200,14,1)");
        db.execSQL("insert into Product values (5,'White SweatShirt',2131231245,220,20,1)");
        db.execSQL("insert into Product values (6,'Trouser',2131231249,260,25,1)");
        db.execSQL("insert into Product values (7,'Purple Suit',2131231244,600,13,1)");
        db.execSQL("insert into Product values (8,'Purple Blouse',2131230817,210,30,1)");
        db.execSQL("insert into Product values (9,'Simon Blouse',2131230818,220,28,1)");
        db.execSQL("insert into Product values (10,'Blouse',2131230819,250,16,1)");
        //men category
        db.execSQL("insert into Product values (11,'Blue Jeans',2131231132,250,10,2)");
        db.execSQL("insert into Product values (12,'Sweatpants',2131231133,150,15,2)");
        db.execSQL("insert into Product values (13,'Plazer',2131231134,550,13,2)");
        db.execSQL("insert into Product values (14,'Jacket',2131231131,650,14,2)");
        db.execSQL("insert into Product values (15,'SweatShirt',2131231136,200,12,2)");
        //children category
        db.execSQL("insert into Product values (16,'Cat Jacket',2131230830,250,10,3)");
        db.execSQL("insert into Product values (17,'Dress',2131230829,370,15,3)");
        db.execSQL("insert into Product values (18,'Pajama',2131230831,170,13,3)");
        db.execSQL("insert into Product values (19,'Pattot Pajama',2131231191,120,14,3)");
        db.execSQL("insert into Product values (20,'Salopet',2131230832,100,20,3)");
        //categories
        db.execSQL("insert into Category values (1,'Women')");
        db.execSQL("insert into Category values (2,'Men')");
        db.execSQL("insert into Category values (3,'Children')");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
       db.execSQL("drop table if exists Customers");
        db.execSQL("drop table if exists Product");
        db.execSQL("drop table if exists Category");
        db.execSQL("drop table if exists ShoppingCart");
        db.execSQL("drop table if exists User_Order");
        onCreate(db);

    }
    public void addUser(String name, String email, String birthdate, String location, String phone,String password) {
        SQLiteDatabase db_write = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("email", email);
        contentValues.put("birthdate", birthdate);
        contentValues.put("location", location);
        contentValues.put("phone", phone);
        contentValues.put("password", password);
        db_write.insert("Customers", null, contentValues);
        db_write.close();
    }
    public boolean chech_email_if_exist(String email) {
        SQLiteDatabase db_read = this.getReadableDatabase();
        Cursor cursor =db_read.rawQuery("select email from Customers",null);
        boolean find=false;
        if(cursor.moveToFirst()){
            do{
                String a=cursor.getString(0);
                if(a.equals(email)){
                   find =true;
                   return find;
                }
            }while (cursor.moveToNext());
            return find;
        }
        return find;
    }
    public String check(String email , String pass ){
        SQLiteDatabase db_read = this.getReadableDatabase();
        String quary ="select email,password from Customers";
        Cursor cursor =db_read.rawQuery(quary,null);
        String b="not found";
        if(cursor.moveToFirst()){
            do{
                if(cursor.getString(0).equals(email)){
                    b=cursor.getString(1);
                    break;
                }
            }while (cursor.moveToNext());
        }
        return b;
    }
    public int getid(String email) {
        SQLiteDatabase db_read = this.getReadableDatabase();
        String quary ="select ID,email from Customers ";
        Cursor cursor =db_read.rawQuery(quary,null);
        String a;
        int b=0;
        if(cursor.moveToFirst()){
            do{
                a=cursor.getString(1);
                if(a.equals(email))
                {
                    b=cursor.getInt(0);
                    break;
                }
            }while (cursor.moveToNext());
        }
        return b;
    }
    public final String[] getData(int id) {
        SQLiteDatabase db_read = this.getReadableDatabase();
        String quary ="select * from Customers";
        Cursor cursor =db_read.rawQuery(quary,null);
        int a;
        String [] arr=new String [4];  //name,email,location,pass
        if(cursor.moveToFirst()){
            do{
                a=cursor.getInt(0);
                if(a==id)
                {
                    arr[0]=cursor.getString(1);
                    arr[1]=cursor.getString(5);
                    arr[2]=cursor.getString(4);
                    arr[3]=cursor.getString(3);
                    break;
                }
            }while (cursor.moveToNext());

        }
        return arr;
    }
    public void updtaed(int id,String name,String phone,String location,String birthdate ){
        SQLiteDatabase db_read = this.getReadableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("name",name);
        contentValues.put("phone",phone);
        contentValues.put("location",location);
        contentValues.put("birthdate",birthdate);
        db_read.update("Customers",contentValues,"ID like ?",new String[]{String.valueOf(id)});
        db_read.close();
    }
    public final void change_Pass(int id,String password) {
        SQLiteDatabase db_read = this.getReadableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("password",password);
        db_read.update("Customers",contentValues,"ID like ?",new String[]{String.valueOf(id)});
        db_read.close();
    }
    public String get_password(int user_id) {
        SQLiteDatabase db_read = this.getReadableDatabase();
        String pass=String.valueOf(user_id);
        Cursor cursor =db_read.rawQuery("select password from Customers where ID like ? ",new String[]{pass});
       if(cursor!= null)
       {
           cursor.moveToFirst();
           return cursor.getString(0);
       }
        return null;
    }
    public  Cursor getWomenCategory_data() {
        SQLiteDatabase db_read = this.getReadableDatabase();
        String quary ="select * from Product where cate_id='1'";
        Cursor cursor =db_read.rawQuery(quary,null);
        return cursor;
    }
    public  Cursor getMenCategory_data() {
        SQLiteDatabase db_read = this.getReadableDatabase();
        String quary ="select * from Product where cate_id='2'";
        Cursor cursor =db_read.rawQuery(quary,null);
        return cursor;
    }
    public  Cursor getChildrenCategory_data() {
        SQLiteDatabase db_read = this.getReadableDatabase();
        String quary ="select * from Product where cate_id='3'";
        Cursor cursor =db_read.rawQuery(quary,null);
        return cursor;
    }
    public int add_item_toorder(int user_id, int pro_id, int quantity, String p_name, double p_price, int p_img, int p_cat, String date) {
        SQLiteDatabase db_write = this.getWritableDatabase();
        Cursor cursor=db_write.rawQuery("select order_id from User_Order where product_id like ? and customer_id like ?",new String[]{String.valueOf(pro_id),String.valueOf(user_id)});
        if(cursor.moveToFirst())
        {
            //existed item
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues=new ContentValues();
            contentValues.put("product_quntity",quantity);
            contentValues.put("purchase_data", date);
            db.update("User_Order",contentValues,"product_id like ?",new String[]{String.valueOf(pro_id)});
            db.close();
            return 0;
        }
        else {
            //new item
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("cart_id", user_id);
            contentValues.put("customer_id", user_id);
            contentValues.put("product_name", p_name);
            contentValues.put("product_image", p_img);
            contentValues.put("product_price", p_price);
            contentValues.put("product_quntity", quantity);
            contentValues.put("product_id", pro_id);
            contentValues.put("product_category", p_cat);
            contentValues.put("purchase_data", date);
            db.insert("User_Order", null, contentValues);
            db.close();
            return 1;
        }
    }
    public void update_product_quantity(int p_id, int new_q) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("quantity",new_q);
        db.update("Product",contentValues,"id like ?",new String[]{String.valueOf(p_id)});
        db.close();
    }
    public int get_new_item_quantity(int item_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =db.rawQuery("select quantity from Product where id=? ",new String[]{String.valueOf(item_id)});
        if(cursor!=null)
        {
            cursor.moveToFirst();
            return cursor.getInt(0);
        }
        return 0;
    }
    public Cursor get_user_orders(int user_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from User_Order where customer_id=? and cart_id=?",
                new String[]{String.valueOf(user_id),String.valueOf(user_id)});
        if(cursor!=null)
        {
            //cursor.moveToFirst();
            return cursor;
        }
        return null;
    }
    public void delete_order(int order_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int numofrecords=db.delete( "User_Order", "order_id =?",new String[]{String.valueOf(order_id)});
        db.close();
    }
    public void delete_user_orders(int user_id) {
        SQLiteDatabase db_write = this.getWritableDatabase();
        int numofrecords=db_write.delete( "User_Order", "cart_id =? and customer_id=?",new String[]{String.valueOf(user_id),String.valueOf(user_id)});
        db_write.close();
    }
    public int get_quantity_or_item_inuser_order(int p_id,int user_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select product_quntity from User_Order where customer_id=? and product_id=?",
                new String[]{String.valueOf(user_id),String.valueOf(p_id)});
        if(cursor.moveToFirst())
        {
            return cursor.getInt(0);
        }
        return 0;
    }
    public Cursor search_by_text() {
        SQLiteDatabase db_read = this.getReadableDatabase();
        Cursor cursor=db_read.rawQuery("select name from Product",null);
        if(cursor!=null)
        {
            cursor.moveToFirst();
            return cursor;
        }
        db_read.close();
        return null;
    }
    public void store_order_details_after_submit(int user_id,String credit,double price,String date,String address) {
        SQLiteDatabase db_write = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_id", user_id);
        contentValues.put("credit_card", credit);
        contentValues.put("Order_price", price);
        contentValues.put("Order_address", address);
        contentValues.put("Order_date", date);
        db_write.insert("ShoppingCart", null, contentValues);
        db_write.close();
    }
    public Cursor get_product_data(String name){
        SQLiteDatabase db_read = this.getReadableDatabase();
        Cursor cursor =db_read.rawQuery("select * from Product where name like ? ",new String[]{name});
        if(cursor!=null)
        {
            cursor.moveToFirst();
            return cursor;
        }
        db_read.close();
        return null;
    }
    public String get_email(int user_id) {
        SQLiteDatabase db_read = this.getReadableDatabase();
        String quary ="select ID,email from Customers ";
        Cursor cursor =db_read.rawQuery(quary,null);
        int a;
        String b=null;
        if(cursor.moveToFirst()){
            do{
                a=Integer.parseInt(cursor.getString(0));
                if(a==user_id)
                {
                    b=cursor.getString(1);
                    break;
                }
            }while (cursor.moveToNext());
        }
        return b;
    }
    public boolean check_item_if_exist_inorder(int user_id,int p_id) {
        SQLiteDatabase db_read = this.getReadableDatabase();
        Cursor cursor =db_read.rawQuery("select product_id,customer_id from User_Order",null);
        if(cursor.moveToFirst()){
            do {
                if(cursor.getInt(1)==user_id)
                {
                    return true;
                }
            }while (cursor.moveToNext());
        }
        return false;
    }
}