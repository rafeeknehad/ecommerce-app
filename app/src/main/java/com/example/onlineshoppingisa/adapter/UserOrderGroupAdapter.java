package com.example.onlineshoppingisa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshoppingisa.R;
import com.example.onlineshoppingisa.models.OrderGroup;

import java.util.List;

public class UserOrderGroupAdapter extends RecyclerView.Adapter<UserOrderGroupAdapter.UserOrderGroupViewHolder> {

    private Context mContext;
    private List<OrderGroup> mOrderGroupList;

    public UserOrderGroupAdapter(Context context, List<OrderGroup> orderGroupList) {
        this.mContext = context;
        this.mOrderGroupList = orderGroupList;
    }

    @NonNull
    @Override
    public UserOrderGroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_group_cardview, parent, false);
        return new UserOrderGroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserOrderGroupViewHolder holder, int position) {
        holder.mDateTxt.setText(mOrderGroupList.get(position).getDate());
        UserOrderAdapter userOrderAdapter = new UserOrderAdapter(mContext, mOrderGroupList.get(position).getProductRooms());
        holder.mOrderRecy.setAdapter(userOrderAdapter);
        holder.mOrderRecy.setHasFixedSize(true);
        holder.mOrderRecy.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
    }

    @Override
    public int getItemCount() {
        return mOrderGroupList.size();
    }

    public class UserOrderGroupViewHolder extends RecyclerView.ViewHolder {

        private TextView mDateTxt;
        private RecyclerView mOrderRecy;

        public UserOrderGroupViewHolder(@NonNull View itemView) {
            super(itemView);
            mDateTxt = itemView.findViewById(R.id.order_group_cardview_datetxt);
            mOrderRecy = itemView.findViewById(R.id.order_group_cardview_recy);
        }
    }
}
