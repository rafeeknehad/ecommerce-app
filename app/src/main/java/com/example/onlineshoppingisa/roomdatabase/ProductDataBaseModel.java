package com.example.onlineshoppingisa.roomdatabase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.room.RoomDatabase;

import com.example.onlineshoppingisa.MainActivity3;

import java.util.List;

public class ProductDataBaseModel extends AndroidViewModel {

    private ProductDataBase productDataBase;

    public ProductDataBaseModel(@NonNull Application application) {
        super(application);
        productDataBase = ProductDataBase.grtInstance(application);
        System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb "+productDataBase.toString());
    }

    public LiveData<List<ProductRoom>> getLiveDataOfUser(String id)
    {
        return productDataBase.productDao().getAllData(id);
    }


}
