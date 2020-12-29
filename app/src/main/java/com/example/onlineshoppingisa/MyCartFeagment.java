package com.example.onlineshoppingisa;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshoppingisa.models.FashionDetails;
import com.example.onlineshoppingisa.models.LabtopDetails;
import com.example.onlineshoppingisa.models.MobileDetails;
import com.example.onlineshoppingisa.models.ProductDetailCardView;
import com.example.onlineshoppingisa.models.ProductDetailCardViewGroup;
import com.example.onlineshoppingisa.roomdatabase.ProductDataBaseModel;
import com.example.onlineshoppingisa.roomdatabase.ProductRoom;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MyCartFeagment extends Fragment {

    private ProductDataBaseModel productDataBaseModel;
    private ArrayList<MobileDetails> mobileDetailsArrayList;
    private ArrayList<FashionDetails> fashionDetailsArrayList;
    private ArrayList<LabtopDetails> labtopDetailsArrayList;

    private List<ProductDetailCardView> allUserProductDetailCardViews;
    private List<ProductDetailCardView> mobileUserProductDetailCardViews;
    private List<ProductDetailCardView> labtopUserProductDetailCardViews;
    private List<ProductDetailCardView> fashionUserProductDetailCardViews;

    private List<ProductDetailCardViewGroup> productUserDetailCardViewGroups;

    private RecyclerView recyclerView;

    private ProductAdapterGroup productAdapterGroup;

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public MyCartFeagment(ArrayList<MobileDetails> mobileDetailsArrayList,ArrayList<FashionDetails> fashionDetailsArrayList,
                          ArrayList<LabtopDetails> labtopDetailsArrayList) {
        this.mobileDetailsArrayList = mobileDetailsArrayList;
        this.fashionDetailsArrayList = fashionDetailsArrayList;
        this.labtopDetailsArrayList = labtopDetailsArrayList;

        allUserProductDetailCardViews = new ArrayList<>();
        mobileUserProductDetailCardViews = new ArrayList<>();
        labtopUserProductDetailCardViews = new ArrayList<>();
        fashionUserProductDetailCardViews = new ArrayList<>();
        productUserDetailCardViewGroups = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_cart_fragment, container, false);
        recyclerView = view.findViewById(R.id.my_cart_fragment_recyclerview);
        showUserProduct(firebaseAuth.getCurrentUser().getUid(),getActivity());
        productAdapterGroup = new ProductAdapterGroup(getActivity(),productUserDetailCardViewGroups);
        recyclerView.setAdapter(productAdapterGroup);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        recyclerView.setHasFixedSize(true);
        return view;
    }

    private void showUserProduct(String uid, Context context) {
        productDataBaseModel = ViewModelProviders.of((FragmentActivity) context).get(ProductDataBaseModel.class);
        productDataBaseModel.getLiveDataOfUser(uid).observe((LifecycleOwner) context, new Observer<List<ProductRoom>>() {
            @Override
            public void onChanged(List<ProductRoom> productRooms) {
                System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkggg "+productRooms.size());
                for (ProductRoom productRoom : productRooms) {
                    if (productRoom.getProductId().contains("labtop")) {
                        for (LabtopDetails labtopDetails : labtopDetailsArrayList) {
                            if (labtopDetails.getKey().equals(productRoom.getProductId())) {
                                labtopUserProductDetailCardViews.add(new ProductDetailCardView(getString(R.string.labtop_firebase), labtopDetails.getName(),
                                        labtopDetails.getPrice(), labtopDetails.getRating(), labtopDetails.getImage()));
                                break;
                            }
                        }
                    }
                    else if (productRoom.getProductId().contains("mobile")) {
                        for (MobileDetails mobileDetails : mobileDetailsArrayList) {
                            if (mobileDetails.getKey().equals(productRoom.getProductId())) {
                                mobileUserProductDetailCardViews.add(new ProductDetailCardView(getString(R.string.mobile_firebase), mobileDetails.getName(),
                                        mobileDetails.getPrice(), mobileDetails.getRating(), mobileDetails.getImage()));
                                break;
                            }
                        }
                    }
                    else if (productRoom.getProductId().contains("Fashion")) {
                        for (FashionDetails fashionDetails : fashionDetailsArrayList) {
                            if (fashionDetails.getKey().equals(productRoom.getProductId())) {
                                fashionUserProductDetailCardViews.add(new ProductDetailCardView(getString(R.string.fashion_firebase), fashionDetails.getName(),
                                        fashionDetails.getPrice(), fashionDetails.getRating(), fashionDetails.getImage()));
                                break;
                            }
                        }
                    }
                }
                allUserProductDetailCardViews.addAll(mobileUserProductDetailCardViews);
                allUserProductDetailCardViews.addAll(labtopUserProductDetailCardViews);
                allUserProductDetailCardViews.addAll(fashionUserProductDetailCardViews);
                if (mobileUserProductDetailCardViews.size() != 0) {
                    productUserDetailCardViewGroups.add(new ProductDetailCardViewGroup(getString(R.string.mobile_firebase), mobileUserProductDetailCardViews));
                }
                if (fashionUserProductDetailCardViews.size() != 0) {
                    productUserDetailCardViewGroups.add(new ProductDetailCardViewGroup(getString(R.string.fashion_firebase), fashionUserProductDetailCardViews));
                }
                if (labtopUserProductDetailCardViews.size() != 0) {
                    productUserDetailCardViewGroups.add(new ProductDetailCardViewGroup(getString(R.string.labtop_firebase), labtopUserProductDetailCardViews));
                }
                System.out.println("nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnbbb "+mobileUserProductDetailCardViews.size()+"  "+
                        labtopUserProductDetailCardViews.size()+" "+fashionUserProductDetailCardViews.size());
                productAdapterGroup.notifyDataSetChanged();
            }
        });

    }
}
