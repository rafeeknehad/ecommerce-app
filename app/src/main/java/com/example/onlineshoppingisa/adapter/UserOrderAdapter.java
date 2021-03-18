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
import com.example.onlineshoppingisa.util.OrderDetailsOfOrderFragment;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class UserOrderAdapter extends RecyclerView.Adapter<UserOrderAdapter.UserOrderViewHolder> {

    private Context mContext;
    private List<OrderDetailsOfOrderFragment> orderDetailsOfOrderFragments;

    public UserOrderAdapter(Context mContext, List<OrderDetailsOfOrderFragment> orderDetailsOfOrderFragments) {
        this.mContext = mContext;
        this.orderDetailsOfOrderFragments = orderDetailsOfOrderFragments;
    }

    @NonNull
    @Override
    public UserOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_cardview,parent,false);
        return new UserOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserOrderViewHolder holder, int position) {

        DecimalFormat format = new DecimalFormat("###,###,###.00");
        OrderDetailsOfOrderFragment item = orderDetailsOfOrderFragments.get(position);
        Picasso.with(mContext).load(item.getProductImage())
                .into(holder.mImageView);

        holder.mProductNameTxt.setText(item.getProductName());
        holder.mProductQuantityTxt.setText(item.getProductQuantity());
        long totalPrice = Integer.parseInt(item.getProductQuantity()) * Integer.parseInt(item.getProductTotalPrice());
        holder.mProductPriceTxt.setText(String.format("%s%s", format.format(totalPrice), '$'));
    }

    @Override
    public int getItemCount() {
        return orderDetailsOfOrderFragments.size();
    }

    public static class UserOrderViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private TextView mProductNameTxt;
        private TextView mProductQuantityTxt;
        private TextView mProductPriceTxt;

        public UserOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.order_card_view_image_view);
            mProductNameTxt = itemView.findViewById(R.id.order_card_view_product_name);
            mProductQuantityTxt = itemView.findViewById(R.id.order_card_view_product_quantity);
            mProductPriceTxt = itemView.findViewById(R.id.order_card_view_price);
        }
    }
}
