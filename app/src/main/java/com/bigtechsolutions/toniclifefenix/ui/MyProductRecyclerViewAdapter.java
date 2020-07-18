package com.bigtechsolutions.toniclifefenix.ui;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigtechsolutions.toniclifefenix.R;
import com.bigtechsolutions.toniclifefenix.api.responses.models.Product;
import com.bumptech.glide.Glide;
import java.util.Random;
import java.util.List;


public class MyProductRecyclerViewAdapter extends RecyclerView.Adapter<MyProductRecyclerViewAdapter.ViewHolder> {

    private Context ctx;
    private List<Product> mValues;
    private OnProductlistener mOnProductListener;

    public MyProductRecyclerViewAdapter(Context context, List<Product> items, OnProductlistener onProductListener) {
        mValues = items;
        ctx = context;
        this.mOnProductListener = onProductListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_product, parent, false);
        return new ViewHolder(view, mOnProductListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if(mValues != null)
        {
            holder.product = mValues.get(position);

            String price = "Precio distribuidor: $" + holder.product.getDistributorPrice();
            String tax = "Impuesto: $" + holder.product.getTax();
            String total = "Precio final: $" + holder.product.getTotal();
            String points = "Puntos: " + holder.product.getPoints();

            holder.nameProduct.setText(holder.product.getName());
            holder.priceProduct.setText(price);
            holder.taxProduct.setText(tax);
            holder.totalProduct.setText(total);
            holder.pointsProduct.setText(points);

            Glide.with(ctx)
                    .load(holder.product.getImageUrl()).into(holder.imageProduct);
        }

    }

    public void setDataList(List<Product> productList)
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        public final ImageView imageProduct;
        public final TextView nameProduct;
        public final TextView priceProduct;
        public final TextView taxProduct;
        public final TextView totalProduct;
        public final TextView pointsProduct;
        public Product product;
        OnProductlistener onProductlistener;

        public ViewHolder(View view, OnProductlistener onProductlistener) {
            super(view);
            mView = view;
            imageProduct = view.findViewById(R.id.product_image);
            nameProduct = view.findViewById(R.id.product_name);
            priceProduct = view.findViewById(R.id.distributor_price);
            taxProduct = view.findViewById(R.id.product_tax);
            totalProduct = view.findViewById(R.id.product_total);
            pointsProduct = view.findViewById(R.id.points);
            this.onProductlistener = onProductlistener;

            view.setOnClickListener(this);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + nameProduct.getText() + "'";
        }

        @Override
        public void onClick(View v) {
            onProductlistener.onProductClick(getAdapterPosition());
        }
    }

    public interface OnProductlistener {
        void onProductClick(int position);
    }
}