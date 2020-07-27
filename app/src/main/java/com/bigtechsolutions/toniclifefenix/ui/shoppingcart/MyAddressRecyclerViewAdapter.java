package com.bigtechsolutions.toniclifefenix.ui.shoppingcart;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bigtechsolutions.toniclifefenix.R;
import com.bigtechsolutions.toniclifefenix.api.responses.models.Address;
import com.google.android.material.card.MaterialCardView;

import java.util.List;


public class MyAddressRecyclerViewAdapter extends RecyclerView.Adapter<MyAddressRecyclerViewAdapter.ViewHolder> {

    private Context ctx;
    private List<Address> mValues;
    private OnAddressListener onAddressListener;

    public MyAddressRecyclerViewAdapter(Context context, List<Address> items, OnAddressListener onAddressListener) {
        mValues = items;
        ctx = context;
        this.onAddressListener = onAddressListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_address_item, parent, false);
        return new ViewHolder(view, onAddressListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if(mValues != null)
        {
            holder.address = mValues.get(position);

            holder.alias.setText(holder.address.getAlias());
            holder.fullAddress.setText(holder.address.getFullAddress());

            if(holder.address.isSelected())
            {
                holder.card.setBackgroundColor(Color.parseColor("#8DABCA"));
            }
        }
    }

    public void setDataList(List<Address> addressesList)
    {
        this.mValues = addressesList;
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
        public final TextView alias;
        public final TextView fullAddress;
        public Address address;
        OnAddressListener onAddressListener;
        public MaterialCardView card;

        public ViewHolder(View view, OnAddressListener onAddressListener) {
            super(view);
            mView = view;
            alias = view.findViewById(R.id.alias_address);
            fullAddress = view.findViewById(R.id.full_address);
            this.onAddressListener = onAddressListener;
            card = view.findViewById(R.id.address_card);

            view.setOnClickListener(this);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + fullAddress.getText() + "'";
        }

        @Override
        public void onClick(View v) {
            onAddressListener.OnAddressClick(getAdapterPosition());
        }
    }

    public interface OnAddressListener {
        void OnAddressClick(int position);
    }
}