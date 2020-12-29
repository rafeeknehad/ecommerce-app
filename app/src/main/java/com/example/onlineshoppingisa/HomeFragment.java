package com.example.onlineshoppingisa;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshoppingisa.models.AllCategory;
import com.example.onlineshoppingisa.models.FashionDetails;
import com.example.onlineshoppingisa.models.LabtopDetails;
import com.example.onlineshoppingisa.models.MobileDetails;
import com.example.onlineshoppingisa.models.ProductDetailCardView;
import com.example.onlineshoppingisa.models.ProductDetailCardViewGroup;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    //xml
    private RecyclerView recyclerView;
    private androidx.appcompat.widget.SearchView searchView;
    private MenuItem menuItem;


    private MainActivity3Model mainActivity3Model;

    private ArrayList<MobileDetails> mobileDetailsArrayList;
    private ArrayList<FashionDetails> fashionDetailsArrayList;
    private ArrayList<LabtopDetails> labtopDetailsArrayList;

    private List<ProductDetailCardView> allProductDetailCardViews;
    private List<ProductDetailCardView> mobileProductDetailCardViews;
    private List<ProductDetailCardView> labtopProductDetailCardViews;
    private List<ProductDetailCardView> fashionProductDetailCardViews;
    private List<ProductDetailCardViewGroup> productDetailCardViewGroups;

    public static ProductAdapterGroup productAdapterGroup;



    public HomeFragment() {
        this.mobileDetailsArrayList = mobileDetailsArrayList;
        this.fashionDetailsArrayList = fashionDetailsArrayList;
        this.labtopDetailsArrayList = labtopDetailsArrayList;

        allProductDetailCardViews = new ArrayList<>();
        mobileProductDetailCardViews = new ArrayList<>();
        labtopProductDetailCardViews = new ArrayList<>();
        fashionProductDetailCardViews = new ArrayList<>();
        productDetailCardViewGroups = new ArrayList<>();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        recyclerView = view.findViewById(R.id.home_fragment_product);
        return view;
    }

    public void initailProductDetailCardViews(ArrayList<MobileDetails> mobileDetailsArrayList, ArrayList<FashionDetails> fashionDetailsArrayList, ArrayList<LabtopDetails> labtopDetailsArrayList) {
        for (int i = 0; i < 10; i++) {
            MobileDetails mobileDetails = mobileDetailsArrayList.get(i);
            LabtopDetails labtopDetails = labtopDetailsArrayList.get(i);
            FashionDetails fashionDetails = fashionDetailsArrayList.get(i);

            ProductDetailCardView mobileItem = new ProductDetailCardView(getString(R.string.mobile_firebase), mobileDetails.getName(), mobileDetails.getPrice(), mobileDetails.getRating(),
                    mobileDetails.getImage());

            ProductDetailCardView fashionItem = new ProductDetailCardView(getString(R.string.fashion_firebase), fashionDetails.getName(), fashionDetails.getPrice(), fashionDetails.getRating(),
                    fashionDetails.getImage());

            ProductDetailCardView labtopItem = new ProductDetailCardView(getString(R.string.labtop_firebase), labtopDetails.getName(), labtopDetails.getPrice(), labtopDetails.getRating(),
                    labtopDetails.getImage());

            allProductDetailCardViews.add(mobileItem);
            allProductDetailCardViews.add(fashionItem);
            allProductDetailCardViews.add(labtopItem);

            mobileProductDetailCardViews.add(mobileItem);
            fashionProductDetailCardViews.add(fashionItem);
            labtopProductDetailCardViews.add(labtopItem);
        }
        createAdapterGroup();
    }

    private void createAdapterGroup() {
        productDetailCardViewGroups.add(new ProductDetailCardViewGroup(getString(R.string.mobile_firebase), mobileProductDetailCardViews));
        productDetailCardViewGroups.add(new ProductDetailCardViewGroup(getString(R.string.fashion_firebase), fashionProductDetailCardViews));
        productDetailCardViewGroups.add(new ProductDetailCardViewGroup(getString(R.string.labtop_firebase), labtopProductDetailCardViews));
        setDataForProdructAdapter(productDetailCardViewGroups);
    }

    public void setDataForProdructAdapter(List<ProductDetailCardViewGroup> productDetailCardViewGroups) {
        System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkk "+productDetailCardViewGroups.size()+" "+productDetailCardViewGroups.get(0).getProductTypeList());
        productAdapterGroup = new ProductAdapterGroup(getActivity(), productDetailCardViewGroups);
        recyclerView.setAdapter(productAdapterGroup);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
    }


}
