package com.example.amitwalke.databaseoperation.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amitwalke.databaseoperation.R;
import com.example.amitwalke.databaseoperation.database.StorageManager;
import com.example.amitwalke.databaseoperation.model.CartItem;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.DataViewHolder> {

    private ArrayList<CartItem> cartItem;
    Context mContext;
    StorageManager manager;

    public class DataViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public DataViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.mTextViewName);
        }
    }


    public RecyclerAdapter(Context mContext,ArrayList<CartItem> cartItem) {
        this.cartItem = cartItem;
        this.mContext =mContext;
        manager=StorageManager.getInstance(mContext);
    }



    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_layout, parent, false);
        return new DataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) {
        final CartItem item = cartItem.get(position);
        holder.name.setText(item.productId);
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,item.productId,Toast.LENGTH_SHORT).show();
            }
        });

        holder.name.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(mContext,item.productId+"\n"+item.productName,Toast.LENGTH_SHORT).show();
                manager.removeFromCart(item.productId);
                cartItem=manager.getProductsFromLocalCart();

                notifyDataSetChanged();
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
         if(cartItem!=null)
         return cartItem.size();
        else
            return 0;

    }
}