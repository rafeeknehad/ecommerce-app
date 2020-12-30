package com.example.onlineshoppingisa.activity2;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.onlineshoppingisa.activity2.MainActivity2Reposotory;
import com.example.onlineshoppingisa.models.User;

import java.util.List;

public class MainActivity2Model extends AndroidViewModel {

    private MainActivity2Reposotory reposotory;
    private LiveData<List<User>> listLiveData;


    public MainActivity2Model(@NonNull Application application) {
        super(application);
        reposotory = new MainActivity2Reposotory();
        listLiveData = reposotory.getAllUsers();
    }

    public void addUser(User user)
    {
        reposotory.addUsers(user);
    }

    public void addUserAuth(User user)
    {
        reposotory.addUserAuth(user);
    }

    public LiveData<List<User>> getAllUser()
    {
        return listLiveData;
    }

    public LiveData<Boolean> loginUser(String email,String pass){
        return reposotory.loginUser(email,pass);
    }

}
