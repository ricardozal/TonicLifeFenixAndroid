package mx.com.bigtechsolutions.toniclifefenix.ui.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import mx.com.bigtechsolutions.toniclifefenix.R;
import mx.com.bigtechsolutions.toniclifefenix.api.responses.models.Address;
import mx.com.bigtechsolutions.toniclifefenix.data.entity.ShoppingCart;
import mx.com.bigtechsolutions.toniclifefenix.ui.shoppingcart.ShoppingCartRecyclerViewAdapter;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class MyAddressesAdapter extends RecyclerView.Adapter<MyAddressesAdapter.ViewHolder> {

    private Context ctx;
    private List<Address> mValues;
    public OnEditListener onEditListener;

    public MyAddressesAdapter(Context ctx, List<Address> mValues, OnEditListener onEditListener) {
        this.ctx = ctx;
        this.mValues = mValues;
        this.onEditListener = onEditListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.address_item, parent, false);
        return new MyAddressesAdapter.ViewHolder(view, onEditListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(mValues != null){

            holder.address = mValues.get(position);

            holder.alias.setText(holder.address.getAlias());
            holder.fullAddress.setText(holder.address.getFullAddress());

        }
    }

    public void setDataList(List<Address> addressList)
    {
        this.mValues = addressList;
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
        public final MaterialButton editBtn;
        OnEditListener onEditListener;
        public Address address;

        public ViewHolder(View view, OnEditListener onEditListener) {
            super(view);
            mView = view;
            alias = view.findViewById(R.id.address_profile_alias);
            fullAddress = view.findViewById(R.id.address_profile_full_address);
            editBtn = view.findViewById(R.id.address_profile_edit);
            this.onEditListener = onEditListener;

            editBtn.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int id = v.getId();

            if(id == R.id.address_profile_edit){

                onEditListener.onEditClick(getAdapterPosition());

            }
        }
    }

    public interface OnEditListener {

        void onEditClick(int position);

    }



}
