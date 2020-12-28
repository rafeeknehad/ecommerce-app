package com.example.onlineshoppingisa;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class ProductDialog extends DialogFragment {

    private static final String TAG = "ProductDialog";
    
    //variable

    private String productId;
    private Context context;
    private getDataFromProductDialog listener;
    //xml
    private Button increase;
    private Button decresase;
    private TextView text;

    public ProductDialog(String id, Context context) {
        this.productId = id;
        context = context;
        listener = (getDataFromProductDialog) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.product_dialog,null);

        increase = view.findViewById(R.id.product_dialog_add);
        decresase = view.findViewById(R.id.product_dialog_sub);
        text = view.findViewById(R.id.product_dialog_number);

        builder.setView(view)
                .setTitle("Product Number")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "onClick: 1111 "+"cancel");
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.getQuantity(Integer.valueOf(text.getText().toString()));
                    }
                });


        int number = Integer.valueOf(text.getText().toString());


        if(number==0)
        {
            decresase.setEnabled(false);
        }

        decresase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.valueOf(text.getText().toString());
                number--;
                text.setText(String.valueOf(number));
                if(number==0)
                {
                    decresase.setEnabled(false);
                }
            }
        });

        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.valueOf(text.getText().toString());
                decresase.setEnabled(true);
                number++;
                text.setText(String.valueOf(number));
            }
        });

        return builder.create();
    }

    public interface getDataFromProductDialog
    {
        public void getQuantity(int quantity);
    }
}


