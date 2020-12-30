package com.example.onlineshoppingisa.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.onlineshoppingisa.R;
import com.example.onlineshoppingisa.models.ProductDetailCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

public class ModifiadCartViewFragment extends Fragment {

    private ImageView productimageView;
    private TextView productName;
    private TextView productQuantity;
    private TextView productPrice;
    private TextView productDataDelivery;
    private TextView productLocation;
    private FloatingActionButton addBtn;
    private FloatingActionButton subBtn;
    private Button confirmCart;
    private ImageButton locationBtn;

    private ProductDetailCardView productDetailCardView;
    public ModifiadCartViewFragment(ProductDetailCardView productDetailCardView) {
        this.productDetailCardView = productDetailCardView;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.modifiad_cart_view, container, false);
        productimageView = view.findViewById(R.id.modifiad_cart_view_image);
        productName = view.findViewById(R.id.modifiad_cart_view_product_name);
        productQuantity = view.findViewById(R.id.modifiad_cart_view_product_quality_value);
        productPrice = view.findViewById(R.id.modifiad_cart_view_product_price_value);
        productDataDelivery = view.findViewById(R.id.modifiad_cart_view_deliver_Date_value);
        productLocation = view.findViewById(R.id.modifiad_cart_view_loaction_value);
        addBtn = view.findViewById(R.id.modifiad_cart_view_increase_value);
        subBtn = view.findViewById(R.id.modifiad_cart_view_decrese_value);
        confirmCart = view.findViewById(R.id.modifiad_cart_view_confitm_btn);
        locationBtn = view.findViewById(R.id.modifiad_cart_view_location_btn);

        initialData();
        return view;
    }

    private void initialData() {

    }


}
