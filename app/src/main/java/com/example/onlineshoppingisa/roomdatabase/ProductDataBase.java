package com.example.onlineshoppingisa.roomdatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ProductRoom.class},version = 1)
public abstract class ProductDataBase extends RoomDatabase {
    private static ProductDataBase instance;

    public abstract ProductDao productDao();

    public static synchronized ProductDataBase grtInstance(Context context)
    {
        if(instance == null)
        {
            instance = Room.databaseBuilder(context,ProductDataBase.class,"DataBase")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
