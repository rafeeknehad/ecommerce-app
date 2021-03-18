package com.example.onlineshoppingisa.fragment;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshoppingisa.MyOrderFragmentModel;
import com.example.onlineshoppingisa.R;
import com.example.onlineshoppingisa.adapter.UserOrderGroupAdapter;
import com.example.onlineshoppingisa.models.FashionDetails;
import com.example.onlineshoppingisa.models.LabtopDetails;
import com.example.onlineshoppingisa.models.MobileDetails;
import com.example.onlineshoppingisa.models.OrderDetails;
import com.example.onlineshoppingisa.models.Orders;
import com.example.onlineshoppingisa.util.OrderDetailsGroupOfOrderFragment;
import com.example.onlineshoppingisa.util.OrderDetailsOfOrderFragment;

import java.util.ArrayList;
import java.util.List;

public class MyOrderFragment extends Fragment {

    private static final String TAG = "MyOrderFragment";
    private RecyclerView mRecyclerView;
    private MyOrderFragmentModel mMyOrderViewModel;

    private List<MobileDetails> mobileDetailsArrayList;
    private List<FashionDetails> fashionDetailsArrayList;
    private List<LabtopDetails> laptopDetailsArrayList;

    private List<Orders> ordersList;
    private List<OrderDetails> orderDetailsList;

    public MyOrderFragment(List<MobileDetails> mobileDetailsArrayList, List<FashionDetails> fashionDetailsArrayList,
                           List<LabtopDetails> laptopDetailsArrayList) {
        this.mobileDetailsArrayList = mobileDetailsArrayList;
        this.fashionDetailsArrayList = fashionDetailsArrayList;
        this.laptopDetailsArrayList = laptopDetailsArrayList;
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
                ordersList = orders;
                initData(orders, orderDetailsList);
                Log.d(TAG, "onChanged: 11111111222 " + orders.size());
            }
        });
        mMyOrderViewModel.getAllOrderDetails().observe(getViewLifecycleOwner(), new Observer<List<OrderDetails>>() {
            @Override
            public void onChanged(List<OrderDetails> orderDetails) {
                Log.d(TAG, "onChanged: 1111111111 " + orderDetails.size());
                orderDetailsList = orderDetails;
                initData(ordersList, orderDetails);
            }
        });

        return view;
    }

    private void initData(List<Orders> orders, List<OrderDetails> orderDetails) {
        List<OrderDetailsGroupOfOrderFragment> groupOfData = new ArrayList<>();
        for (Orders itemOfOrder : orders) {
            List<OrderDetailsOfOrderFragment> orderDetailForOrder = new ArrayList<>();
            for (OrderDetails itemOfOrderDetails : orderDetails) {
                if (itemOfOrder.getOrderId().equals(itemOfOrderDetails.getOrderId())) {
                    if (itemOfOrderDetails.getProductId().contains("mobile")) {
                        for (MobileDetails mobileDetails : mobileDetailsArrayList) {
                            if (mobileDetails.getKey().equals(itemOfOrderDetails.getProductId())) {
                                orderDetailForOrder.add(new OrderDetailsOfOrderFragment(mobileDetails.getImage(), mobileDetails.getName(),
                                        itemOfOrderDetails.getQuantity(), mobileDetails.getPrice()));
                                Log.d(TAG, "initData: 444444 mobile");
                                break;
                            }
                        }
                    } else if (itemOfOrderDetails.getProductId().contains("Fashion")) {
                        for (FashionDetails fashionDetails : fashionDetailsArrayList) {
                            if (fashionDetails.getKey().equals(itemOfOrderDetails.getProductId())) {
                                orderDetailForOrder.add(new OrderDetailsOfOrderFragment(fashionDetails.getImage(), fashionDetails.getName(),
                                        itemOfOrderDetails.getQuantity(), fashionDetails.getPrice()));
                                Log.d(TAG, "initData: 444444 fashion");
                                break;

                            }
                        }
                    } else if (itemOfOrderDetails.getProductId().contains("labtop")) {
                        for (LabtopDetails labtopDetails : laptopDetailsArrayList) {
                            if (labtopDetails.getKey().equals(itemOfOrderDetails.getProductId())) {
                                orderDetailForOrder.add(new OrderDetailsOfOrderFragment(labtopDetails.getImage(), labtopDetails.getName(),
                                        itemOfOrderDetails.getQuantity(), labtopDetails.getPrice()));
                                Log.d(TAG, "initData: 444444 laptop");
                                break;
                            }
                        }
                    }
                }
            }
            groupOfData.add(new OrderDetailsGroupOfOrderFragment(itemOfOrder.getDate(), orderDetailForOrder));
        }
        UserOrderGroupAdapter groupAdapter = new UserOrderGroupAdapter(getActivity(), groupOfData);
        mRecyclerView.setAdapter(groupAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);
    }

}
