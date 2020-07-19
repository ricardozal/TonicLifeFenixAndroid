package com.bigtechsolutions.toniclifefenix.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bigtechsolutions.toniclifefenix.R;
import com.bigtechsolutions.toniclifefenix.api.responses.models.Product;
import com.bigtechsolutions.toniclifefenix.data.entity.ShoppingCart;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class ShoppingCartRecyclerViewAdapter extends RecyclerView.Adapter<ShoppingCartRecyclerViewAdapter.ViewHolder> {

    private Context ctx;
    private List<ShoppingCart> mValues;

    public ShoppingCartRecyclerViewAdapter(Context context, List<ShoppingCart> items) {
        mValues = items;
        ctx = context;
    }

    @Override
    public ShoppingCartRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shpping_cart_item, parent, false);
        return new ShoppingCartRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if(mValues != null)
        {
            holder.product = mValues.get(position);

            String price = "$" + (holder.product.getPrice() * holder.product.getQuantity());
            String points = "Puntos: " + (holder.product.getPoints() * holder.product.getQuantity());
            String quantity = holder.product.getQuantity() + "  unidad(es)";

            holder.nameProduct.setText(holder.product.getProductName());
            holder.priceProduct.setText(price);
            holder.pointsProduct.setText(points);
            holder.quantity.setText(quantity);

            Glide.with(ctx)
                    .load(holder.product.getImageUrl()).into(holder.imageProduct);
        }

    }

    public void setDataList(List<ShoppingCart> productList)
    {
        this.mValues = productList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {

        if(mValues != null)
        {
            return mValues.size();
        } else {
            return 0;
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView imageProduct;
        public final TextView nameProduct;
        public final TextView priceProduct;
        public final TextView pointsProduct;
        public final MaterialButton quantity;
        public ShoppingCart product;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            imageProduct = view.findViewById(R.id.imageProdCart);
            nameProduct = view.findViewById(R.id.product_name_cart);
            priceProduct = view.findViewById(R.id.price_cart);
            pointsProduct = view.findViewById(R.id.points_cart);
            quantity = view.findViewById(R.id.changeQuantityBtn);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + nameProduct.getText() + "'";
        }

    }

}
