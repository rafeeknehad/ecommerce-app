package com.example.onlineshoppingisa;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.onlineshoppingisa.models.OrderDetails;
import com.example.onlineshoppingisa.models.Orders;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyOrderFragmentModel extends AndroidViewModel {

    private static final String TAG = "MyOrderFragmentModel";
    private MutableLiveData<List<Orders>> mOrdersMutableLiveData;
    private MutableLiveData<List<OrderDetails>> mOrderDetailsLiveData;
    private List<Orders> ordersList;
    private List<OrderDetails> orderDetailsList;

    public MyOrderFragmentModel(@NonNull Application application) {
        super(application);
        mOrdersMutableLiveData = new MutableLiveData<>();
        mOrderDetailsLiveData = new MutableLiveData<>();
        ordersList = new ArrayList<>();
        orderDetailsList = new ArrayList<>();
        mOrdersMutableLiveData.setValue(ordersList);
        mOrderDetailsLiveData.setValue(orderDetailsList);
    }

    public LiveData<List<Orders>> getCustomerOrder() {
        ordersList.clear();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Orders")
                .document(MainActivity.currentUser.getUserAuthId())
                .collection("Order")
                .get()
                .addOnCompleteListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.isSuccessful() && queryDocumentSnapshots.getResult() != null) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots.getResult()) {
                            Orders order = documentSnapshot.toObject(Orders.class);
                            order.setOrderId(documentSnapshot.getId());
                            ordersList.add(order);
                        }
                    }
                    Collections.sort(ordersList);
                    mOrdersMutableLiveData.setValue(ordersList);
                    Log.d(TAG, "getCustomerOrder: 7777777 " + ordersList.size());
                });
        return mOrdersMutableLiveData;
    }

    public LiveData<List<OrderDetails>> getAllOrderDetails() {
        orderDetailsList = new ArrayList<>();
        Log.d(TAG, "getAllOrderDetails: 1111 " + ordersList.size());
        FirebaseFirestore.getInstance().collection("OrderDetails")
                .document(MainActivity.currentUser.getUserAuthId())
                .collection("OrderDetails")
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        for (QueryDocumentSnapshot snapshot : task.getResult()) {
                            OrderDetails orderDetails = snapshot.toObject(OrderDetails.class);
                            orderDetails.setOrderDetailsId(snapshot.getId());
                            orderDetailsList.add(orderDetails);
                        }
                        mOrderDetailsLiveData.setValue(orderDetailsList);
                    }
                });

        return mOrderDetailsLiveData;
    }
}
