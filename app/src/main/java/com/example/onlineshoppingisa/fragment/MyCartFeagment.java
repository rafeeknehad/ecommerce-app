package com.example.onlineshoppingisa.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshoppingisa.R;
import com.example.onlineshoppingisa.adapter.ProductAdapterGroup;
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
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MyCartFeagment extends Fragment {

    private static final String TAG = "MyCartFeagment";

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

    private Button buttonConfirm;

    public MyCartFeagment(ArrayList<MobileDetails> mobileDetailsArrayList, ArrayList<FashionDetails> fashionDetailsArrayList,
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
        buttonConfirm = view.findViewById(R.id.my_cart_fragment_btn);
        recyclerView = view.findViewById(R.id.my_cart_fragment_recyclerview);
        showUserProduct(firebaseAuth.getCurrentUser().getUid(), getActivity());
        productAdapterGroup = new ProductAdapterGroup(getActivity(), productUserDetailCardViewGroups);
        recyclerView.setAdapter(productAdapterGroup);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));


        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });
        return view;
    }

    private void sendEmail() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.host", "smtp.gmail.com");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", "false");
        properties.setProperty("mail.smtp.quitwait", "false");


        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("rafeek.nehad9823@gmail.com", "@Rafeek11111");
            }
        });

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("rafeek.nehad9823@gmail.com"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("rafeeknehad333@gmail.com"));
                message.setSubject("sending email");
                message.setText("hello");
                Transport.send(message);
                Toast.makeText(getActivity(), "Succsess", Toast.LENGTH_SHORT).show();

            } catch (MessagingException e) {
                Log.d(TAG, "sendEmail: 111111111111111111 " + e.getMessage().toString());
                e.printStackTrace();
            }


        }
    }

    private void showUserProduct(String uid, Context context) {
        allUserProductDetailCardViews = new ArrayList<>();
        mobileUserProductDetailCardViews = new ArrayList<>();
        labtopUserProductDetailCardViews = new ArrayList<>();
        fashionUserProductDetailCardViews = new ArrayList<>();
        productUserDetailCardViewGroups = new ArrayList<>();
        productDataBaseModel = ViewModelProviders.of((FragmentActivity) context).get(ProductDataBaseModel.class);
        productDataBaseModel.getLiveDataOfUser(uid).observe((LifecycleOwner) context, new Observer<List<ProductRoom>>() {
            @Override
            public void onChanged(List<ProductRoom> productRooms) {
                System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkggg " + productRooms.size());
                for (ProductRoom productRoom : productRooms) {
                    if (productRoom.getProductId().contains("labtop")) {
                        for (LabtopDetails labtopDetails : labtopDetailsArrayList) {
                            if (labtopDetails.getKey().equals(productRoom.getProductId())) {
                                labtopUserProductDetailCardViews.add(new ProductDetailCardView(labtopDetails.getKey(), getActivity().getString(R.string.labtop_firebase), labtopDetails.getName(),
                                        labtopDetails.getPrice(), labtopDetails.getRating(), labtopDetails.getImage()));
                                break;
                            }
                        }
                    } else if (productRoom.getProductId().contains("mobile")) {
                        for (MobileDetails mobileDetails : mobileDetailsArrayList) {
                            if (mobileDetails.getKey().equals(productRoom.getProductId())) {
                                mobileUserProductDetailCardViews.add(new ProductDetailCardView(mobileDetails.getKey(), getActivity().getString(R.string.mobile_firebase), mobileDetails.getName(),
                                        mobileDetails.getPrice(), mobileDetails.getRating(), mobileDetails.getImage()));
                                break;
                            }
                        }
                    } else if (productRoom.getProductId().contains("Fashion")) {
                        for (FashionDetails fashionDetails : fashionDetailsArrayList) {
                            if (fashionDetails.getKey().equals(productRoom.getProductId())) {
                                fashionUserProductDetailCardViews.add(new ProductDetailCardView(fashionDetails.getKey(), getActivity().getString(R.string.fashion_firebase), fashionDetails.getName(),
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
                productAdapterGroup.notifyDataSetChanged();
            }
        });

    }

}
