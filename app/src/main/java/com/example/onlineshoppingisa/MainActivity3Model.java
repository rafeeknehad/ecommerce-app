package com.example.onlineshoppingisa;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.onlineshoppingisa.models.AllCategory;
import com.example.onlineshoppingisa.models.FashionDetails;

import java.util.HashMap;
import java.util.List;

public class MainActivity3Model extends AndroidViewModel {

    private MutableLiveData<FashionDetails> objectMutableLiveData;
    private MainActivity3Repostory repostory;

    public MainActivity3Model(@NonNull Application application) {
        super(application);
        repostory = new MainActivity3Repostory(application);
    }

    public LiveData<AllCategory> getLiveData()
    {
        return repostory.getAllData();
    }
}
