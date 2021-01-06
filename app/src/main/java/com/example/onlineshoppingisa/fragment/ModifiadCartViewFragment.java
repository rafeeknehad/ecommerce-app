package com.example.onlineshoppingisa.fragment;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
import com.example.onlineshoppingisa.roomdatabase.ProductDataBase;
import com.example.onlineshoppingisa.roomdatabase.ProductRoom;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ModifiadCartViewFragment extends Fragment {

    private static final int PLACE_PICKER_REQUEST = 1;
    private static final String TAG = "ModifiadCartViewFragmen";

    private ConfirmOrder confirmOrder;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private CollectionReference orderDetailsCollection = firestore.collection("OrderDetails");
    private CollectionReference orderCollection = firestore.collection("Orders");
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private String orderId;
    private ProductDataBase productDataBase;
    private String orderDetailsId;

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

        productDataBase = ProductDataBase.grtInstance(getActivity());

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
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBtn.setEnabled(true);
                String currentValue = productQuantity.getText().toString();
                Double price = Double.valueOf(productPrice.getText().toString())/(Double.valueOf(currentValue));
                int currentValueInt = Integer.valueOf(currentValue)+1;
                productQuantity.setText(String.valueOf(currentValueInt));
                productPrice.setText(String.valueOf(price*currentValueInt));
            }
        });

        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentValue = productQuantity.getText().toString();
                Double price = Double.valueOf(productPrice.getText().toString())/(Double.valueOf(currentValue));
                int currentValueInt = Integer.valueOf(currentValue)-1;
                productQuantity.setText(String.valueOf(currentValueInt));
                productPrice.setText(String.valueOf(price*currentValueInt));
                if(currentValueInt==0)
                {
                    subBtn.setEnabled(false);
                }
            }
        });
        return view;
    }

    private void initialData() {

        Log.d(TAG, "initialData: **** "+confirmOrder.getProductDeliverDate());
        Picasso.with(getActivity())
                .load(confirmOrder.getProductImage())
                .into(productimageView);

        productName.setText(confirmOrder.getProductName());

        productDataDelivery.setText(confirmOrder.getProductDeliverDate());

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
        getLocation(confirmOrder.getLatitude(),confirmOrder.getLongitude());
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
                                Log.d(TAG, "onSuccess: **** "+orderId);
                                confirmOrder.setOrderId(orderId);
                                Log.d(TAG, "run: **** "+documentReference.getId());
                                orderDetailsId = documentReference.getId();
                            }
                        });
            }
        }, 5000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                confirmOrder.setOrderId(orderId);
                confirmOrder.setOrderDetailId(orderDetailsId);
                productDataBase.productDao().insert(new ProductRoom(confirmOrder, firebaseAuth.getCurrentUser().getUid(), confirmOrder.getProductId()))
                        .subscribeOn(Schedulers.computation())
                        .subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onComplete() {
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        });
            }
        }, 8000);

    }

    private void getLocation(String x,String y) {
        String location = new String();
        Geocoder geocoder;
        List<Address> addresses = new ArrayList<>();
        geocoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(Double.valueOf(x), Double.valueOf(y), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }
        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName();
        if (address != null) location += address + ",";
        if (city != null) location += city + ",";
        if (state != null) location += state + ",";
        if (country != null) location += country + ",";
        if (postalCode != null) location += postalCode + ",";
        if (knownName != null) location += knownName + ",";
        productLocation.setText(location);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == getActivity().RESULT_OK) {
                Place place = PlacePicker.getPlace(data, getActivity());
                String x = String.valueOf(place.getLatLng().latitude);
                String y = String.valueOf(place.getLatLng().longitude);
                getLocation(x,y);
            }
        }
    }
}
