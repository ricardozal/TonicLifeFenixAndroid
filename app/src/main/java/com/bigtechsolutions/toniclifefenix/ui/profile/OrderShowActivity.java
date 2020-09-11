package com.bigtechsolutions.toniclifefenix.ui.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bigtechsolutions.toniclifefenix.R;
import com.bigtechsolutions.toniclifefenix.api.responses.models.Order;
import com.bigtechsolutions.toniclifefenix.api.responses.models.ProductOrder;
import com.bigtechsolutions.toniclifefenix.commons.MyFenixApp;
import com.bigtechsolutions.toniclifefenix.ui.profile.register_points.RegisterPointsActivity;
import com.bigtechsolutions.toniclifefenix.viewmodel.OrderViewModel;
import com.bigtechsolutions.toniclifefenix.viewmodel.interfaces.OnOrderItemResponse;
import com.bigtechsolutions.toniclifefenix.viewmodel.interfaces.OnResponse;
import com.google.android.material.button.MaterialButton;

public class OrderShowActivity extends AppCompatActivity implements View.OnClickListener {

    OrderViewModel orderViewModel;
    ProgressDialog loading;
    int orderId, countryId;
    Double price, points;
    TextView id, date, totalPrice, totalPoints, totalTaxes, shippingPrice, products, status, paymentMethod, delivery;
    Toolbar toolbar;
//    MaterialButton pointsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_show);

        findViews();

//        pointsBtn.setOnClickListener(this);

        loading = ProgressDialog.show(this, "Cargando", "Por favor espere...", false, false);

        orderViewModel = new ViewModelProvider(this)
                .get(OrderViewModel.class);

        toolbar = findViewById(R.id.toolbarOrder);

        toolbarConfig();

        Bundle bundle = getIntent().getExtras();
        orderId = bundle.getInt("orderId");

        getData();

    }

    private void getData() {
        orderViewModel.getOrder(orderId, new OnOrderItemResponse() {
            @Override
            public void OnSuccess(String title, String message, Order order) {

                price = Double.parseDouble(order.getTotalPrice().replace(",",""));
                points =  Double.parseDouble(order.getTotalPoints().replace(",",""));
                countryId = order.getCountryId();


                String folio = "Folio: "+order.getId();
                String dateStr = "Fecha de compra: "+order.getDate();
                String priceStr = "Total: $"+order.getTotalPrice();
                String taxStr = "Impuestos: "+order.getTotalTaxes();
                String pointsStr = "Puntos totales: "+order.getTotalPoints();
                String shippingStr = "Envío: "+order.getShippingPrice();
                String statusStr = "Status: "+order.getStatus();
                String productsStr = "";

                for (ProductOrder product:
                        order.getProducts()) {

                    productsStr += "Producto: "+product.getName()+"\n"+
                            "Puntos: "+product.getPoints()+"\n"+
                            "Precio: "+product.getPrice()+"\n"+
                            "Cantidad: "+product.getQuantity()+"\n"+
                            "Total: "+product.getTotal()+"\n"+
                            "Puntos acumulados: "+product.getAccumulatedPoints()+"\n\n";

                }

                id.setText(folio);
                date.setText(dateStr);
                totalPrice.setText(priceStr);
                totalPoints.setText(pointsStr);
                totalTaxes.setText(taxStr);
                shippingPrice.setText(shippingStr);
                status.setText(statusStr);
                paymentMethod.setText(order.getPaymentMethod());
                delivery.setText(order.getDelivery());
                products.setText(productsStr);

                toolbar.setTitle(folio);

                loading.dismiss();
            }

            @Override
            public void OnError(String title, String message) {
                loading.dismiss();
                displayAlert(title,message);
            }
        });
    }


    private void findViews() {
        id = findViewById(R.id.order_id);
        date = findViewById(R.id.order_date);
        totalPrice = findViewById(R.id.order_total_price);
        totalPoints = findViewById(R.id.order_total_points);
        totalTaxes = findViewById(R.id.order_total_tax);
        shippingPrice = findViewById(R.id.order_shipping);
        products = findViewById(R.id.order_products);
        status = findViewById(R.id.order_status);
        paymentMethod = findViewById(R.id.order_payment_method);
        delivery = findViewById(R.id.order_delivery);
//        pointsBtn = findViewById(R.id.pointsBtn);
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

    private void displayAlert(@NonNull String title,
                              @Nullable String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setCancelable(false);
        builder.create().show();
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

//        if(id == R.id.pointsBtn){
//
//            loading = ProgressDialog.show(this, "Cargando", "Por favor espere...", false, false);
//
//
//            orderViewModel.validateRegisterPoints(orderId, new OnResponse() {
//                @Override
//                public void OnSuccess(String title, String message) {
//                    loading.dismiss();
//
//                    Intent i = new Intent(MyFenixApp.getContext(), RegisterPointsActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("orderId", orderId);
//                    bundle.putDouble("price", price);
//                    bundle.putDouble("points", points);
//                    bundle.putInt("countryId", countryId);
//                    i.putExtras(bundle);
//                    startActivity(i);
//
//                }
//
//                @Override
//                public void OnError(String title, String message) {
//                    loading.dismiss();
//                    displayAlert(title,message);
//                }
//            });
//
//        }

    }
}