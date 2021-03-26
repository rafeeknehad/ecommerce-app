package com.example.onlineshoppingisa.roomdatabase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ProductDataBaseModel extends AndroidViewModel {

    private ProductDataBase productDataBase;

    public ProductDataBaseModel(@NonNull Application application) {
        super(application);
        productDataBase = ProductDataBase.grtInstance(application);
    }

    public LiveData<List<ProductRoom>> getLiveDataOfUser(String id) {
        return productDataBase.productDao().getAllData(id);
    }

    /*public void deleteDataForUser(String userId, String productId) {
        productDataBase.productDao().deleteDataForUser(userId, productId);
    }

    public LiveData<ProductRoom> findProduct(String userId, String productId) {
        return productDataBase.productDao().findProduct(userId, productId);
    }*/

    public void deleteAllOrderFromUser(String userId){
        productDataBase.productDao().deleteAllOrderFromUser(userId);
    }

}
