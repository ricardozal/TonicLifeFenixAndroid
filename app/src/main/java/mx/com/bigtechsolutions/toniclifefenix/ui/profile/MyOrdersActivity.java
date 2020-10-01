package mx.com.bigtechsolutions.toniclifefenix.ui.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import mx.com.bigtechsolutions.toniclifefenix.R;
import mx.com.bigtechsolutions.toniclifefenix.api.responses.models.Order;
import mx.com.bigtechsolutions.toniclifefenix.api.responses.models.OrderItem;
import mx.com.bigtechsolutions.toniclifefenix.api.responses.models.PaymentMethod;
import mx.com.bigtechsolutions.toniclifefenix.commons.MyFenixApp;
import mx.com.bigtechsolutions.toniclifefenix.ui.BottomNavigationActivity;
import mx.com.bigtechsolutions.toniclifefenix.ui.ChooseCountryFragment;
import mx.com.bigtechsolutions.toniclifefenix.ui.shoppingcart.PaymentMethodsRecyclerViewAdapter;
import mx.com.bigtechsolutions.toniclifefenix.ui.shoppingcart.ShoppingCartActivity;
import mx.com.bigtechsolutions.toniclifefenix.ui.shoppingcart.StripePayActivity;
import mx.com.bigtechsolutions.toniclifefenix.viewmodel.OrderViewModel;
import mx.com.bigtechsolutions.toniclifefenix.viewmodel.interfaces.OnOrderItemResponse;

import java.util.List;

public class MyOrdersActivity extends AppCompatActivity implements MyOrdersAdapter.OnOrderItemListener {

    OrderViewModel orderViewModel;
    ProgressDialog loading;
    RecyclerView recyclerView;
    Toolbar toolbar;
    MyOrdersAdapter adapter;
    List<OrderItem> orderItemList;
    TextView txtEmptyPurchases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        loading = ProgressDialog.show(this, "Cargando", "Por favor espere...", false, false);

        orderViewModel = new ViewModelProvider(this)
                .get(OrderViewModel.class);

        recyclerView = findViewById(R.id.order_items_list);
        toolbar = findViewById(R.id.toolbarOrderItem);
        txtEmptyPurchases = findViewById(R.id.txtEmptyPurchases);

        toolbarConfig();

        adapter = new MyOrdersAdapter(
                this,
                orderItemList,
                this
        );

        recyclerView.setAdapter(adapter);

        loadOrdersData();
    }

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }

    private void loadOrdersData() {

        orderViewModel.getOrders().observe(this, new Observer<List<OrderItem>>() {
            @Override
            public void onChanged(List<OrderItem> orderItems) {
                orderItemList = orderItems;
                if(orderItemList.size() < 1)
                    txtEmptyPurchases.setVisibility(View.VISIBLE);
                adapter.setDataList(orderItems);
            }
        });

        orderViewModel.getDownloadFinished().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean downloadFinished) {
                if (downloadFinished != null) {
                    if (downloadFinished) {
                        loading.dismiss();
                    }
                }
            }
        });
    }

    private void toolbarConfig() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void OnOrderItemClick(int position) {

        int orderId = orderItemList.get(position).getId();

        Intent i = new Intent(MyFenixApp.getContext(), OrderShowActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("orderId", orderId);
        i.putExtras(bundle);
        startActivity(i);

    }

}