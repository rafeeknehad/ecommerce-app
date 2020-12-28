package com.example.onlineshoppingisa;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshoppingisa.models.ProductType;

import java.util.ArrayList;

public class ProductTypeAdapter extends RecyclerView.Adapter<ProductTypeAdapter.ProductTypeViewHolder> {

    private static final String TAG = "ProductTypeAdapter";

    public ArrayList<ProductType> typeArrayList;
    public Context context;
    public ProductTypeAdapterInterface adapterInterface;

    public ProductTypeAdapter(ArrayList<ProductType> typeArrayList, Context context) {
        this.typeArrayList = typeArrayList;
        this.context = context;
        this.adapterInterface = (ProductTypeAdapterInterface) context;

    }

    public void setOnItemAdapterProductTypeListener(ProductTypeAdapterInterface adapterInterface)
    {
    }

    @NonNull
    @Override
    public ProductTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_type_cardview, parent, false);
        ProductTypeViewHolder productTypeViewHolder = new ProductTypeViewHolder(view);
        return productTypeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductTypeViewHolder holder, int position) {
        String item = typeArrayList.get(position).getType();
        holder.textView.setText(item);
    }

    @Override
    public int getItemCount() {
        return typeArrayList.size();
    }

    public class ProductTypeViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public ProductTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.product_type_cardview_txt);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(getAdapterPosition()!=RecyclerView.NO_POSITION)
                    {
                        adapterInterface.productTypeAdapterSetOnItemClick(getAdapterPosition());
                    }
                }
            });
        }
    }

    public interface ProductTypeAdapterInterface
    {
        public void productTypeAdapterSetOnItemClick(int pos);
    }


}
