package com.bigtechsolutions.toniclifefenix.ui.shoppingcart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bigtechsolutions.toniclifefenix.R;
import com.bigtechsolutions.toniclifefenix.api.AuthApiClient;
import com.bigtechsolutions.toniclifefenix.api.AuthApiService;
import com.bigtechsolutions.toniclifefenix.api.requests.OrderRequest;
import com.bigtechsolutions.toniclifefenix.api.requests.ProductRequest;
import com.bigtechsolutions.toniclifefenix.commons.Constants;
import com.bigtechsolutions.toniclifefenix.commons.MyFenixApp;
import com.bigtechsolutions.toniclifefenix.commons.SharedPreferencesManager;
import com.bigtechsolutions.toniclifefenix.data.entity.ShoppingCart;
import com.bigtechsolutions.toniclifefenix.ui.NewDistributorActivity;
import com.bigtechsolutions.toniclifefenix.viewmodel.OrderViewModel;
import com.bigtechsolutions.toniclifefenix.viewmodel.ShoppingCartViewModel;
import com.bigtechsolutions.toniclifefenix.viewmodel.interfaces.OnOrderResponse;
import com.google.android.material.button.MaterialButton;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PayPalPaymentActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int PAYPAL_REQUEST_CODE = 7171;

    private PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Constants.PAYPAL_CLIENT_ID);

    int deliveryAddressId;
    int paymentMethodId;
    ShoppingCartViewModel productViewModel;
    OrderViewModel orderViewModel;
    ProgressDialog loading;
    Toolbar toolbar;
    private MaterialButton pay;
    private TextView toPayTxt;
    List<ProductRequest> productsRequest = new ArrayList<>();
    double amount = 0;
    String currency = "";

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_pal_payment);

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);

        productViewModel = new ViewModelProvider(this)
                .get(ShoppingCartViewModel.class);

        orderViewModel = new ViewModelProvider(this)
                .get(OrderViewModel.class);

        Bundle bundle = getIntent().getExtras();
        deliveryAddressId = bundle.getInt("addressId");
        paymentMethodId = bundle.getInt("paymentMethodId");

        findViews();
        events();
        toolbarConfig();
        getShoppingCart();
        setPaypal();

    }

    private void setPaypal() {

        amount = productViewModel.getTotalOrder();

        String selected = SharedPreferencesManager.getStringValue(Constants.COUNTRY);

        if(selected != null){

            if(selected.equals("1")){
                currency = "MXN";
            } else if(selected.equals("2")){
                currency = "USD";
            }

        }

        String totalStr = "Total a pagar: " + "$"+amount+currency;
        toPayTxt.setText(totalStr);

    }

    private void getShoppingCart() {

        productViewModel.getAll().observe(this, new Observer<List<ShoppingCart>>() {
            @Override
            public void onChanged(List<ShoppingCart> shoppingCarts) {
                for ( ShoppingCart product : shoppingCarts ) {

                    productsRequest.add(new ProductRequest(product.getProductId(),product.getQuantity()));

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
                Intent i = new Intent(MyFenixApp.getContext(), PaymentMethodActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void events() {
        pay.setOnClickListener(this);
    }

    private void findViews() {

        pay = findViewById(R.id.PayPalBtn);
        toPayTxt = findViewById(R.id.total_to_pay_paypal);
        toolbar = findViewById(R.id.toolbarPaypal);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(id == R.id.PayPalBtn){

            PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(amount),currency,
                    "Compra en Tonic Life Fenix", PayPalPayment.PAYMENT_INTENT_SALE);

            Intent intent = new Intent(this, PaymentActivity.class);
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
            intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
            startActivityForResult(intent, PAYPAL_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PAYPAL_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {

                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                if (confirmation != null) {
                    // Payment completed successfully

                    loading = ProgressDialog.show(this, "Cargando", "Por favor espere...", false, false);

                    int distributorId = SharedPreferencesManager.getIntValue(Constants.DISTRIBUTOR_ID);
                    int branchId = SharedPreferencesManager.getIntValue(Constants.BRANCH_ID);

                    OrderRequest orderRequest = new OrderRequest(deliveryAddressId, distributorId, branchId, paymentMethodId, productsRequest);

                    orderViewModel.saveOrder(orderRequest, new OnOrderResponse() {
                        @Override
                        public void OnSuccess(String title, String message, Integer orderId, Double currentPoints) {

                            int kitsNumber = productViewModel.getNumberKits();

                            productViewModel.deleteAll();
                            SharedPreferencesManager.removeValue(Constants.BRANCH_ID);
                            SharedPreferencesManager.removeValue(Constants.CURRENT_POINTS);
                            SharedPreferencesManager.setStringValue(Constants.CURRENT_POINTS, String.valueOf(currentPoints));

                            loading.dismiss();

                            displayAlert(
                                    title,
                                    message,
                                    kitsNumber > 0,
                                    orderId,
                                    true
                            );

                        }

                        @Override
                        public void OnError(String title, String message) {

                            loading.dismiss();

                            displayAlert(
                                    title,
                                    "El pago se procesó, pero algo salió mal al guardar tu orden de compra, ponte en contacto de inmediato con el administrador.",
                                    false,
                                    0,
                                    true
                            );

                        }
                    });

                }

            } else if(resultCode == Activity.RESULT_CANCELED) {

                loading.dismiss();

                displayAlert(
                        "Cancelado",
                        "El pago no fue procesado",
                        false,
                        0,
                        false
                );

            }

        } else if(resultCode == PaymentActivity.RESULT_EXTRAS_INVALID){

            loading.dismiss();

            displayAlert(
                    "Invalido",
                    "El pago no fue procesado",
                    false,
                    0,
                    false
            );

        }
    }

    private void displayAlert(@NonNull String title,
                              @Nullable String message,
                              boolean isKit,
                              int orderId,
                              boolean correctPayment) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(correctPayment){
                    if(isKit){
                        Intent i = new Intent(MyFenixApp.getContext(), NewDistributorActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("orderId", orderId);
                        i.putExtras(bundle);
                        startActivity(i);
                        finish();

                    } else {

                        Intent i = new Intent(MyFenixApp.getContext(), ShoppingCartActivity.class);
                        startActivity(i);
                        finish();

                    }
                } else {
                    dialog.dismiss();
                }

            }
        });
        builder.setCancelable(false);
        builder.create().show();
    }
}