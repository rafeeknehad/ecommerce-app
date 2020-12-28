package com.example.onlineshoppingisa;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.onlineshoppingisa.models.ConfirmOrder;
import com.example.onlineshoppingisa.models.OrderDetails;
import com.example.onlineshoppingisa.models.Orders;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity4 extends AppCompatActivity {

    private static final String TAG = "MainActivity4";

    //variable
    private ConfirmOrder confirmOrder;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private CollectionReference orderDetailsCollection = firestore.collection("OrderDetails");
    private CollectionReference orderCollection = firestore.collection("Orders");
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private String orderId;

    //xml
    private ImageView productImage;
    private TextView productName;
    private TextView productQuantity;
    private TextView productTotalPrice;
    private TextView productLocation;
    private TextView productDeliverdDate;
    private Button confirmOrderBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        confirmOrder = getIntent().getParcelableExtra(MainActivity5.PARAMTER1);

        productImage = findViewById(R.id.main_activity4_image);
        productName = findViewById(R.id.main_activity4_product_name);
        productQuantity = findViewById(R.id.main_activity4_product_quality_value);
        productTotalPrice = findViewById(R.id.main_activity4_product_price_value);
        productLocation = findViewById(R.id.main_activity4_loaction_value);
        productDeliverdDate = findViewById(R.id.main_activity4_deliver_Date_value);
        confirmOrderBtn = findViewById(R.id.main_activity4_confitm_btn);
        //Log.d(TAG, "onCreate: 0000000000000 "+confirmOrder.getLatitude()+"   "+confirmOrder.getLongitude());
        initialData();
        confirmOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrder();
            }
        });
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

    private void initialData() {
        Picasso.with(MainActivity4.this)
                .load(confirmOrder.getProductImage())
                .into(productImage);

        productName.setText(confirmOrder.getProductName());

        productQuantity.setText(confirmOrder.getGetProductQuantity());
        confirmOrder.getProductPrice().replace(",","");
        confirmOrder.getProductPrice().replace(".","");
        String priceStr = new String();
        for (int i=0;i<confirmOrder.getProductPrice().length();i++)
        {
            if(confirmOrder.getProductPrice().charAt(i) ==',' || confirmOrder.getProductPrice().charAt(i) == '.')
            {
                continue;
            }
            priceStr+=confirmOrder.getProductPrice().charAt(i);
        }
        Double price = Double.valueOf(priceStr) * Double.valueOf(confirmOrder.getGetProductQuantity());
        productTotalPrice.setText(String.valueOf(price) + "EGP");

        productDeliverdDate.setText(confirmOrder.getProductDeliverDate());

        String location = null;
        Geocoder geocoder;
        List<Address> addresses = new ArrayList<>();
        geocoder = new Geocoder(this, Locale.getDefault());
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

        location = address + " , " + city + " , " + state + " , " + country + " , " + postalCode + " , " + knownName;
        productLocation.setText(location);
    }

}