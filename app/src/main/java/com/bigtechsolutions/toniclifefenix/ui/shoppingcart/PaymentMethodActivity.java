package com.bigtechsolutions.toniclifefenix.ui.shoppingcart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bigtechsolutions.toniclifefenix.R;
import com.bigtechsolutions.toniclifefenix.api.responses.models.Branch;
import com.bigtechsolutions.toniclifefenix.api.responses.models.PaymentMethod;
import com.bigtechsolutions.toniclifefenix.commons.MyFenixApp;
import com.bigtechsolutions.toniclifefenix.viewmodel.AddressViewModel;
import com.bigtechsolutions.toniclifefenix.viewmodel.OrderViewModel;

import java.util.List;

public class PaymentMethodActivity extends AppCompatActivity implements PaymentMethodsRecyclerViewAdapter.OnPaymentMethodListener {

    OrderViewModel orderViewModel;
    ProgressDialog loading;
    RecyclerView recyclerView;
    Toolbar toolbar;
    PaymentMethodsRecyclerViewAdapter adapter;
    List<PaymentMethod> paymentMethodList;

    int deliveryAddressId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);

        Bundle bundle = getIntent().getExtras();
        deliveryAddressId = bundle.getInt("addressId");

        loading = ProgressDialog.show(this, "Cargando", "Por favor espere...", false, false);


        orderViewModel = new ViewModelProvider(this)
                .get(OrderViewModel.class);

        recyclerView = findViewById(R.id.payment_methods_list);
        toolbar = findViewById(R.id.toolbarPaymentMethods);

        toolbarConfig();

        adapter = new PaymentMethodsRecyclerViewAdapter(
                this,
                paymentMethodList,
                this
        );

        recyclerView.setAdapter(adapter);

        loadPaymentMethodData();

    }

    private void loadPaymentMethodData() {

        orderViewModel.getPaymentMethods().observe(this, new Observer<List<PaymentMethod>>() {
            @Override
            public void onChanged(List<PaymentMethod> paymentMethods) {
                paymentMethodList = paymentMethods;
                adapter.setDataList(paymentMethods);
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
                Intent i = new Intent(MyFenixApp.getContext(), ShoppingCartActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    @Override
    public void OnPaymentMethodClick(int position) {

        if(paymentMethodList.get(position).getId() == 1){ //PayPal

            Toast.makeText(MyFenixApp.getContext(), "PayPal", Toast.LENGTH_SHORT).show();

        } else if(paymentMethodList.get(position).getId() == 2){ //Stripe

            Intent i = new Intent(MyFenixApp.getContext(), StripePayActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("addressId", deliveryAddressId);
            bundle.putInt("paymentMethodId", paymentMethodList.get(position).getId());
            i.putExtras(bundle);
            startActivity(i);
            finish();

        }

    }
}