package com.example.onlineshoppingisa.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshoppingisa.MainActivity;
import com.example.onlineshoppingisa.R;
import com.example.onlineshoppingisa.adapter.ProductAdapterGroup;
import com.example.onlineshoppingisa.models.FashionDetails;
import com.example.onlineshoppingisa.models.FlowChart;
import com.example.onlineshoppingisa.models.LabtopDetails;
import com.example.onlineshoppingisa.models.MobileDetails;
import com.example.onlineshoppingisa.models.OrderDetails;
import com.example.onlineshoppingisa.models.Orders;
import com.example.onlineshoppingisa.models.ProductDetailCardView;
import com.example.onlineshoppingisa.models.ProductDetailCardViewGroup;
import com.example.onlineshoppingisa.roomdatabase.ProductDataBaseModel;
import com.example.onlineshoppingisa.roomdatabase.ProductRoom;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import static java.lang.Integer.parseInt;

public class MyCartFragment extends Fragment {

    private static final String TAG = "MyCartFragment";
    private static final int PLACE_PICKER_REQUEST = 1;

    private List<MobileDetails> mobileDetailsArrayList;
    private List<FashionDetails> fashionDetailsArrayList;
    private List<LabtopDetails> laptopDetailsArrayList;
    public List<ProductDetailCardView> all;
    private List<ProductDetailCardView> mobileUserProductDetailCardViews;
    private List<ProductDetailCardView> laptopUserProductDetailCardViews;
    private List<ProductDetailCardView> fashionUserProductDetailCardViews;
    private List<ProductDetailCardViewGroup> productUserDetailCardViewGroups;
    private List<ProductRoom> mProductRoomList;

    public static ProductAdapterGroup productAdapterGroup;
    private ProductDataBaseModel mProductDataBaseModel;


    private String mUserId;
    private String mLocation;
    private String mOrderId;
    private String mUserProduct;
    private long totalPrice;
    private long totalPrice1;
    private TextView mTotalPrice;

    public MyCartFragment() {
        this.mobileDetailsArrayList = new ArrayList<>();
        this.fashionDetailsArrayList = new ArrayList<>();
        this.laptopDetailsArrayList = new ArrayList<>();

        all = new ArrayList<>();
        mobileUserProductDetailCardViews = new ArrayList<>();
        laptopUserProductDetailCardViews = new ArrayList<>();
        fashionUserProductDetailCardViews = new ArrayList<>();
        productUserDetailCardViewGroups = new ArrayList<>();

        mUserId = MainActivity.currentUser.getIdKey();
    }

    public MyCartFragment(ArrayList<MobileDetails> mobileDetailsArrayList, ArrayList<FashionDetails> fashionDetailsArrayList,
                          ArrayList<LabtopDetails> laptopDetailsArrayList) {
        this.mobileDetailsArrayList = mobileDetailsArrayList;
        this.fashionDetailsArrayList = fashionDetailsArrayList;
        this.laptopDetailsArrayList = laptopDetailsArrayList;

        all = new ArrayList<>();
        mobileUserProductDetailCardViews = new ArrayList<>();
        laptopUserProductDetailCardViews = new ArrayList<>();
        fashionUserProductDetailCardViews = new ArrayList<>();
        productUserDetailCardViewGroups = new ArrayList<>();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            mUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_cart_fragment, container, false);
        mProductDataBaseModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(ProductDataBaseModel.class);
        Button buttonConfirm = view.findViewById(R.id.my_cart_fragment_btn);
        Button buttonLocation = view.findViewById(R.id.my_cart_fragment_set_location);
        RecyclerView recyclerView = view.findViewById(R.id.my_cart_fragment_recyclerview);
        mTotalPrice = view.findViewById(R.id.my_cart_fragment_total_price);
        productUserDetailCardViewGroups = new ArrayList<>();
        productAdapterGroup = new ProductAdapterGroup(getActivity(), productUserDetailCardViewGroups);
        recyclerView.setAdapter(productAdapterGroup);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        showUserProduct();
        buttonConfirm.setOnClickListener(v -> setOrder());
        buttonLocation.setOnClickListener(v -> setLocation());
        return view;
    }

    private void setLocation() {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(Objects.requireNonNull(getActivity())), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    private void sendEmail() {
        DecimalFormat format = new DecimalFormat("###,###,###.00");
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");


        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("rafeek.nehad9823@gmail.com", "fhwghlfjlejxtkrx");
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
                message.setSubject("Confirm Order");
                mUserProduct = "Order Id: " + mOrderId + "\n" + mUserProduct;
                mUserProduct = mUserProduct + "\n" + "Total Order Price: " + format.format(totalPrice) + "$";
                message.setText(mUserProduct);
                Transport.send(message);

            } catch (MessagingException e) {
                e.printStackTrace();
            }

        }
    }

    @SuppressLint("SetTextI18n")
    public void showUserProduct() {
        mUserProduct = "";
        totalPrice = 0;
        DecimalFormat format = new DecimalFormat("###,###,###.00");
        productUserDetailCardViewGroups = new ArrayList<>();
        mobileUserProductDetailCardViews = new ArrayList<>();
        fashionUserProductDetailCardViews = new ArrayList<>();
        laptopUserProductDetailCardViews = new ArrayList<>();
        mProductDataBaseModel.getLiveDataOfUser(mUserId).observe(Objects.requireNonNull(getActivity()), productRooms -> {
            mProductRoomList = productRooms;
            for (ProductRoom item : productRooms) {
                if (item.getProductId().contains("mobile")) {
                    for (MobileDetails mobile : mobileDetailsArrayList) {
                        if (item.getProductId().equals(mobile.getKey())) {
                            totalPrice += parseInt(mobile.getPrice()) * item.getQuantity();
                            mUserProduct += "Name: " + mobile.getName() + "\n" + "Quantity: " + item.getQuantity() + "\n" +
                                    "Total price: " + format.format(parseInt(mobile.getPrice()) * item.getQuantity())
                                    + "$" + "\n-----------------------------------------------------------------";
                            mobileUserProductDetailCardViews.add(new ProductDetailCardView(item.getProductId(),
                                    getActivity().getResources().getString(R.string.mobile_firebase),
                                    mobile.getName(), mobile.getPrice(), mobile.getRating(),
                                    mobile.getImage()));
                            break;
                        }
                    }
                } else if (item.getProductId().contains("labtop")) {
                    for (LabtopDetails laptop : laptopDetailsArrayList) {
                        if (item.getProductId().equals(laptop.getKey())) {
                            totalPrice += parseInt(laptop.getPrice()) * item.getQuantity();
                            mUserProduct = mUserProduct + ("Name: " + laptop.getName() + "\n" + "Quantity: " + item.getQuantity() + "\n" +
                                    "Total price: " + format.format(parseInt(laptop.getPrice()) * item.getQuantity())
                                    + "$" + "\n-----------------------------------------------------------------");

                            laptopUserProductDetailCardViews.add(new ProductDetailCardView(item.getProductId(),
                                    getActivity().getResources().getString(R.string.labtop_firebase),
                                    laptop.getName(), laptop.getPrice(), laptop.getRating(),
                                    laptop.getImage()));
                            break;
                        }
                    }
                } else {
                    for (FashionDetails fashion : fashionDetailsArrayList) {
                        if (item.getProductId().equals(fashion.getKey())) {
                            totalPrice += parseInt(fashion.getPrice()) * item.getQuantity();
                            mUserProduct = mUserProduct + ("Name: " + fashion.getName() + "\n" +
                                    "Quantity: " + item.getQuantity() + "\n" +
                                    "Total price: " + format.format(parseInt(fashion.getPrice()) * item.getQuantity())
                                    + "$" + "\n-----------------------------------------------------------------");

                            fashionUserProductDetailCardViews.add(new ProductDetailCardView(item.getProductId(),
                                    getActivity().getResources().getString(R.string.fashion_firebase),
                                    fashion.getName(), fashion.getPrice(), fashion.getRating(),
                                    fashion.getImage()));
                            break;
                        }
                    }
                }
            }
            mTotalPrice.setText("Total Price: " + format.format(totalPrice) + " $");
            all.addAll(mobileUserProductDetailCardViews);
            all.addAll(laptopUserProductDetailCardViews);
            all.addAll(fashionUserProductDetailCardViews);
            if (mobileUserProductDetailCardViews.size() != 0)
                productUserDetailCardViewGroups.add(new ProductDetailCardViewGroup(getActivity().getString(R.string.mobile_firebase), mobileUserProductDetailCardViews));
            if (fashionUserProductDetailCardViews.size() != 0)
                productUserDetailCardViewGroups.add(new ProductDetailCardViewGroup(getActivity().getString(R.string.fashion_firebase), fashionUserProductDetailCardViews));
            if (laptopUserProductDetailCardViews.size() != 0)
                productUserDetailCardViewGroups.add(new ProductDetailCardViewGroup(getActivity().getString(R.string.labtop_firebase), laptopUserProductDetailCardViews));
            productAdapterGroup.setList(productUserDetailCardViewGroups);
        });
    }

    private void setOrder() {
        /*Calendar c = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss");
        c.add(Calendar.DATE, 3);  // number of days to add
        String deliverData = df.format(c.getTime());
        */
        Orders order = new Orders(mUserId, mLocation);
        FirebaseFirestore.getInstance()
                .collection("Orders")
                .document(mUserId)
                .collection("Order")
                .add(order)
                .addOnSuccessListener(documentReference -> {
                    mOrderId = documentReference.getId();
                    addToFlowChartRep();
                    setOrderDetails();
                })
                .addOnFailureListener(e -> {

                });
    }

    private void addToFlowChartRep() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        Log.d(TAG, "addToFlowChartRep: 0000 " + month);
        FirebaseFirestore.getInstance()
                .collection("FlowChart")
                .document(MainActivity.currentUser.getUserAuthId())
                .collection("Orders")
                .add(new FlowChart(mOrderId))
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Log.d(TAG, "onComplete: ///// "+task.isSuccessful());
                    }
                });
    }

    private void setOrderDetails() {
        Log.d(TAG, "setOrderDetails: ..... " + mOrderId);
        CollectionReference collectionReference = FirebaseFirestore
                .getInstance()
                .collection("OrderDetails")
                .document(mUserId)
                .collection("OrderDetails");
        for (ProductRoom productRoom : mProductRoomList) {
            OrderDetails orderDetails = new OrderDetails(productRoom.getProductId(),
                    mOrderId,
                    productRoom.getQuantity().toString());

            collectionReference.add(orderDetails)
                    .addOnSuccessListener(documentReference -> confirmOrder())
                    .addOnFailureListener(e -> {

                    });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == -1 && data != null) {
                Place place = PlacePicker.getPlace(data, Objects.requireNonNull(getActivity()));
                String x = String.valueOf(place.getLatLng().latitude);
                String y = String.valueOf(place.getLatLng().longitude);
                getLocation(x, y);
            }
        }
    }

    private void getLocation(String x, String y) {
        Geocoder geocoder;
        geocoder = new Geocoder(getActivity(), Locale.getDefault());
        List<Address> addresses = new ArrayList<>();
        try {
            addresses = geocoder.getFromLocation(Double.parseDouble(x), Double.parseDouble(y), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }
        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName();
        String location = "";
        if (address != null) location += address + ",";
        if (city != null) location += city + ",";
        if (state != null) location += state + ",";
        if (country != null) location += country + ",";
        if (postalCode != null) location += postalCode + ",";
        if (knownName != null) location += knownName + ",";

        mLocation = location;
    }

    private void confirmOrder() {
        sendEmail();
        mProductDataBaseModel.deleteAllOrderFromUser(mUserId);
        showUserProduct();
    }

    public void setDataForProductAdapter(String type) {
        totalPrice1 = 0;
        DecimalFormat format = new DecimalFormat("###,###,###.00");
        productUserDetailCardViewGroups = new ArrayList<>();
        mobileUserProductDetailCardViews = new ArrayList<>();
        fashionUserProductDetailCardViews = new ArrayList<>();
        laptopUserProductDetailCardViews = new ArrayList<>();
        mProductDataBaseModel.getLiveDataOfUser(mUserId).observe(Objects.requireNonNull(getActivity()), productRooms -> {
            mProductRoomList = productRooms;
            for (ProductRoom item : productRooms) {
                Log.d(TAG, "setDataForProductAdapter: 000011 " + type + " " + getString(R.string.mobile_firebase));
                if (item.getProductId().contains("mobile") && (type.equals(getString(R.string.mobile_firebase)) || type.equals("All"))) {
                    for (MobileDetails mobile : mobileDetailsArrayList) {
                        if (item.getProductId().equals(mobile.getKey())) {
                            totalPrice1 += parseInt(mobile.getPrice()) * item.getQuantity();
                            mobileUserProductDetailCardViews.add(new ProductDetailCardView(item.getProductId(),
                                    getActivity().getResources().getString(R.string.mobile_firebase),
                                    mobile.getName(), mobile.getPrice(), mobile.getRating(),
                                    mobile.getImage()));
                            break;
                        }
                    }
                } else if (item.getProductId().contains("labtop")) {
                    for (LabtopDetails laptop : laptopDetailsArrayList) {
                        if (item.getProductId().equals(laptop.getKey()) && (type.equals(getString(R.string.labtop_firebase)) || type.equals("All"))) {
                            totalPrice1 += parseInt(laptop.getPrice()) * item.getQuantity();
                            laptopUserProductDetailCardViews.add(new ProductDetailCardView(item.getProductId(),
                                    getActivity().getResources().getString(R.string.labtop_firebase),
                                    laptop.getName(), laptop.getPrice(), laptop.getRating(),
                                    laptop.getImage()));
                            break;
                        }
                    }
                } else {
                    for (FashionDetails fashion : fashionDetailsArrayList) {
                        if (item.getProductId().equals(fashion.getKey()) && (type.equals(getString(R.string.fashion_firebase)) || type.equals("All"))) {
                            totalPrice1 += parseInt(fashion.getPrice()) * item.getQuantity();
                            fashionUserProductDetailCardViews.add(new ProductDetailCardView(item.getProductId(),
                                    getActivity().getResources().getString(R.string.fashion_firebase),
                                    fashion.getName(), fashion.getPrice(), fashion.getRating(),
                                    fashion.getImage()));
                            break;
                        }
                    }
                }
            }
            mTotalPrice.setText("Total Price: " + format.format(totalPrice1) + " $");
            all.addAll(mobileUserProductDetailCardViews);
            all.addAll(laptopUserProductDetailCardViews);
            all.addAll(fashionUserProductDetailCardViews);
            if (mobileUserProductDetailCardViews.size() != 0)
                productUserDetailCardViewGroups.add(new ProductDetailCardViewGroup(getActivity().getString(R.string.mobile_firebase), mobileUserProductDetailCardViews));
            if (fashionUserProductDetailCardViews.size() != 0)
                productUserDetailCardViewGroups.add(new ProductDetailCardViewGroup(getActivity().getString(R.string.fashion_firebase), fashionUserProductDetailCardViews));
            if (laptopUserProductDetailCardViews.size() != 0)
                productUserDetailCardViewGroups.add(new ProductDetailCardViewGroup(getActivity().getString(R.string.labtop_firebase), laptopUserProductDetailCardViews));
            productAdapterGroup.setList(productUserDetailCardViewGroups);
        });
    }
}
