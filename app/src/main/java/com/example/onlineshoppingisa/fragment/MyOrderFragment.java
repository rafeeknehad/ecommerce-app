package com.example.onlineshoppingisa.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshoppingisa.MyOrderFragmentModel;
import com.example.onlineshoppingisa.R;
import com.example.onlineshoppingisa.models.Orders;

import java.util.List;

public class MyOrderFragment extends Fragment {

    private static final String TAG = "MyOrderFragment";
    private RecyclerView mRecyclerView;
    private MyOrderFragmentModel mMyOrderViewModel;

    public MyOrderFragment(Context context) {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_order_fragment, container, false);
        mRecyclerView = view.findViewById(R.id.my_order_fragment_recyclerview);
        if (getActivity() != null) {
            mMyOrderViewModel = ViewModelProviders.of(getActivity()).get(MyOrderFragmentModel.class);
        }
        mMyOrderViewModel.getCustomerOrder().observe(this, new Observer<List<Orders>>() {
            @Override
            public void onChanged(List<Orders> orders) {
                Log.d(TAG, "onChanged: 0000 e7na hena " + orders.size());
            }
        });
        return view;
    }

}
