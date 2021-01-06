package com.example.onlineshoppingisa.roomdatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;


@Dao
public interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(ProductRoom productRoom);

    @Delete
    void delete(ProductRoom productRoom);

    @Query("select * from ProductRoom where userId like :userID")
    LiveData<List<ProductRoom>> getAllData(String userID);

    @Query("delete from ProductRoom where userId like :userId")
    void deleteDataForUser(String userId);
}
