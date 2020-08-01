package com.bigtechsolutions.toniclifefenix.ui.shoppingcart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bigtechsolutions.toniclifefenix.R;
import com.bigtechsolutions.toniclifefenix.api.AuthApiClient;
import com.bigtechsolutions.toniclifefenix.api.AuthApiService;
import com.bigtechsolutions.toniclifefenix.api.requests.GenerateIntentRequest;
import com.bigtechsolutions.toniclifefenix.commons.MyFenixApp;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stripe_pay);

        loading = ProgressDialog.show(this, "Cargando", "Por favor espere...", false, false);


        orderViewModel = new ViewModelProvider(this)
                .get(OrderViewModel.class);

        productViewModel = new ViewModelProvider(this)
                .get(ShoppingCartViewModel.class);

        findViews();
        events();
        toolbarConfig();
        retrofitInit();
        setStripe();



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
            activity.loading.dismiss();
            if (status == PaymentIntent.Status.Succeeded) {
                // Payment completed successfully
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                activity.displayAlert(
                        "Payment completed",
                        gson.toJson(paymentIntent)
                );
            } else if (status == PaymentIntent.Status.RequiresPaymentMethod) {
                // Payment failed – allow retrying using a different payment method
                activity.displayAlert(
                        "Payment failed",
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
        builder.setPositiveButton("Ok", null);
        builder.create().show();
    }
}