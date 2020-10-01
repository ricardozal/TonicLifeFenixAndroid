package mx.com.bigtechsolutions.toniclifefenix.ui.shoppingcart;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import mx.com.bigtechsolutions.toniclifefenix.R;
import mx.com.bigtechsolutions.toniclifefenix.api.responses.models.Address;
import mx.com.bigtechsolutions.toniclifefenix.api.responses.models.Branch;
import mx.com.bigtechsolutions.toniclifefenix.commons.Constants;
import mx.com.bigtechsolutions.toniclifefenix.commons.SharedPreferencesManager;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class BranchesRecyclerViewAdapter extends RecyclerView.Adapter<BranchesRecyclerViewAdapter.ViewHolder> {

    private Context ctx;
    private List<Branch> mValues;

    public BranchesRecyclerViewAdapter(Context ctx, List<Branch> mValues) {
        this.ctx = ctx;
        this.mValues = mValues;
    }

    @Override
    public BranchesRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_address_branch_item, parent, false);
        return new BranchesRecyclerViewAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final BranchesRecyclerViewAdapter.ViewHolder holder, int position) {
        if(mValues != null)
        {
            holder.branch = mValues.get(position);

            holder.alias.setText(holder.branch.getName());
            holder.fullAddress.setText(holder.branch.getAddress().getFullAddress());

            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferencesManager.setIntegerValue(Constants.BRANCH_ID, holder.branch.getId());
                    notifyDataSetChanged();
                }
            });

            if(holder.branch.getId().equals(SharedPreferencesManager.getIntValue(Constants.BRANCH_ID))){
                holder.card.setBackgroundColor(Color.parseColor("#8DABCA"));

            } else {
                holder.card.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }

        }
    }

    public void setDataList(List<Branch> addressesList)
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView alias;
        public final TextView fullAddress;
        public Branch branch;
        public MaterialCardView card;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            alias = view.findViewById(R.id.alias_address_branch);
            fullAddress = view.findViewById(R.id.full_address_branch);
            card = view.findViewById(R.id.address_card_branch);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + fullAddress.getText() + "'";
        }

    }

}
