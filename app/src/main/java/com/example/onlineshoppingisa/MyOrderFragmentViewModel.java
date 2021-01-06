package com.example.onlineshoppingisa;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.onlineshoppingisa.models.OrderGroup;
import com.example.onlineshoppingisa.roomdatabase.ProductRoom;
import com.example.onlineshoppingisa.util.UserDateDelivery;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MyOrderFragmentViewModel extends AndroidViewModel {
    private static final String TAG = "MyOrderFragmentViewMode";

    private MutableLiveData<List<OrderGroup>> listMutableLiveData;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    public MyOrderFragmentViewModel(@NonNull Application application) {
        super(application);
        listMutableLiveData = new MutableLiveData<>();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public LiveData<List<OrderGroup>> getAllData() {
        List<OrderGroup> orderGroupList = new ArrayList<>();
        firebaseFirestore.collection("UserDeliveryData")
                .document(firebaseAuth.getCurrentUser().getUid())
                .collection("deliverdData")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            UserDateDelivery userDateDelivery = documentSnapshot.toObject(UserDateDelivery.class);
                            firebaseFirestore.collection("HistoryOrder")
                                    .document(firebaseAuth.getCurrentUser().getUid())
                                    .collection(userDateDelivery.getDate())
                                    .get()
                                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            List<ProductRoom> productRoomList = new ArrayList<>();
                                            for (QueryDocumentSnapshot documentSnapshot1 : queryDocumentSnapshots) {
                                                ProductRoom productRoom = documentSnapshot1.toObject(ProductRoom.class);
                                                productRoomList.add(productRoom);
                                            }
                                            orderGroupList.add(new OrderGroup(userDateDelivery.getDate(),
                                                    productRoomList));
                                            listMutableLiveData.setValue(orderGroupList);
                                        }

                                    });
                        }
                    }
                });
        return listMutableLiveData;
    }
}
