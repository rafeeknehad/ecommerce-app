package com.example.onlineshoppingisa;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.onlineshoppingisa.models.Orders;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyOrderFragmentModel extends AndroidViewModel {

    private static final String TAG = "MyOrderFragmentModel";
    private MutableLiveData<List<Orders>> mOrdersMutableLiveData;
    private List<Orders> ordersList;

    public MyOrderFragmentModel(@NonNull Application application) {
        super(application);
        mOrdersMutableLiveData = new MutableLiveData<>();
        ordersList = new ArrayList<>();
        mOrdersMutableLiveData.setValue(ordersList);
    }

    public LiveData<List<Orders>> getCustomerOrder() {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        Log.d(TAG, "getCustomerOrder: 00000 " + MainActivity.currentUser.getUserAuthId());
        firebaseFirestore.collection("Orders")
                .document(MainActivity.currentUser.getUserAuthId())
                .collection("Order")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Orders order = documentSnapshot.toObject(Orders.class);
                            order.setOrderId(documentSnapshot.getId());
                            ordersList.add(order);
                        }
                        Log.d(TAG, "onComplete: 0000 eeee " + ordersList.size());
                        Collections.sort(ordersList);
                        mOrdersMutableLiveData.setValue(ordersList);
                    }

                });
        return mOrdersMutableLiveData;
    }

}
