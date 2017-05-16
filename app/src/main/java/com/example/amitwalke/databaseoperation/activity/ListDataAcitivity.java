package com.example.amitwalke.databaseoperation.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.amitwalke.databaseoperation.R;
import com.example.amitwalke.databaseoperation.adapter.RecyclerAdapter;
import com.example.amitwalke.databaseoperation.database.StorageManager;
import com.example.amitwalke.databaseoperation.model.CartItem;
import com.example.amitwalke.databaseoperation.network.GetRequestCall;

import java.util.ArrayList;
import java.util.List;

public class ListDataAcitivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    StorageManager manager;
    private static final String TAG = "ListDataAcitivity";
    Button mBtnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data_acitivity);
        manager=StorageManager.getInstance(this);
        mRecyclerView=(RecyclerView)findViewById(R.id.mRecyclerView);
        mBtnSearch=(Button)findViewById(R.id.mBtnSearch);
        ArrayList<CartItem> mCartItems=new ArrayList<>();
        mCartItems=manager.getProductsFromLocalCart();

        RecyclerAdapter mAdapter=new RecyclerAdapter(this,mCartItems);

        RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(mAdapter);

        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
         //       showAlertDialog();
                GetRequestCall mGetRequestCall=new GetRequestCall(ListDataAcitivity.this);
                mGetRequestCall.execute();


            }
        });

    }

    public void showAlertDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Manual Item Search");
        alert.setMessage("Input Search Query");
// Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String result = input.getText().toString();
                if(manager.isItemPresent(result))
                {
                    Toast.makeText(ListDataAcitivity.this,"DATA PRESENT",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ListDataAcitivity.this,"DATA NOT PRESENT",Toast.LENGTH_SHORT).show();
                }
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });
        alert.show();

    }
}
