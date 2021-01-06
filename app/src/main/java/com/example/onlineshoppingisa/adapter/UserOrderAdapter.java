package com.example.onlineshoppingisa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshoppingisa.R;
import com.example.onlineshoppingisa.models.ConfirmOrder;
import com.example.onlineshoppingisa.roomdatabase.ProductRoom;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class UserOrderAdapter extends RecyclerView.Adapter<UserOrderAdapter.UserOrderViewHolder> {

    private Context mContext;
    private List<ProductRoom> mProductRoomList;

    public UserOrderAdapter(Context mContext, List<ProductRoom> mProductRoomList) {
        this.mContext = mContext;
        this.mProductRoomList = mProductRoomList;
    }

    @NonNull
    @Override
    public UserOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_cardview,parent,false);
        return new UserOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserOrderViewHolder holder, int position) {
        ProductRoom item = mProductRoomList.get(position);
        Picasso.with(mContext).load(item.getConfirmOrder().getProductImage())
                .into(holder.mImageView);

        holder.mProductNameTxt.setText(item.getConfirmOrder().getProductName());
        holder.mProductQuantityTxt.setText(item.getConfirmOrder().getGetProductQuantity());
        holder.mProductPriceTxt.setText(item.getConfirmOrder().getProductPrice());
    }

    @Override
    public int getItemCount() {
        return mProductRoomList.size();
    }

    public class UserOrderViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView mImageView;
        private TextView mProductNameTxt;
        private TextView mProductQuantityTxt;
        private TextView mProductPriceTxt;
        public UserOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.order_cardview_imageview);
            mProductNameTxt = itemView.findViewById(R.id.order_cardview_product_name);
            mProductQuantityTxt = itemView.findViewById(R.id.order_cardview_product_qunatity);
            mProductPriceTxt = itemView.findViewById(R.id.order_cardview_price);
        }
    }
}
