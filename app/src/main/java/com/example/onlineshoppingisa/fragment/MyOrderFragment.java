package com.example.onlineshoppingisa.fragment;

import android.content.Context;
import android.os.Bundle;
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

import com.example.onlineshoppingisa.MyOrderFragmentViewModel;
import com.example.onlineshoppingisa.R;
import com.example.onlineshoppingisa.adapter.UserOrderAdapter;
import com.example.onlineshoppingisa.adapter.UserOrderGroupAdapter;
import com.example.onlineshoppingisa.models.OrderGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MyOrderFragment extends Fragment {

    private static final String TAG = "MyOrderFragment";


    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private Context mContext;
    private MyOrderFragmentViewModel myOrderFragmentViewModel;

    private RecyclerView recyclerView;
    private UserOrderGroupAdapter userOrderGroupAdapter;

    public MyOrderFragment(Context context) {
        mContext = context;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_order_fragment, container, false);
        recyclerView = view.findViewById(R.id.my_order_fragment_recyclerview);
        myOrderFragmentViewModel = ViewModelProviders.of(getActivity()).get(MyOrderFragmentViewModel.class);

        myOrderFragmentViewModel.getAllData().observe(getActivity(), new Observer<List<OrderGroup>>() {
            @Override
            public void onChanged(List<OrderGroup> orderGroupList) {
                userOrderGroupAdapter = new UserOrderGroupAdapter(getActivity(),orderGroupList);
                recyclerView.setAdapter(userOrderGroupAdapter);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
                userOrderGroupAdapter.notifyDataSetChanged();
            }
        });
        return view;
    }

    private void fetchData() {

    }
}
