package com.example.onlineshoppingisa;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.example.onlineshoppingisa.activity3.MainActivity3;
import com.example.onlineshoppingisa.dialog.ProductDialog;
import com.example.onlineshoppingisa.models.ConfirmOrder;
import com.example.onlineshoppingisa.models.FashionDetails;
import com.example.onlineshoppingisa.models.LabtopDetails;
import com.example.onlineshoppingisa.models.MobileDetails;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class MainActivity5 extends AppCompatActivity implements ProductDialog.getDataFromProductDialog {

    public static final String PARAMTER1 = "Paramter1";
    private static final String TAG = "MainActivity5";
    private static final int PLACE_PICKER_REQUEST = 1;
    private static final int OPEN_ACTIVITY = 2;

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


    private String productId;
    private String productImage;
    private String productNameTxt;
    private String productPriceTxt;
    private String latitude;
    private String longitude;
    private int quantity;
    private String orderId;

    //xml
    private ImageView productImageView;
    private TextView productName;
    private TextView productPrice;
    private LinearLayout linearLayoutContainer;
    private TextView productMemory;
    private TextView productStorage;
    private ReadMoreTextView productDescription;
    private RatingBar productBarRating;
    private TextView productBarValue;
    private Button addCart;
    private Toolbar toolbar;
    private Button setLocation;
    private TextView textViewStorageTxt;
    private TextView textViewMemoryTxt;
    private Button confirmOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);


        toolbar = findViewById(R.id.main_activit5_toolbar);
        productImageView = findViewById(R.id.main_activit5_image);
        productName = findViewById(R.id.main_activit5_text_name);
        productPrice = findViewById(R.id.main_activit5_product_price);
        linearLayoutContainer = findViewById(R.id.main_activit5_linear_layout_container);
        productMemory = findViewById(R.id.main_activit5_memory_ram);
        productStorage = findViewById(R.id.main_activit5_storage_capasity);
        productDescription = findViewById(R.id.main_activit5_text_description);
        productBarRating = findViewById(R.id.main_activit5_text_ratingbar);
        productBarValue = findViewById(R.id.main_activit5_rating_value);
        addCart = findViewById(R.id.main_activit5_add_cart);
        setLocation = findViewById(R.id.main_activit5_select_location);
        textViewStorageTxt = findViewById(R.id.main_activit5_storage_capasity_txt);
        textViewMemoryTxt = findViewById(R.id.main_activit5_memory_ram_txt);
        confirmOrder = findViewById(R.id.main_activit5_confirm_orde);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        addCart.setOnClickListener(v -> openDialog(productId));

        setLocation.setOnClickListener(v -> {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            try {
                startActivityForResult(builder.build(MainActivity5.this), PLACE_PICKER_REQUEST);
            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }
        });

        confirmOrder.setOnClickListener(v -> addProduct());

        initData();
    }

    private void openDialog(String id) {
        ProductDialog productDialog = new ProductDialog(id, MainActivity5.this);
        productDialog.show(getSupportFragmentManager(), "Product Nubmer");
    }

    @SuppressLint("SetTextI18n")
    private void initData() {
        if (getIntent().getStringExtra(MainActivity3.PARAMETER2).equals(getString(R.string.mobile_firebase))) {
            MobileDetails mobileDetails = getIntent().getParcelableExtra(MainActivity3.PARAMETER1);
            Log.d(TAG, "onCreate: 1111 " + mobileDetails.getName());
            linearLayoutContainer.setVisibility(View.VISIBLE);
            textViewStorageTxt.setVisibility(View.VISIBLE);
            productStorage.setVisibility(View.VISIBLE);
            textViewMemoryTxt.setText(getString(R.string.memory_of_ram));
            Picasso.with(MainActivity5.this)
                    .load(mobileDetails.getImage())
                    .into(productImageView);

            productId = mobileDetails.getKey();
            productName.setText(mobileDetails.getDesc1());
            productPrice.setText(mobileDetails.getPrice() + "EGP");
            productDescription.setText(mobileDetails.getDesc2());
            productStorage.setText(mobileDetails.getStorageCapacity());
            productMemory.setText(mobileDetails.getMemoryRam());
            productBarValue.setText(mobileDetails.getRating());
            productBarRating.setRating(Float.parseFloat(mobileDetails.getRating()) / 2.0f);

            productImage = mobileDetails.getImage();
            productNameTxt = mobileDetails.getDesc1();
            productPriceTxt = mobileDetails.getPrice();
        } else if (getIntent().getStringExtra(MainActivity3.PARAMETER2).equals(getString(R.string.fashion_firebase))) {
            FashionDetails fashionDetails = getIntent().getParcelableExtra(MainActivity3.PARAMETER1);
            Log.d(TAG, "initData: 1111 " + fashionDetails.getCategouryId() + " " + fashionDetails.getName());
            Picasso.with(MainActivity5.this)
                    .load(fashionDetails.getImage())
                    .into(productImageView);

            linearLayoutContainer.setVisibility(View.GONE);
            productId = fashionDetails.getKey();
            productName.setText(fashionDetails.getName());
            productPrice.setText(fashionDetails.getPrice() + "EGP");
            productDescription.setText(fashionDetails.getDesc2());
            productBarValue.setText(fashionDetails.getRating());
            productBarRating.setRating(Float.parseFloat(fashionDetails.getRating()) / 2.0f);
            productImage = fashionDetails.getImage();
            productNameTxt = fashionDetails.getDesc1();
            productPriceTxt = fashionDetails.getPrice();

        } else if (getIntent().getStringExtra(MainActivity3.PARAMETER2).equals(getString(R.string.labtop_firebase))) {
            linearLayoutContainer.setVisibility(View.VISIBLE);
            textViewStorageTxt.setVisibility(View.INVISIBLE);
            productStorage.setVisibility(View.INVISIBLE);
            textViewMemoryTxt.setText("Hard Disk");

            LabtopDetails labtopDetails = getIntent().getParcelableExtra(MainActivity3.PARAMETER1);
            Log.d(TAG, "initData: 1111 " + labtopDetails.getCategouryId() + " " + labtopDetails.getName());
            Picasso.with(MainActivity5.this)
                    .load(labtopDetails.getImage())
                    .into(productImageView);

            productId = labtopDetails.getKey();
            productName.setText(labtopDetails.getName());
            productPrice.setText(labtopDetails.getPrice() + "EGP");
            productDescription.setText(labtopDetails.getDesc2());
            productBarValue.setText(labtopDetails.getRating());
            productBarRating.setRating(Float.parseFloat(labtopDetails.getRating()) / 2.0f);
            productMemory.setText(labtopDetails.getHardDisk());
            productImage = labtopDetails.getImage();
            productNameTxt = labtopDetails.getDesc1();
            productPriceTxt = labtopDetails.getPrice();

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK && data != null) {
                Place place = PlacePicker.getPlace(data, MainActivity5.this);
                latitude = String.valueOf(place.getLatLng().latitude);
                longitude = String.valueOf(place.getLatLng().longitude);
            }
        }

    }

    @Override
    public void getQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.product_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.product_home_correct) {
            Log.d(TAG, "onOptionsItemSelected: 1111 checked");
            addProduct();
        }
        return super.onOptionsItemSelected(item);
    }

    private void addProduct() {

        if(quantity>0) {
            Calendar c = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss");
            //String reg_date = df.format(c.getTime());
            c.add(Calendar.DATE, 5);  // number of days to add
            String deliverData = df.format(c.getTime());

            ConfirmOrder confirmOrder = new ConfirmOrder(productId, productImage, String.valueOf(quantity),
                    productPriceTxt, latitude, longitude, productNameTxt, deliverData);

            Log.d(TAG, "addProduct: **** "+confirmOrder.getProductDeliverDate());
            Intent intent = new Intent(MainActivity5.this, MainActivity4.class);
            intent.putExtra(PARAMTER1, confirmOrder);
            startActivityForResult(intent, OPEN_ACTIVITY);
        }
        else {
            Toast.makeText(this, "Please Determine the number of product", Toast.LENGTH_SHORT).show();
        }
    }
}