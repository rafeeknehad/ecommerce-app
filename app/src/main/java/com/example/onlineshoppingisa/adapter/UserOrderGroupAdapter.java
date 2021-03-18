package com.example.onlineshoppingisa.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshoppingisa.R;
import com.example.onlineshoppingisa.util.OrderDetailsGroupOfOrderFragment;
import com.example.onlineshoppingisa.util.OrderDetailsOfOrderFragment;

import java.util.List;

public class UserOrderGroupAdapter extends RecyclerView.Adapter<UserOrderGroupAdapter.UserOrderGroupViewHolder> {

    private static final String TAG = "UserOrderGroupAdapter";
    private Context mContext;
    private List<OrderDetailsGroupOfOrderFragment> mOrderGroupList;

    public UserOrderGroupAdapter(Context context, List<OrderDetailsGroupOfOrderFragment> orderGroupList) {
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
        OrderDetailsGroupOfOrderFragment item = mOrderGroupList.get(position);
        holder.mDateTxt.setText(item.getDate().toString());
        List<OrderDetailsOfOrderFragment> details = item.getList();
        Log.d(TAG, "onBindViewHolder: 00000 " + details.size());
        UserOrderAdapter userOrderAdapter = new UserOrderAdapter(mContext, details);
        holder.recyclerView.setAdapter(userOrderAdapter);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        holder.recyclerView.setHasFixedSize(true);
    }

    @Override
    public int getItemCount() {
        return mOrderGroupList.size();
    }

    public static class UserOrderGroupViewHolder extends RecyclerView.ViewHolder {

        private TextView mDateTxt;
        private RecyclerView recyclerView;

        public UserOrderGroupViewHolder(@NonNull View itemView) {
            super(itemView);
            mDateTxt = itemView.findViewById(R.id.order_group_cardview_datetxt);
            recyclerView = itemView.findViewById(R.id.order_group_cardview_recy);
        }
    }
}
