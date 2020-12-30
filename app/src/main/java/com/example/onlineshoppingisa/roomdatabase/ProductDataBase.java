package com.example.onlineshoppingisa.roomdatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.onlineshoppingisa.models.Converters;

@Database(entities = {ProductRoom.class},version = 2)
@TypeConverters(Converters.class)
public abstract class ProductDataBase extends RoomDatabase {
    private static ProductDataBase instance;

    public abstract ProductDao productDao();

    public static synchronized ProductDataBase grtInstance(Context context)
    {
        if(instance == null)
        {
            instance = Room.databaseBuilder(context,ProductDataBase.class,"DataBase")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

}
