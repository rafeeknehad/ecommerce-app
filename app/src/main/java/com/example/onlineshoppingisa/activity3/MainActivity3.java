package com.example.onlineshoppingisa.activity3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshoppingisa.BottomSheetDialog;
import com.example.onlineshoppingisa.CaptureAct;
import com.example.onlineshoppingisa.MainActivity;
import com.example.onlineshoppingisa.MainActivity5;
import com.example.onlineshoppingisa.R;
import com.example.onlineshoppingisa.adapter.ProductAdapter;
import com.example.onlineshoppingisa.adapter.ProductAdapterGroup;
import com.example.onlineshoppingisa.adapter.ProductTypeAdapter;
import com.example.onlineshoppingisa.fragment.HomeFragment;
import com.example.onlineshoppingisa.fragment.MyCartFragment;
import com.example.onlineshoppingisa.fragment.MyOrderFragment;
import com.example.onlineshoppingisa.models.FashionDetails;
import com.example.onlineshoppingisa.models.LabtopDetails;
import com.example.onlineshoppingisa.models.MobileDetails;
import com.example.onlineshoppingisa.models.ProductDetailCardView;
import com.example.onlineshoppingisa.models.ProductDetailCardViewGroup;
import com.example.onlineshoppingisa.models.ProductType;
import com.example.onlineshoppingisa.roomdatabase.ProductDataBase;
import com.example.onlineshoppingisa.roomdatabase.ProductDataBaseModel;
import com.example.onlineshoppingisa.roomdatabase.ProductRoom;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity3 extends AppCompatActivity implements ProductAdapter.ProductAdapterInterface, ProductTypeAdapter.ProductTypeAdapterInterface,
        ProductAdapterGroup.ProductAdapterGroupInterface, NavigationView.OnNavigationItemSelectedListener {

    public static final String PARAMETER1 = "com.example.onlineShoppingIsa.activity3.MainActivity3";
    public static final String PARAMETER2 = "com.example.onlineShoppingIsa.MainActivity31";
    public static final int REQUEST_CODE_NEW_INTENT = 1;
    private static final String TAG = "MainActivity3";
    private static final int REQUEST_SPEECH_INPUT = 1000;
    ProductDataBase productDataBase;
    private DrawerLayout drawerLayout;
    private RecyclerView productTypeRecyclerView;
    private NavigationView navigationView;
    //variable
    private FirebaseAuth firebaseAuth;
    private MenuItem menuItem;
    private MenuItem menuItem1;
    private MenuItem menuItem2;
    private androidx.appcompat.widget.SearchView searchView;
    private SharedPreferences sharedPreferences;
    private ArrayList<MobileDetails> mobileDetailsArrayList;
    private ArrayList<FashionDetails> fashionDetailsArrayList;
    private ArrayList<ProductType> productTypeArrayList;
    private ArrayList<LabtopDetails> laptopDetailsArrayList;
    private String selectCategory;
    private List<ProductDetailCardView> allProductDetailCardViews;
    private List<ProductDetailCardView> mobileProductDetailCardViews;
    private List<ProductDetailCardView> laptopProductDetailCardViews;
    private List<ProductDetailCardView> fashionProductDetailCardViews;
    private List<ProductDetailCardViewGroup> productDetailCardViewGroups;
    private HomeFragment homeFragment;
    private MyCartFragment myCartFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        sharedPreferences = getSharedPreferences("rememberMe", MODE_PRIVATE);

        //initail variable
        mobileDetailsArrayList = new ArrayList<>();
        fashionDetailsArrayList = new ArrayList<>();
        productTypeArrayList = new ArrayList<>();
        laptopDetailsArrayList = new ArrayList<>();
        MainActivity3Model mainActivity3Model = ViewModelProviders.of(this).get(MainActivity3Model.class);
        productDataBase = ProductDataBase.grtInstance(MainActivity3.this);

        ProductDataBaseModel productDataBaseModel = ViewModelProviders.of(MainActivity3.this).get(ProductDataBaseModel.class);
        selectCategory = "All";

        allProductDetailCardViews = new ArrayList<>();
        mobileProductDetailCardViews = new ArrayList<>();
        laptopProductDetailCardViews = new ArrayList<>();
        fashionProductDetailCardViews = new ArrayList<>();
        productDetailCardViewGroups = new ArrayList<>();

        //initail xml
        productTypeRecyclerView = findViewById(R.id.main_activity3_recycler_view_product_type);
        //ui
        Toolbar toolbar = findViewById(R.id.drawerlayout_toolbar);
        drawerLayout = findViewById(R.id.drawerlayout_id);
        navigationView = findViewById(R.id.drawerlayout_nav_view);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        productDataBaseModel.getLiveDataOfUser(firebaseAuth.getCurrentUser().getUid()).observe(this, new Observer<List<ProductRoom>>() {
            @Override
            public void onChanged(List<ProductRoom> productRooms) {
            }
        });

        myCartFragment = new MyCartFragment();
        mainActivity3Model.getLiveData().observe(this, allCategory -> {
            Log.d(TAG, "onChanged: .... " + allCategory.getLabtopDetails().size());
            Toast.makeText(getApplicationContext(), "" + allCategory.getLabtopDetails().size(), Toast.LENGTH_LONG).show();
            mobileDetailsArrayList = new ArrayList<>(allCategory.getMobileDetailsList());
            fashionDetailsArrayList = new ArrayList<>(allCategory.getFashionDetailsList());
            laptopDetailsArrayList = new ArrayList<>(allCategory.getLabtopDetails());
            productTypeArrayList = new ArrayList<>(allCategory.getProductTypeList());
            homeFragment = new HomeFragment(mobileDetailsArrayList, fashionDetailsArrayList, laptopDetailsArrayList);
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.main_activity3_fragment, homeFragment)
                        .commit();
                navigationView.setCheckedItem(R.id.drawer_menu_home);
            }
            myCartFragment = new MyCartFragment(mobileDetailsArrayList, fashionDetailsArrayList, laptopDetailsArrayList);
            setDataForTypeAdapter();
        });

    }

    private void setDataForTypeAdapter() {
        ProductTypeAdapter productTypeAdapter = new ProductTypeAdapter(productTypeArrayList, MainActivity3.this);
        productTypeRecyclerView.setHasFixedSize(true);
        productTypeRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity3.this, RecyclerView.HORIZONTAL, false));
        productTypeRecyclerView.setAdapter(productTypeAdapter);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.home_menu, menu);
        menuItem = menu.findItem(R.id.home_menu_search);
        menuItem1 = menu.findItem(R.id.home_menu_voice);
        menuItem2 = menu.findItem(R.id.home_menu_scan);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_menu_search:
                searchView = (androidx.appcompat.widget.SearchView) menuItem.getActionView();
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        HomeFragment.productAdapterGroup.getFilter().filter(newText);
                        return true;
                    }
                });
                MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        menuItem1.setVisible(true);
                        menuItem2.setVisible(true);
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        menuItem1.setVisible(false);
                        menuItem2.setVisible(false);
                        return true;
                    }
                });
                break;
            case R.id.home_menu_voice:
                speak();
                break;

            case R.id.home_menu_scan:
                scan();
                break;

            default:
                return false;
        }
        return true;
    }

    private void signOut() {
        Log.d(TAG, "signOut: **** ");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        finish();
        Intent intent = new Intent(MainActivity3.this, MainActivity.class);
        startActivity(intent);
        firebaseAuth.signOut();
    }


    private void scan() {
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setCaptureActivity(CaptureAct.class);
        intentIntegrator.setOrientationLocked(false);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.setPrompt("Scanning Code");
        intentIntegrator.initiateScan();
    }

    private void speak() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak something");

        try {
            startActivityForResult(intent, REQUEST_SPEECH_INPUT);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                searchView.setQuery(result.get(0), false);
                HomeFragment.productAdapterGroup.getFilter().filter(result.get(0));
            }
        }

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                String key = result.getContents().toString();
                Intent intent = new Intent(MainActivity3.this, MainActivity5.class);
                if (key.contains("Fashion")) {
                    for (FashionDetails fashionDetails : fashionDetailsArrayList) {
                        if (fashionDetails.getKey().equals(key)) {
                            intent.putExtra(PARAMETER1, fashionDetails);
                            intent.putExtra(PARAMETER2, getString(R.string.fashion_firebase));
                            break;
                        }
                    }
                } else if (key.contains("labtop")) {
                    for (LabtopDetails labtopDetails : laptopDetailsArrayList) {
                        if (labtopDetails.getKey().equals(key)) {
                            intent.putExtra(PARAMETER1, labtopDetails);
                            intent.putExtra(PARAMETER2, getString(R.string.labtop_firebase));
                            break;
                        }
                    }

                } else if (key.contains("mobile")) {
                    for (MobileDetails mobileDetails : mobileDetailsArrayList) {
                        if (mobileDetails.getKey().equals(key)) {
                            intent.putExtra(PARAMETER1, mobileDetails);
                            intent.putExtra(PARAMETER2, getString(R.string.mobile_firebase));
                            break;
                        }
                    }
                }
                startActivityForResult(intent, REQUEST_CODE_NEW_INTENT);
            }
        }
    }

    @Override
    public void productTypeAdapterSetOnItemClick(int pos) {
        selectCategory = productTypeArrayList.get(pos).getType();
        initailProductDetailCardViews();
        productDetailCardViewGroups = new ArrayList<>();
        if (selectCategory.equals("All")) {
            productDetailCardViewGroups.add(new ProductDetailCardViewGroup(getString(R.string.mobile_firebase), mobileProductDetailCardViews));
            productDetailCardViewGroups.add(new ProductDetailCardViewGroup(getString(R.string.fashion_firebase), fashionProductDetailCardViews));
            productDetailCardViewGroups.add(new ProductDetailCardViewGroup(getString(R.string.labtop_firebase), laptopProductDetailCardViews));
            homeFragment.setDataForProdructAdapter(productDetailCardViewGroups);
        } else if (selectCategory.equals(getString(R.string.mobile_firebase))) {
            productDetailCardViewGroups.add(new ProductDetailCardViewGroup(getString(R.string.mobile_firebase), mobileProductDetailCardViews));
            homeFragment.setDataForProdructAdapter(productDetailCardViewGroups);
        } else if (selectCategory.equals(getString(R.string.fashion_firebase))) {
            productDetailCardViewGroups.add(new ProductDetailCardViewGroup(getString(R.string.fashion_firebase), fashionProductDetailCardViews));
            homeFragment.setDataForProdructAdapter(productDetailCardViewGroups);
        } else if (selectCategory.equals(getString(R.string.labtop_firebase))) {
            productDetailCardViewGroups.add(new ProductDetailCardViewGroup(getString(R.string.labtop_firebase), laptopProductDetailCardViews));
            homeFragment.setDataForProdructAdapter(productDetailCardViewGroups);
        }

    }

    private void initailProductDetailCardViews() {
        mobileProductDetailCardViews.clear();
        fashionProductDetailCardViews.clear();
        laptopProductDetailCardViews.clear();
        allProductDetailCardViews.clear();
        for (int i = 0; i < 10; i++) {
            MobileDetails mobileDetails = mobileDetailsArrayList.get(i);
            LabtopDetails labtopDetails = laptopDetailsArrayList.get(i);
            FashionDetails fashionDetails = fashionDetailsArrayList.get(i);

            ProductDetailCardView mobileItem = new ProductDetailCardView(mobileDetails.getKey(), getString(R.string.mobile_firebase), mobileDetails.getName(), mobileDetails.getPrice(), mobileDetails.getRating(),
                    mobileDetails.getImage());

            ProductDetailCardView fashionItem = new ProductDetailCardView(fashionDetails.getKey(), getString(R.string.fashion_firebase), fashionDetails.getName(), fashionDetails.getPrice(), fashionDetails.getRating(),
                    fashionDetails.getImage());

            ProductDetailCardView labtopItem = new ProductDetailCardView(labtopDetails.getKey(), getString(R.string.labtop_firebase), labtopDetails.getName(), labtopDetails.getPrice(), labtopDetails.getRating(),
                    labtopDetails.getImage());

            allProductDetailCardViews.add(mobileItem);
            allProductDetailCardViews.add(fashionItem);
            allProductDetailCardViews.add(labtopItem);

            mobileProductDetailCardViews.add(mobileItem);
            fashionProductDetailCardViews.add(fashionItem);
            laptopProductDetailCardViews.add(labtopItem);

        }
    }

    @Override
    public void ProductAdapterGroupSetOnClicked(int pos) {
        Log.d(TAG, "ProductAdapterGropSetOnClicked: 1111 " + pos);
    }

    @Override
    public void productAdapterSetOnItemClickListener(ProductDetailCardView productDetailCardView, int pos) {
        if (getSupportFragmentManager().findFragmentById(R.id.main_activity3_fragment) instanceof HomeFragment) {
            Log.d(TAG, "productAdapterSetOnItemClickListener: ----- "+productDetailCardView.getProductId());
            BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(productDetailCardView.getProductId());
            mBottomSheetDialog.show(getSupportFragmentManager(), "BottomSheet");
        }

    }

    @Override
    public void productAdapterSetOnItemClickListenerCartFragment(ProductDetailCardView productDetailCardView, int pos) {
        if (getSupportFragmentManager().findFragmentById(R.id.main_activity3_fragment) instanceof MyCartFragment) {

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.drawer_menu_home:
                navigationView.setCheckedItem(R.id.drawer_menu_home);
                HomeFragment homeFragment1 = new HomeFragment(mobileDetailsArrayList, fashionDetailsArrayList, laptopDetailsArrayList);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_activity3_fragment, homeFragment1)
                        .addToBackStack(null)
                        .commit();
                break;

            case R.id.my_cart:
                navigationView.setCheckedItem(R.id.my_cart);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_activity3_fragment, myCartFragment)
                        .addToBackStack(null)
                        .commit();
                break;

            case R.id.sign_out:
                signOut();
                break;

            case R.id.my_order:
                showUserOrder();
                break;

        }
        return true;
    }

    private void showUserOrder() {
        MyOrderFragment myOrderFragment = new MyOrderFragment(MainActivity3.this);
        navigationView.setCheckedItem(R.id.my_order);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_activity3_fragment, myOrderFragment)
                .addToBackStack(null)
                .commit();
    }
}