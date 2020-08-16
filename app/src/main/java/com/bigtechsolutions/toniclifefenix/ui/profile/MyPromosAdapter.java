package com.bigtechsolutions.toniclifefenix.ui.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bigtechsolutions.toniclifefenix.R;
import com.bigtechsolutions.toniclifefenix.api.responses.models.OrderItem;
import com.bigtechsolutions.toniclifefenix.api.responses.models.Promotion;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class MyPromosAdapter extends RecyclerView.Adapter<MyPromosAdapter.ViewHolder> {

    private Context ctx;
    private List<Promotion> mValues;

    public MyPromosAdapter(Context ctx, List<Promotion> mValues) {
        this.ctx = ctx;
        this.mValues = mValues;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.promotion_item, parent, false);
        return new MyPromosAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(mValues != null)
        {
            holder.promotion = mValues.get(position);

            String minAmount = "Monto mínimo: " + holder.promotion.getMinAmount();
            String date = "Promoción válida del " + holder.promotion.getDateBegin() +  " al "+holder.promotion.getDateEnd();

            holder.name.setText(holder.promotion.getName());
            holder.desc.setText(holder.promotion.getDescription());
            holder.date.setText(date);
            holder.minAmount.setText(minAmount);
            holder.isAccumulative.setText(holder.promotion.getIsAccumulative());

        }
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

    public void setDataList(List<Promotion> promotions)
    {
        this.mValues = promotions;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView name;
        public final TextView desc;
        public final TextView date;
        public final TextView minAmount;
        public final TextView isAccumulative;
        public Promotion promotion;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            name = view.findViewById(R.id.promo_name);
            desc = view.findViewById(R.id.promo_desc);
            date = view.findViewById(R.id.promo_date);
            minAmount = view.findViewById(R.id.promo_min_amount);
            isAccumulative = view.findViewById(R.id.promo_is_accumulative);
        }
    }

}
