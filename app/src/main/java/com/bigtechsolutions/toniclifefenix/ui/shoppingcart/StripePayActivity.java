package com.bigtechsolutions.toniclifefenix.ui.shoppingcart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bigtechsolutions.toniclifefenix.R;
import com.bigtechsolutions.toniclifefenix.api.AuthApiClient;
import com.bigtechsolutions.toniclifefenix.api.AuthApiService;
import com.bigtechsolutions.toniclifefenix.api.requests.GenerateIntentRequest;
import com.bigtechsolutions.toniclifefenix.api.requests.OrderRequest;
import com.bigtechsolutions.toniclifefenix.api.requests.ProductRequest;
import com.bigtechsolutions.toniclifefenix.commons.Constants;
import com.bigtechsolutions.toniclifefenix.commons.MyFenixApp;
import com.bigtechsolutions.toniclifefenix.commons.SharedPreferencesManager;
import com.bigtechsolutions.toniclifefenix.data.entity.ShoppingCart;
import com.bigtechsolutions.toniclifefenix.viewmodel.OnOrderResponse;
import com.bigtechsolutions.toniclifefenix.viewmodel.OnSuccess;
import com.bigtechsolutions.toniclifefenix.viewmodel.OrderViewModel;
import com.bigtechsolutions.toniclifefenix.viewmodel.ShoppingCartViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentIntentResult;
import com.stripe.android.Stripe;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import com.stripe.android.model.PaymentIntent;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.view.CardInputWidget;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StripePayActivity extends AppCompatActivity implements View.OnClickListener {

    private String paymentIntentClientSecret;
    private Stripe stripe;
    private MaterialButton pay;
    private CardInputWidget cardInputWidget;
    AuthApiClient authApiClient;
    AuthApiService authApiService;
    Toolbar toolbar;
    OrderViewModel orderViewModel;
    ShoppingCartViewModel productViewModel;
    ProgressDialog loading;

    int deliveryAddressId;
    int paymentMethodId;
    ShoppingCartViewModel mViewModel;

    List<ProductRequest> productsRequest = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stripe_pay);

        loading = ProgressDialog.show(this, "Cargando", "Por favor espere...", false, false);


        orderViewModel = new ViewModelProvider(this)
                .get(OrderViewModel.class);

        productViewModel = new ViewModelProvider(this)
                .get(ShoppingCartViewModel.class);

        mViewModel = new ViewModelProvider(this).get(ShoppingCartViewModel.class);

        Bundle bundle = getIntent().getExtras();
        deliveryAddressId = bundle.getInt("addressId");
        paymentMethodId = bundle.getInt("paymentMethodId");


        findViews();
        events();
        toolbarConfig();
        retrofitInit();
        getShoppingCart();
        setStripe();



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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Handle the result of stripe.confirmPayment
        stripe.onPaymentResult(requestCode, data, new PaymentResultCallback(this));
    }

    private void setStripe() {
        stripe = new Stripe(
                getApplicationContext(),
                Objects.requireNonNull("pk_test_51HB9ItGBCPqeJ6dQ6S1Z1s2vcPg7S75x0Zp9iEyIwcN1QZkQ55cDwMLNzIYDisU7rrOcT4uWzVAEoYgDzIOVtjhg00cnRpwmVZ")
        );

        double amount = productViewModel.getTotalOrder();

        GenerateIntentRequest generateIntentRequest = new GenerateIntentRequest(amount,"mxn");

        orderViewModel.generateIntent(generateIntentRequest, new OnSuccess() {
            @Override
            public void OnRequestSuccess() {

            }

            @Override
            public void OnRequestStripeSuccess(String key) {

                paymentIntentClientSecret = key;

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

    private void retrofitInit() {
        authApiClient = AuthApiClient.getInstance();
        authApiService = authApiClient.getAuthApiService();
    }

    private void events() {
        pay.setOnClickListener(this);
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

    private void findViews() {

        pay = findViewById(R.id.stripePayBtn);
        cardInputWidget = findViewById(R.id.cardInputStripe);
        toolbar = findViewById(R.id.toolbarStripe);

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if(id == R.id.stripePayBtn){

            PaymentMethodCreateParams params = cardInputWidget.getPaymentMethodCreateParams();
            if (params != null) {
                loading = ProgressDialog.show(this, "Procesando pago", "Por favor espere...", false, false);
                ConfirmPaymentIntentParams confirmParams = ConfirmPaymentIntentParams
                        .createWithPaymentMethodCreateParams(params, paymentIntentClientSecret);
                stripe.confirmPayment(this, confirmParams);
            }

        }

    }

    private static final class PaymentResultCallback
            implements ApiResultCallback<PaymentIntentResult> {
        @NonNull
        private final WeakReference<StripePayActivity> activityRef;
        PaymentResultCallback(@NonNull StripePayActivity activity) {
            activityRef = new WeakReference<>(activity);
        }
        @Override
        public void onSuccess(@NonNull PaymentIntentResult result) {
            final StripePayActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }
            PaymentIntent paymentIntent = result.getIntent();
            PaymentIntent.Status status = paymentIntent.getStatus();
            if (status == PaymentIntent.Status.Succeeded) {
                // Payment completed successfully

                int distributorId = SharedPreferencesManager.getIntValue(Constants.DISTRIBUTOR_ID);
                int branchId = SharedPreferencesManager.getIntValue(Constants.BRANCH_ID);

                OrderRequest orderRequest = new OrderRequest(activity.deliveryAddressId, distributorId, branchId, activity.paymentMethodId, activity.productsRequest);

                activity.orderViewModel.saveOrder(orderRequest, new OnOrderResponse() {
                    @Override
                    public void OnSuccess(String title, String message) {

                        activity.mViewModel.deleteAll();
                        SharedPreferencesManager.removeValue(Constants.BRANCH_ID);

                        activity.loading.dismiss();

                        activity.displayAlert(
                                title,
                                message
                        );

                    }

                    @Override
                    public void OnError(String title, String message) {

                        activity.loading.dismiss();

                        activity.displayAlert(
                                title,
                                "El pago se procesó, pero algo salió mal al guardar tu orden de compra, ponte en contacto de inmediato con el administrador."
                        );

                    }
                });

            } else if (status == PaymentIntent.Status.RequiresPaymentMethod) {
                // Payment failed – allow retrying using a different payment method
                activity.displayAlert(
                        "El pago no se pudo realizar",
                        Objects.requireNonNull(paymentIntent.getLastPaymentError()).getMessage()
                );
            }
        }
        @Override
        public void onError(@NonNull Exception e) {
            final StripePayActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }
            // Payment request failed – allow retrying using the same payment method
            activity.loading.dismiss();
            activity.displayAlert("Error", e.toString());
        }
    }

    private void displayAlert(@NonNull String title,
                              @Nullable String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(MyFenixApp.getContext(), ShoppingCartActivity.class);
                startActivity(i);
                finish();
            }
        });
        builder.setCancelable(false);
        builder.create().show();
    }
}