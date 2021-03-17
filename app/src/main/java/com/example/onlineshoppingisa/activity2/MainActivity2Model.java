package com.example.onlineshoppingisa.activity2;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.onlineshoppingisa.models.User;

import java.util.List;

public class MainActivity2Model extends AndroidViewModel {

    private MainActivity2Reposotory repository;
    private LiveData<List<User>> listLiveData;


    public MainActivity2Model(@NonNull Application application) {
        super(application);
        repository = new MainActivity2Reposotory();
        listLiveData = repository.getAllUsers();
    }

    public void addUserAuth(User user)
    {
        repository.addUserAuth(user);
    }

    public LiveData<List<User>> getAllUser()
    {
        return listLiveData;
    }

    public LiveData<Boolean> loginUser(String email,String pass){
        return repository.loginUser(email,pass);
    }

}
