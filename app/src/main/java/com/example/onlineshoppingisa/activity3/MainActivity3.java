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

import com.example.onlineshoppingisa.CaptureAct;
import com.example.onlineshoppingisa.MainActivity;
import com.example.onlineshoppingisa.MainActivity5;
import com.example.onlineshoppingisa.R;
import com.example.onlineshoppingisa.adapter.ProductAdapter;
import com.example.onlineshoppingisa.adapter.ProductAdapterGroup;
import com.example.onlineshoppingisa.adapter.ProductTypeAdapter;
import com.example.onlineshoppingisa.fragment.HomeFragment;
import com.example.onlineshoppingisa.fragment.ModifiadCartViewFragment;
import com.example.onlineshoppingisa.fragment.MyCartFeagment;
import com.example.onlineshoppingisa.models.AllCategory;
import com.example.onlineshoppingisa.models.ConfirmOrder;
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

    public static final String PARAMTER1 = "com.example.onlineshoppingisa.activity3.MainActivity3";
    public static final String PARAMTER2 = "com.example.onlineshoppingisa.MainActivity31";
    public static final int REQUEST_CODE_NEWINTENT = 1;
    private static final String TAG = "MainActivity3";
    private static final int REQUEST_SPEECH_INPUT = 1000;
    ProductDataBase productDataBase;
    //ui
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private RecyclerView productTypeRecyclerView;
    private NavigationView navigationView;
    //variable
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private MenuItem menuItem;
    private MenuItem menuItem1;
    private MenuItem menuItem2;
    private androidx.appcompat.widget.SearchView searchView;
    private SharedPreferences sharedPreferences;
    private MainActivity3Model mainActivity3Model;
    private ArrayList<MobileDetails> mobileDetailsArrayList;
    private ArrayList<FashionDetails> fashionDetailsArrayList;
    private ArrayList<ProductType> productTypeArrayList;
    private ArrayList<LabtopDetails> labtopDetailsArrayList;
    private ProductTypeAdapter productTypeAdapter;
    private String selectCategtory;
    private List<ProductDetailCardView> allProductDetailCardViews;
    private List<ProductDetailCardView> mobileProductDetailCardViews;
    private List<ProductDetailCardView> labtopProductDetailCardViews;
    private List<ProductDetailCardView> fashionProductDetailCardViews;
    private List<ProductDetailCardViewGroup> productDetailCardViewGroups;
    private List<ProductDetailCardViewGroup> productUserDetailCardViewGroups;
    private ProductDataBaseModel productDataBaseModel;
    private HomeFragment homeFragment;
    private MyCartFeagment myCartFeagment;
    private List<ProductRoom> userProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        sharedPreferences = getSharedPreferences("rememberMe", MODE_PRIVATE);

        //initail variable
        mobileDetailsArrayList = new ArrayList<>();
        fashionDetailsArrayList = new ArrayList<>();
        productTypeArrayList = new ArrayList<>();
        labtopDetailsArrayList = new ArrayList<>();
        mainActivity3Model = ViewModelProviders.of(this).get(MainActivity3Model.class);
        productDataBase = ProductDataBase.grtInstance(MainActivity3.this);

        productDataBaseModel = ViewModelProviders.of(MainActivity3.this).get(ProductDataBaseModel.class);
        selectCategtory = "All";

        allProductDetailCardViews = new ArrayList<>();
        mobileProductDetailCardViews = new ArrayList<>();
        labtopProductDetailCardViews = new ArrayList<>();
        fashionProductDetailCardViews = new ArrayList<>();
        productDetailCardViewGroups = new ArrayList<>();

        userProduct = new ArrayList<>();

        productUserDetailCardViewGroups = new ArrayList<>();

        //initail xml
        productTypeRecyclerView = findViewById(R.id.main_activity3_recycler_view_product_type);
        toolbar = findViewById(R.id.drawerlayout_toolbar);
        drawerLayout = findViewById(R.id.drawerlayout_id);
        navigationView = findViewById(R.id.drawerlayout_nav_view);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        productDataBaseModel.getLiveDataOfUser(firebaseAuth.getCurrentUser().getUid()).observe(this, new Observer<List<ProductRoom>>() {
            @Override
            public void onChanged(List<ProductRoom> productRooms) {
                userProduct = productRooms;
            }
        });

        mainActivity3Model.getLiveData().observe(this, new Observer<AllCategory>() {
            @Override
            public void onChanged(AllCategory allCategory) {
                mobileDetailsArrayList = new ArrayList<>(allCategory.getMobileDetailsList());
                fashionDetailsArrayList = new ArrayList<>(allCategory.getFashionDetailsList());
                labtopDetailsArrayList = new ArrayList<>(allCategory.getLabtopDetails());
                productTypeArrayList = new ArrayList<>(allCategory.getProductTypeList());
                homeFragment = new HomeFragment(mobileDetailsArrayList, fashionDetailsArrayList, labtopDetailsArrayList);
                if (savedInstanceState == null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_activity3_fragment, homeFragment)
                            .commit();
                    navigationView.setCheckedItem(R.id.drawer_menu_home);
                }
                myCartFeagment = new MyCartFeagment(mobileDetailsArrayList, fashionDetailsArrayList, labtopDetailsArrayList);
                setDataForTypeAdapter();
            }
        });

    }

    private void setDataForTypeAdapter() {
        productTypeAdapter = new ProductTypeAdapter(productTypeArrayList, MainActivity3.this);
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
                            intent.putExtra(PARAMTER1, fashionDetails);
                            intent.putExtra(PARAMTER2, getString(R.string.fashion_firebase));
                            break;
                        }
                    }
                } else if (key.contains("labtop")) {
                    for (LabtopDetails labtopDetails : labtopDetailsArrayList) {
                        if (labtopDetails.getKey().equals(key)) {
                            intent.putExtra(PARAMTER1, labtopDetails);
                            intent.putExtra(PARAMTER2, getString(R.string.labtop_firebase));
                            break;
                        }
                    }

                } else if (key.contains("mobile")) {
                    for (MobileDetails mobileDetails : mobileDetailsArrayList) {
                        if (mobileDetails.getKey().equals(key)) {
                            intent.putExtra(PARAMTER1, mobileDetails);
                            intent.putExtra(PARAMTER2, getString(R.string.mobile_firebase));
                            break;
                        }
                    }
                }
                startActivityForResult(intent, REQUEST_CODE_NEWINTENT);
            }
        }
    }

    @Override
    public void productTypeAdapterSetOnItemClick(int pos) {
        selectCategtory = productTypeArrayList.get(pos).getType();
        initailProductDetailCardViews();
        productDetailCardViewGroups = new ArrayList<>();
        if (selectCategtory.equals("All")) {
            productDetailCardViewGroups.add(new ProductDetailCardViewGroup(getString(R.string.mobile_firebase), mobileProductDetailCardViews));
            productDetailCardViewGroups.add(new ProductDetailCardViewGroup(getString(R.string.fashion_firebase), fashionProductDetailCardViews));
            productDetailCardViewGroups.add(new ProductDetailCardViewGroup(getString(R.string.labtop_firebase), labtopProductDetailCardViews));
            homeFragment.setDataForProdructAdapter(productDetailCardViewGroups);
        } else if (selectCategtory.equals(getString(R.string.mobile_firebase))) {
            productDetailCardViewGroups.add(new ProductDetailCardViewGroup(getString(R.string.mobile_firebase), mobileProductDetailCardViews));
            homeFragment.setDataForProdructAdapter(productDetailCardViewGroups);
        } else if (selectCategtory.equals(getString(R.string.fashion_firebase))) {
            productDetailCardViewGroups.add(new ProductDetailCardViewGroup(getString(R.string.fashion_firebase), fashionProductDetailCardViews));
            homeFragment.setDataForProdructAdapter(productDetailCardViewGroups);
        } else if (selectCategtory.equals(getString(R.string.labtop_firebase))) {
            productDetailCardViewGroups.add(new ProductDetailCardViewGroup(getString(R.string.labtop_firebase), labtopProductDetailCardViews));
            homeFragment.setDataForProdructAdapter(productDetailCardViewGroups);
        }

    }

    private void initailProductDetailCardViews() {
        mobileProductDetailCardViews.clear();
        fashionProductDetailCardViews.clear();
        labtopProductDetailCardViews.clear();
        allProductDetailCardViews.clear();
        for (int i = 0; i < 10; i++) {
            MobileDetails mobileDetails = mobileDetailsArrayList.get(i);
            LabtopDetails labtopDetails = labtopDetailsArrayList.get(i);
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
            labtopProductDetailCardViews.add(labtopItem);

        }
    }

    @Override
    public void ProductAdapterGropSetOnClicked(int pos) {
        Log.d(TAG, "ProductAdapterGropSetOnClicked: 1111 " + pos);
    }

    @Override
    public void productAdapterSetOnItemClickListener(ProductDetailCardView productDetailCardView, int pos) {
        if (getSupportFragmentManager().findFragmentById(R.id.main_activity3_fragment) instanceof HomeFragment) {
            Intent intent = new Intent(MainActivity3.this, MainActivity5.class);
            if (productDetailCardView.getProductType().equals(getString(R.string.mobile_firebase))) {
                for (MobileDetails mobileDetails : mobileDetailsArrayList) {
                    if (mobileDetails.getKey().equals(productDetailCardView.getProductId())) {
                        intent.putExtra(PARAMTER1, mobileDetails);
                        break;
                    }
                }
            } else if (productDetailCardView.getProductType().equals(getString(R.string.fashion_firebase))) {
                for (FashionDetails fashionDetails : fashionDetailsArrayList) {
                    if (fashionDetails.getKey().equals(productDetailCardView.getProductId())) {
                        intent.putExtra(PARAMTER1, fashionDetails);
                        break;
                    }
                }
            } else if (productDetailCardView.getProductType().equals(getString(R.string.labtop_firebase))) {
                for (LabtopDetails labtopDetails : labtopDetailsArrayList) {
                    if (labtopDetails.getKey().equals(productDetailCardView.getProductId())) {
                        intent.putExtra(PARAMTER1, labtopDetails);
                        break;
                    }
                }
            }
            intent.putExtra(PARAMTER2, productDetailCardView.getProductType());
            startActivityForResult(intent, REQUEST_CODE_NEWINTENT);
        }

    }

    @Override
    public void productAdapterSetOnItemClickListenerCartFragment(ProductDetailCardView productDetailCardView, int pos) {
        if (getSupportFragmentManager().findFragmentById(R.id.main_activity3_fragment) instanceof MyCartFeagment) {
            ConfirmOrder confirmOrder = null;
            for (ProductRoom productRoom : userProduct) {
                if (productRoom.getProductId().equals(productDetailCardView.getProductId())) {
                    confirmOrder = productRoom.getConfirmOrder();
                }
            }
            ModifiadCartViewFragment modifiadCartViewFragment = new ModifiadCartViewFragment(confirmOrder);
            getSupportFragmentManager().beginTransaction().replace(R.id.main_activity3_fragment, modifiadCartViewFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.drawer_menu_home:
                navigationView.setCheckedItem(R.id.drawer_menu_home);
                HomeFragment homeFragment1 = new HomeFragment(mobileDetailsArrayList, fashionDetailsArrayList, labtopDetailsArrayList);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_activity3_fragment, homeFragment1)
                        .addToBackStack(null)
                        .commit();
                break;

            case R.id.my_cart:
                navigationView.setCheckedItem(R.id.my_cart);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_activity3_fragment, myCartFeagment)
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

    }
}