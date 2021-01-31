package com.example.onlineshoppingisa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshoppingisa.R;
import com.example.onlineshoppingisa.models.ProductDetailCardView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    //private static final String TAG = "ProductAdapter";

    public ArrayList<ProductDetailCardView> productDetailCardViews;
    public Context context;

    public ProductAdapterInterface productAdapterInterface;
    public ArrayList<ProductDetailCardView> productDetailCardViewsFull;

    public ProductAdapter(ArrayList<ProductDetailCardView> productDetailCardViews, Context context) {
        this.productDetailCardViews = productDetailCardViews;
        this.context = context;
        productAdapterInterface = (ProductAdapterInterface) context;
        productDetailCardViewsFull = new ArrayList<>(productDetailCardViews);
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_detail_cardview, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductDetailCardView productDetailCardView = productDetailCardViews.get(position);
        Picasso.with(context).load(productDetailCardView.getProductImage())
                .resize(300, 300)
                .into(holder.productImage);

        holder.productName.setText(productDetailCardView.getProductName());
        holder.productPrice.setText(productDetailCardView.getProductPrice());
        holder.productRatingValue.setText(productDetailCardView.getProductRating());
        holder.productRating.setRating((Float.parseFloat(productDetailCardView.getProductRating()) / 2.0f));
    }

    @Override
    public int getItemCount() {
        return productDetailCardViews.size();
    }

    public void setAdapterInterface(ProductAdapterInterface mListener) {
        this.productAdapterInterface = mListener;
    }

    public interface ProductAdapterInterface {
        void productAdapterSetOnItemClickListener(ProductDetailCardView productDetailCardView, int pos);

        void productAdapterSetOnItemClickListenerCartFragment(ProductDetailCardView productDetailCardView, int pos);
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        private ImageView productImage;
        private TextView productName;
        private TextView productPrice;
        private RatingBar productRating;
        private TextView productRatingValue;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_card_image);
            productName = itemView.findViewById(R.id.product_card_name);
            productPrice = itemView.findViewById(R.id.product_card_price);
            productRating = itemView.findViewById(R.id.product_card_rating);
            productRatingValue = itemView.findViewById(R.id.product_card_rating_text);

            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    productAdapterInterface.productAdapterSetOnItemClickListener(productDetailCardViews.get(pos), pos);
                    productAdapterInterface.productAdapterSetOnItemClickListenerCartFragment(productDetailCardViews.get(pos), pos);
                }
            });
        }
    }

}
