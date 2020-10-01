package mx.com.bigtechsolutions.toniclifefenix.ui.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import mx.com.bigtechsolutions.toniclifefenix.R;
import mx.com.bigtechsolutions.toniclifefenix.api.responses.models.OrderItem;
import mx.com.bigtechsolutions.toniclifefenix.api.responses.models.PaymentMethod;
import mx.com.bigtechsolutions.toniclifefenix.ui.shoppingcart.PaymentMethodsRecyclerViewAdapter;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.ViewHolder> {

    private Context ctx;
    private List<OrderItem> mValues;
    private OnOrderItemListener onOrderItemListener;

    public MyOrdersAdapter(Context ctx, List<OrderItem> mValues, OnOrderItemListener onOrderItemListener) {
        this.ctx = ctx;
        this.mValues = mValues;
        this.onOrderItemListener = onOrderItemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_item, parent, false);
        return new MyOrdersAdapter.ViewHolder(view, onOrderItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(mValues != null)
        {
            holder.orderItem = mValues.get(position);

            String folio = "Folio: " + holder.orderItem.getId();
            String totalPrice = "Total: " + holder.orderItem.getTotalPrice();
            String totalPoints = "Puntos: " + holder.orderItem.getTotalPoints();
            String totalProducts = "No. productos: " + holder.orderItem.getTotalProducts();
            String status = "Status: " + holder.orderItem.getStatus();

            holder.id.setText(folio);
            holder.date.setText(holder.orderItem.getDate());
            holder.totalPrice.setText(totalPrice);
            holder.totalPoints.setText(totalPoints);
            holder.totalProducts.setText(totalProducts);
            holder.status.setText(status);
            holder.delivery.setText(holder.orderItem.getDelivery());

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

    public void setDataList(List<OrderItem> orderItems)
    {
        this.mValues = orderItems;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        public final TextView id;
        public final TextView date;
        public final TextView totalPrice;
        public final TextView totalPoints;
        public final TextView totalProducts;
        public final TextView status;
        public final TextView delivery;
        public OrderItem orderItem;
        OnOrderItemListener onOrderItemListener;
        public MaterialCardView card;

        public ViewHolder(View view, OnOrderItemListener onOrderItemListener) {
            super(view);
            mView = view;
            id = view.findViewById(R.id.order_item_id);
            date = view.findViewById(R.id.order_item_date);
            totalPrice = view.findViewById(R.id.order_item_total_price);
            totalPoints = view.findViewById(R.id.order_item_total_points);
            totalProducts = view.findViewById(R.id.order_item_total_products);
            status = view.findViewById(R.id.order_item_status);
            delivery = view.findViewById(R.id.order_item_delivery);
            this.onOrderItemListener = onOrderItemListener;
            card = view.findViewById(R.id.payment_method_card);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onOrderItemListener.OnOrderItemClick(getAdapterPosition());
        }
    }

    public interface OnOrderItemListener {
        void OnOrderItemClick(int position);
    }

}
