package com.example.onlineshoppingisa.dialog;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.onlineshoppingisa.R;
import com.example.onlineshoppingisa.models.ProductDetailCardView;
import com.example.onlineshoppingisa.roomdatabase.ProductDataBase;
import com.example.onlineshoppingisa.roomdatabase.ProductRoom;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BottomSheetDialog extends BottomSheetDialogFragment {

    private static final String TAG = "BottomSheetDialog";

    private FloatingActionButton mDecrease;
    private TextView mTextView;
    private Button mCancelOrder;
    private String mProductId;
    private ProductDataBase mProductRoom;
    private String userId;
    private ProductDetailCardView mProductDetailCardView;
    private BottomSheetDialogInt mBottomSheetDialogInt;

    public BottomSheetDialog(ProductDetailCardView productDetailCardView) {
        this.mProductDetailCardView = productDetailCardView;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_layout, container, false);
        mTextView = view.findViewById(R.id.bottom_sheet_text_view);
        mDecrease = view.findViewById(R.id.bottom_sheet_sup);
        mProductId = mProductDetailCardView.getProductId();
        FloatingActionButton mIncrease = view.findViewById(R.id.bottom_sheet_add);
        Button mConfirmOrder = view.findViewById(R.id.bottom_sheet_confirm_order);
        ImageView mImageView = view.findViewById(R.id.bottom_sheet_image_view);
        TextView mProductName = view.findViewById(R.id.bottom_sheet_product_name);

        Picasso.with(getActivity())
                .load(Uri.parse(mProductDetailCardView.getProductImage()))
                .into(mImageView);

        mProductName.setText(mProductDetailCardView.getProductName());
        mCancelOrder = view.findViewById(R.id.bottom_sheet_cancel_order);
        mProductRoom = ProductDataBase.grtInstance(getActivity());
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        }

        mProductRoom.productDao().findProduct(userId,
                mProductId).observe(this, productRoom -> {
            Log.d(TAG, "onCreateView: ---- " + productRoom);
            if (productRoom != null) {
                mCancelOrder.setEnabled(true);
                setData(productRoom);
                mDecrease.setEnabled(true);
            } else {
                Log.d(TAG, "onCreateView: ---- dis");
                mCancelOrder.setEnabled(false);
            }
        });
        if (mTextView.getText().toString().equals("0")) {
            mDecrease.setEnabled(false);
        } else {
            mDecrease.setEnabled(true);
        }

        mIncrease.setOnClickListener(v -> {
            int mCurrentValue = Integer.parseInt(mTextView.getText().toString());
            mTextView.setText(String.valueOf(mCurrentValue + 1));
            mDecrease.setEnabled(true);
        });

        mDecrease.setOnClickListener(v -> {
            int mCurrentValue = Integer.parseInt(mTextView.getText().toString());
            mTextView.setText(String.valueOf(mCurrentValue - 1));
            if (mCurrentValue - 1 == 0) {
                mDecrease.setEnabled(false);
            }

        });

        mConfirmOrder.setOnClickListener(v -> addOrder());

        mCancelOrder.setOnClickListener(v -> chancelOrder());
        return view;
    }

    private void setData(ProductRoom productRoom) {
        mTextView.setText(String.valueOf(productRoom.getQuantity()));
    }

    private void addOrder() {
        Log.d(TAG, "addOrder: .....");
        if (!mTextView.getText().toString().equals("0")) {
            ProductRoom mRoom = new ProductRoom(Integer.parseInt(mTextView.getText().toString()),
                    Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid(),
                    mProductId);
            mProductRoom.productDao().insert(mRoom)
                    .subscribeOn(Schedulers.computation())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onComplete() {
                            Log.d(TAG, "onComplete: .... complete");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(TAG, "onError: ..... " + e.getMessage());
                        }
                    });
            dismiss();
            Log.d(TAG, "addOrder: ..... Add");
        } else {
            Toast.makeText(getActivity(), "Quality equal to zero", Toast.LENGTH_SHORT).show();
        }
    }

    private void chancelOrder() {
        mProductRoom.productDao().deleteDataForUser(userId,
                mProductId)
                .subscribeOn(Schedulers.computation())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ....cancel");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: ..... " + e.getMessage());
                    }
                });
        dismiss();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        mBottomSheetDialogInt.sheetDismiss();
    }

    public void setBottomSheetDialogInt(BottomSheetDialogInt bottomSheetDialogInt) {
        this.mBottomSheetDialogInt = bottomSheetDialogInt;
    }

    public interface BottomSheetDialogInt {
        void sheetDismiss();
    }
}
