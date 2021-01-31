package com.example.onlineshoppingisa;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.onlineshoppingisa.fragment.ModifiadCartViewFragment;
import com.example.onlineshoppingisa.models.ConfirmOrder;

public class MainActivity4 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        ConfirmOrder confirmOrder = getIntent().getParcelableExtra(MainActivity5.PARAMTER1);
        ModifiadCartViewFragment modifiadCartViewFragment = new ModifiadCartViewFragment(confirmOrder);

        getSupportFragmentManager().beginTransaction().replace(R.id.activity_4_recyclerView, modifiadCartViewFragment)
                .commit();
    }

}