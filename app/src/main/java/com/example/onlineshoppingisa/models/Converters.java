package com.example.onlineshoppingisa.models;

import androidx.room.TypeConverter;

import com.google.gson.Gson;

public class Converters {
    @TypeConverter
    public String fromConfirmOrderToGson(ConfirmOrder confirmOrder)
    {
        return new Gson().toJson(confirmOrder);
    }

    @TypeConverter
    public ConfirmOrder fromGsonToConfirmOrder(String stringUser)
    {
        return new Gson().fromJson(stringUser,ConfirmOrder.class);
    }
}
