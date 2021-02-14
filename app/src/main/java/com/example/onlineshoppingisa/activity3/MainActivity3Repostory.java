package com.example.onlineshoppingisa.activity3;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.onlineshoppingisa.R;
import com.example.onlineshoppingisa.models.AllCategory;
import com.example.onlineshoppingisa.models.FashionDetails;
import com.example.onlineshoppingisa.models.LabtopDetails;
import com.example.onlineshoppingisa.models.MobileDetails;
import com.example.onlineshoppingisa.models.ProductType;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity3Repostory {

    private static final String TAG = "MainActivity3Repostory";

    private MutableLiveData<AllCategory> mutableLiveData;
    private DatabaseReference databaseReference;
    private ArrayList<MobileDetails> mobileDetailsArrayList;
    private ArrayList<FashionDetails> fashionDetailsArrayList;
    private ArrayList<ProductType> productTypeArrayList;
    private ArrayList<LabtopDetails> labtopDetailsArrayList;
    private Application application;

    public MainActivity3Repostory(Application application) {
        mutableLiveData = new MediatorLiveData<>();
        this.application = application;
        databaseReference = FirebaseDatabase.getInstance().getReference("");
        mobileDetailsArrayList = new ArrayList<>();
        fashionDetailsArrayList = new ArrayList<>();
        productTypeArrayList = new ArrayList<>();
        labtopDetailsArrayList = new ArrayList<>();
    }

    public LiveData<AllCategory> getAllData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mobileDetailsArrayList.clear();
                fashionDetailsArrayList.clear();
                productTypeArrayList.clear();
                labtopDetailsArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String type = dataSnapshot.getKey();
                    if (type != null && type.equals(application.getString(R.string.mobile_firebase))) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            MobileDetails mobileDetails = dataSnapshot1.getValue(MobileDetails.class);
                            if (mobileDetails != null) {
                                mobileDetails.setKey(dataSnapshot1.getKey());
                            }
                            mobileDetailsArrayList.add(mobileDetails);
                        }
                    } else if (type != null && type.equals(application.getString(R.string.fashion_firebase))) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            FashionDetails fashionDetails = dataSnapshot1.getValue(FashionDetails.class);
                            if (fashionDetails != null) {
                                fashionDetails.setKey(dataSnapshot1.getKey());
                            }
                            fashionDetailsArrayList.add(fashionDetails);
                        }
                    } else if (type != null && type.equals(application.getString(R.string.product_type_firebase))) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            ProductType productType = dataSnapshot1.getValue(ProductType.class);
                            if (productType != null) {
                                productType.setKey(dataSnapshot1.getKey());
                            }
                            productTypeArrayList.add(productType);
                        }
                    } else if (type != null && type.equals(application.getString(R.string.labtop_firebase))) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            LabtopDetails labtopDetails = dataSnapshot1.getValue(LabtopDetails.class);
                            if (labtopDetails != null) {
                                labtopDetails.setKey(dataSnapshot1.getKey());
                            }
                            labtopDetailsArrayList.add(labtopDetails);
                        }
                    }

                }
                AllCategory allCategory = new AllCategory(productTypeArrayList, mobileDetailsArrayList, fashionDetailsArrayList,
                        labtopDetailsArrayList);
                mutableLiveData.setValue(allCategory);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "onCancelled: ...... "+error.getMessage());
            }
        });

        return mutableLiveData;
    }
}
