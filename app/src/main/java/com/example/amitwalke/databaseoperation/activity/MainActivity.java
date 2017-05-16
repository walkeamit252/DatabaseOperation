package com.example.amitwalke.databaseoperation.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.amitwalke.databaseoperation.R;
import com.example.amitwalke.databaseoperation.database.DatabaseHelper;
import com.example.amitwalke.databaseoperation.database.StorageManager;
import com.example.amitwalke.databaseoperation.model.CartItem;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    EditText mEditText;
    Button btn,mBtnShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditText=(EditText)findViewById(R.id.mEditText);
        btn=(Button)findViewById(R.id.mBtn);
        mBtnShow=(Button)findViewById(R.id.mBtnShow);
        final StorageManager manager=StorageManager.getInstance(this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartItem mCartItem=new CartItem();
                mCartItem.productId=mEditText.getText().toString();
                mCartItem.productName="Amit";
                mCartItem.productUrl="abc";
                int item= manager.addToLocalCart(mCartItem);
                Log.i(TAG, "onClick: ITEM VALUE==>"+item);
                if(item==1){
                    Log.i(TAG, "onClick: ITEM ADDED");
                }else {
                    Log.i(TAG, "onClick: ITEM NOT ADDED");
                }
            }
        });

        mBtnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ListDataAcitivity.class));
            }
        });

    }
}
