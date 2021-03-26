package com.example.onlineshoppingisa.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshoppingisa.R;
import com.example.onlineshoppingisa.adapter.ProductAdapterGroup;
import com.example.onlineshoppingisa.models.FashionDetails;
import com.example.onlineshoppingisa.models.LabtopDetails;
import com.example.onlineshoppingisa.models.MobileDetails;
import com.example.onlineshoppingisa.models.ProductDetailCardView;
import com.example.onlineshoppingisa.models.ProductDetailCardViewGroup;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {



    private ArrayList<MobileDetails> mobileDetailsArrayList;
    private ArrayList<FashionDetails> fashionDetailsArrayList;
    private ArrayList<LabtopDetails> laptopDetailsArrayList;

    public List<ProductDetailCardView> allProductDetailCardViews;
    private List<ProductDetailCardView> mobileProductDetailCardViews;
    private ProductAdapterGroup productAdapterGroup;
    private List<ProductDetailCardView> fashionProductDetailCardViews;
    private List<ProductDetailCardViewGroup> productDetailCardViewGroups;
    private List<ProductDetailCardView> laptopProductDetailCardViews;


    public HomeFragment() {
    }

    public HomeFragment(ArrayList<MobileDetails> mobileDetailsArrayList, ArrayList<FashionDetails> fashionDetailsArrayList,
                        ArrayList<LabtopDetails> laptopDetailsArrayList) {
        this.mobileDetailsArrayList = mobileDetailsArrayList;
        this.fashionDetailsArrayList = fashionDetailsArrayList;
        this.laptopDetailsArrayList = laptopDetailsArrayList;

        allProductDetailCardViews = new ArrayList<>();
        mobileProductDetailCardViews = new ArrayList<>();
        laptopProductDetailCardViews = new ArrayList<>();
        fashionProductDetailCardViews = new ArrayList<>();
        productDetailCardViewGroups = new ArrayList<>();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        //xml
        RecyclerView recyclerView = view.findViewById(R.id.home_fragment_product);
        productAdapterGroup = new ProductAdapterGroup(getActivity(), productDetailCardViewGroups);
        recyclerView.setAdapter(productAdapterGroup);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        initialProductDetailCardViews(getActivity(),mobileDetailsArrayList,fashionDetailsArrayList, laptopDetailsArrayList);
        return view;
    }

    public void initialProductDetailCardViews(Context context, ArrayList<MobileDetails> mobileDetailsArrayList, ArrayList<FashionDetails> fashionDetailsArrayList, ArrayList<LabtopDetails> labtopDetailsArrayList) {
        for (int i = 0; i < 10; i++) {
            MobileDetails mobileDetails = mobileDetailsArrayList.get(i);
            LabtopDetails labtopDetails = labtopDetailsArrayList.get(i);
            FashionDetails fashionDetails = fashionDetailsArrayList.get(i);

            ProductDetailCardView mobileItem = new ProductDetailCardView(mobileDetails.getKey(),context.getString(R.string.mobile_firebase), mobileDetails.getName(), mobileDetails.getPrice(), mobileDetails.getRating(),
                    mobileDetails.getImage());

            ProductDetailCardView fashionItem = new ProductDetailCardView(fashionDetails.getKey(),context.getString(R.string.fashion_firebase), fashionDetails.getName(), fashionDetails.getPrice(), fashionDetails.getRating(),
                    fashionDetails.getImage());

            ProductDetailCardView labtopItem = new ProductDetailCardView(labtopDetails.getKey(),context.getString(R.string.labtop_firebase), labtopDetails.getName(), labtopDetails.getPrice(), labtopDetails.getRating(),
                    labtopDetails.getImage());

            allProductDetailCardViews.add(mobileItem);
            allProductDetailCardViews.add(fashionItem);
            allProductDetailCardViews.add(labtopItem);

            mobileProductDetailCardViews.add(mobileItem);
            fashionProductDetailCardViews.add(fashionItem);
            laptopProductDetailCardViews.add(labtopItem);
        }
        createAdapterGroup(context);
    }

    private void createAdapterGroup(Context context) {
        productDetailCardViewGroups.add(new ProductDetailCardViewGroup(context.getString(R.string.mobile_firebase), mobileProductDetailCardViews));
        productDetailCardViewGroups.add(new ProductDetailCardViewGroup(context.getString(R.string.fashion_firebase), fashionProductDetailCardViews));
        productDetailCardViewGroups.add(new ProductDetailCardViewGroup(context.getString(R.string.labtop_firebase), laptopProductDetailCardViews));
        setDataForProductAdapter(productDetailCardViewGroups);
    }

    public void setDataForProductAdapter(List<ProductDetailCardViewGroup> productDetailCardViewGroups) {
        productAdapterGroup.setList(productDetailCardViewGroups);
    }

    public ProductAdapterGroup getProductAdapterGroup() {
        return productAdapterGroup;
    }
}
