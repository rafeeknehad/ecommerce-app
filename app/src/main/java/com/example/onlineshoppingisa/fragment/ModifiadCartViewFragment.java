package com.example.onlineshoppingisa.fragment;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.onlineshoppingisa.R;
import com.example.onlineshoppingisa.models.ConfirmOrder;
import com.example.onlineshoppingisa.models.OrderDetails;
import com.example.onlineshoppingisa.models.Orders;
import com.example.onlineshoppingisa.models.ProductDetailCardView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ModifiadCartViewFragment extends Fragment {


    private ConfirmOrder confirmOrder;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private CollectionReference orderDetailsCollection = firestore.collection("OrderDetails");
    private CollectionReference orderCollection = firestore.collection("Orders");
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private String orderId;

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


    public ModifiadCartViewFragment(ConfirmOrder confirmOrderVal) {
        this.confirmOrder = confirmOrderVal;
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
        confirmCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrder();
            }
        });

        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

    private void initialData() {
        Picasso.with(getActivity())
                .load(confirmOrder.getProductImage())
                .into(productimageView);

        productName.setText(confirmOrder.getProductName());

        productQuantity.setText(confirmOrder.getGetProductQuantity());
        String priceStr = new String();
        for (int i = 0; i < confirmOrder.getProductPrice().length(); i++) {
            if (confirmOrder.getProductPrice().charAt(i) == ',' || confirmOrder.getProductPrice().charAt(i) == '.') {
                continue;
            }
            priceStr += confirmOrder.getProductPrice().charAt(i);
        }
        Double price = Double.valueOf(priceStr) * Double.valueOf(confirmOrder.getGetProductQuantity());
        productPrice.setText(String.valueOf(price) + "EGP");

        productDataDelivery.setText(confirmOrder.getProductDeliverDate());

        productLocation.setText(getLocation());
    }

    private void addOrder() {
        Orders orders = new Orders(confirmOrder.getProductDeliverDate(), firebaseAuth.getCurrentUser().getUid(),
                productLocation.getText().toString());
        orderCollection.add(orders)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        orderId = documentReference.getId();
                    }
                });


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                OrderDetails orderDetails = new OrderDetails(confirmOrder.getProductId(), orderId,
                        String.valueOf(confirmOrder.getGetProductQuantity()));
                orderDetailsCollection.add(orderDetails)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {

                            }
                        });
            }
        }, 5000);

    }

    private String getLocation() {
        Toast.makeText(getActivity(), confirmOrder.getLatitude()+" \n "+confirmOrder.getLongitude(), Toast.LENGTH_SHORT).show();
        String location = new String();
        Geocoder geocoder;
        List<Address> addresses = new ArrayList<>();
        geocoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(Double.valueOf(confirmOrder.getLatitude()), Double.valueOf(confirmOrder.getLongitude()), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }
        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName();
        if(address!=null)location+=address+",";
        if(city!=null)location+=city+",";
        if(state!=null)location+=state+",";
        if(country!=null)location+=country+",";
        if(postalCode!=null)location+=postalCode+",";
        if(knownName!=null)location+=knownName+",";

        return location;
    }


}
