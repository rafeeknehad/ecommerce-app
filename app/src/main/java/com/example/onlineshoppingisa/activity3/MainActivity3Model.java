package com.example.onlineshoppingisa.activity3;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.onlineshoppingisa.models.AllCategory;
import com.example.onlineshoppingisa.models.FashionDetails;

public class MainActivity3Model extends AndroidViewModel {

    private MutableLiveData<FashionDetails> objectMutableLiveData;
    private MainActivity3Repostory repostory;

    public MainActivity3Model(@NonNull Application application) {
        super(application);
        repostory = new MainActivity3Repostory(application);
    }

    public LiveData<AllCategory> getLiveData()
    {
        System.out.println(".............................");
        return repostory.getAllData();
    }
}
