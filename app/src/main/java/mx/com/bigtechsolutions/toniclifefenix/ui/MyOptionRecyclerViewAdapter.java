package mx.com.bigtechsolutions.toniclifefenix.ui;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import mx.com.bigtechsolutions.toniclifefenix.R;
import mx.com.bigtechsolutions.toniclifefenix.commons.Constants;
import mx.com.bigtechsolutions.toniclifefenix.commons.MyFenixApp;
import mx.com.bigtechsolutions.toniclifefenix.commons.SharedPreferencesManager;
import mx.com.bigtechsolutions.toniclifefenix.login.MainActivity;

import java.util.List;

public class MyOptionRecyclerViewAdapter extends RecyclerView.Adapter<MyOptionRecyclerViewAdapter.ViewHolder> {

    private final List<String> mValues;
    private OnOptionlistener mOnOptionListener;

    public MyOptionRecyclerViewAdapter(List<String> items, OnOptionlistener onOptionlistener) {
        mValues = items;
        this.mOnOptionListener = onOptionlistener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_option, parent, false);
        return new ViewHolder(view, mOnOptionListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        if(mValues != null)
        {
            holder.mItem = mValues.get(position);

            int imageResource = 0;

            switch (position)
            {
                case 0:
                    imageResource = R.drawable.baseline_loyalty_black_18dp;
                    break;
                case 1:
                    imageResource = R.drawable.baseline_shopping_bag_black_18dp;
                    break;
                case 2:
                    imageResource = R.drawable.baseline_address_black_18dp;
                    break;
                case 3:
                    imageResource = R.drawable.baseline_payment_black_18dp;
                    break;
                case 4:
                    imageResource = R.drawable.baseline_logout_black_18dp;
                    break;
            }

            holder.icon.setImageResource(imageResource);
            holder.text.setText(holder.mItem);
        }

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        public final ImageView icon;
        public final TextView text;
        public String mItem;
        OnOptionlistener onOptionlistener;

        public ViewHolder(View view, OnOptionlistener onOptionlistener) {
            super(view);
            mView = view;
            icon = view.findViewById(R.id.item_icon);
            text= view.findViewById(R.id.item_text);
            this.onOptionlistener = onOptionlistener;

            view.setOnClickListener(this);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + text.getText() + "'";
        }

        @Override
        public void onClick(View v) {

            onOptionlistener.onOptionClick(getAdapterPosition());

        }
    }

    public interface OnOptionlistener {
        void onOptionClick(int position);
    }
}