package mx.com.bigtechsolutions.toniclifefenix.ui.shoppingcart;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import mx.com.bigtechsolutions.toniclifefenix.R;
import mx.com.bigtechsolutions.toniclifefenix.api.responses.models.Address;
import mx.com.bigtechsolutions.toniclifefenix.api.responses.models.PaymentMethod;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class PaymentMethodsRecyclerViewAdapter extends RecyclerView.Adapter<PaymentMethodsRecyclerViewAdapter.ViewHolder> {

    private Context ctx;
    private List<PaymentMethod> mValues;
    private OnPaymentMethodListener onPaymentMethodListener;

    public PaymentMethodsRecyclerViewAdapter(Context ctx, List<PaymentMethod> mValues, OnPaymentMethodListener onPaymentMethodListener) {
        this.ctx = ctx;
        this.mValues = mValues;
        this.onPaymentMethodListener = onPaymentMethodListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.payment_method_item, parent, false);
        return new ViewHolder(view, onPaymentMethodListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if(mValues != null)
        {
            holder.paymentMethod = mValues.get(position);

            holder.name.setText(holder.paymentMethod.getName());

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

    public void setDataList(List<PaymentMethod> paymentMethods)
    {
        this.mValues = paymentMethods;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        public final TextView name;
        public PaymentMethod paymentMethod;
        OnPaymentMethodListener onPaymentMethodListener;
        public MaterialCardView card;

        public ViewHolder(View view, OnPaymentMethodListener onPaymentMethodListener) {
            super(view);
            mView = view;
            name = view.findViewById(R.id.payment_method_name);
            this.onPaymentMethodListener = onPaymentMethodListener;
            card = view.findViewById(R.id.payment_method_card);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onPaymentMethodListener.OnPaymentMethodClick(getAdapterPosition());
        }
    }

    public interface OnPaymentMethodListener {
        void OnPaymentMethodClick(int position);
    }

}
