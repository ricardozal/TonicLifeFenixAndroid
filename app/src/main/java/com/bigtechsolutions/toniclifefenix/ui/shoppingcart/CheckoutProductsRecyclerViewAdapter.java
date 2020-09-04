package com.bigtechsolutions.toniclifefenix.ui.shoppingcart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bigtechsolutions.toniclifefenix.R;
import com.bigtechsolutions.toniclifefenix.data.entity.ShoppingCart;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;

import java.text.DecimalFormat;
import java.util.List;

public class CheckoutProductsRecyclerViewAdapter extends RecyclerView.Adapter<CheckoutProductsRecyclerViewAdapter.ViewHolder> {

    private Context ctx;
    private List<ShoppingCart> mValues;
    DecimalFormat f = new DecimalFormat("##.00");

    public CheckoutProductsRecyclerViewAdapter(Context ctx, List<ShoppingCart> mValues) {
        this.ctx = ctx;
        this.mValues = mValues;
    }

    @NonNull
    @Override
    public CheckoutProductsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_checkout_item, parent, false);
        return new CheckoutProductsRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(mValues != null)
        {
            holder.product = mValues.get(position);

            String price = "$" + f.format((holder.product.getPrice() * holder.product.getQuantity()));
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
        public final TextView quantity;
        public ShoppingCart product;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            imageProduct = view.findViewById(R.id.imageProdCheckout);
            nameProduct = view.findViewById(R.id.product_name_checkout);
            priceProduct = view.findViewById(R.id.price_checkout);
            pointsProduct = view.findViewById(R.id.points_checkout);
            quantity = view.findViewById(R.id.quantity_checkout);

        }

    }

}
