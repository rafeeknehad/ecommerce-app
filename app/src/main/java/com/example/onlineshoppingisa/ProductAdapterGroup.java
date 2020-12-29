package com.example.onlineshoppingisa;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshoppingisa.models.ProductDetailCardView;
import com.example.onlineshoppingisa.models.ProductDetailCardViewGroup;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapterGroup extends RecyclerView.Adapter<ProductAdapterGroup.ProductAdapterGropViewHolder> implements Filterable {

    private static final String TAG = "ProductAdapterGroup";

    private Context context;
    private List<ProductDetailCardViewGroup> productDetailCardViewGroups;
    private ProductAdapter productAdapter;
    private ProductAdapterGroupInterface productAdapterGroupInterface;
    private List<ProductDetailCardViewGroup> productDetailCardViewGroupsFull;

    public ProductAdapterGroup(Context context, List<ProductDetailCardViewGroup> productDetailCardViewGroups) {
        this.context = context;
        this.productDetailCardViewGroups = productDetailCardViewGroups;
        productAdapterGroupInterface = (ProductAdapterGroupInterface) context;
        productDetailCardViewGroupsFull = new ArrayList<>(productDetailCardViewGroups);
    }

    @NonNull
    @Override
    public ProductAdapterGropViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_detail_cardview_group, parent, false);
        ProductAdapterGropViewHolder viewHolder = new ProductAdapterGropViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapterGropViewHolder holder, int position) {
        ProductDetailCardViewGroup item = productDetailCardViewGroups.get(position);
        holder.textView.setText(item.getProductType());
        productAdapter = new ProductAdapter((ArrayList<ProductDetailCardView>) item.getProductTypeList(), context);
        holder.recyclerView.setNestedScrollingEnabled(true);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        holder.recyclerView.setAdapter(productAdapter);


    }

    @Override
    public int getItemCount() {
        return productDetailCardViewGroups.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                System.out.println("tttttttttttttttttttttttttt "+constraint);
                List<ProductDetailCardViewGroup> filterList = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filterList.addAll(productDetailCardViewGroupsFull);
                } else {
                    String filterStr = constraint.toString().toLowerCase().trim();
                    for (ProductDetailCardViewGroup item : productDetailCardViewGroupsFull) {
                        List<ProductDetailCardView> filterProductList = new ArrayList<>();
                        for (ProductDetailCardView detailCardView : item.getProductTypeList()) {
                            if (detailCardView.getProductName().toLowerCase().contains(filterStr)) {
                                filterProductList.add(detailCardView);
                            }
                        }
                        if (filterProductList.size() != 0) {
                            filterList.add(new ProductDetailCardViewGroup(item.getProductType(), filterProductList));
                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filterList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                productDetailCardViewGroups.clear();
                productDetailCardViewGroups.addAll((List) results.values);
                notifyDataSetChanged();
            }
        };
    }

    public interface ProductAdapterGroupInterface {
        public void ProductAdapterGropSetOnClicked(int pos);
    }

    public class ProductAdapterGropViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private RecyclerView recyclerView;

        public ProductAdapterGropViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.prodduct_detail_cardview_group_text);
            recyclerView = itemView.findViewById(R.id.prodduct_detail_cardview_group_recy);
        }
    }
}
