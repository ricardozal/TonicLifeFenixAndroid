package mx.com.bigtechsolutions.toniclifefenix.ui.share_points;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import mx.com.bigtechsolutions.toniclifefenix.R;
import mx.com.bigtechsolutions.toniclifefenix.api.requests.OrderRequest;
import mx.com.bigtechsolutions.toniclifefenix.api.requests.ProductRequest;
import mx.com.bigtechsolutions.toniclifefenix.commons.Constants;
import mx.com.bigtechsolutions.toniclifefenix.commons.MyFenixApp;
import mx.com.bigtechsolutions.toniclifefenix.commons.SharedPreferencesManager;
import mx.com.bigtechsolutions.toniclifefenix.data.entity.ShoppingCart;
import mx.com.bigtechsolutions.toniclifefenix.ui.NewDistributorActivity;
import mx.com.bigtechsolutions.toniclifefenix.ui.shoppingcart.ShoppingCartActivity;
import mx.com.bigtechsolutions.toniclifefenix.viewmodel.OrderViewModel;
import mx.com.bigtechsolutions.toniclifefenix.viewmodel.ShoppingCartViewModel;
import mx.com.bigtechsolutions.toniclifefenix.viewmodel.interfaces.OnOrderResponse;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class FinishProcessActivity extends AppCompatActivity {

    int deliveryAddressId;
    OrderViewModel orderViewModel;
    ShoppingCartViewModel productViewModel;
    ProgressDialog loading;
    List<ProductRequest> productsRequest = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_process);

        Bundle bundle = getIntent().getExtras();
        deliveryAddressId = bundle.getInt("addressId");

        orderViewModel = new ViewModelProvider(this)
                .get(OrderViewModel.class);

        productViewModel = new ViewModelProvider(this)
                .get(ShoppingCartViewModel.class);

        getShoppingCart();


    }

    private void getShoppingCart() {


        productViewModel.getAll().observe(this, new Observer<List<ShoppingCart>>() {
            @Override
            public void onChanged(List<ShoppingCart> shoppingCarts) {
                for ( ShoppingCart product : shoppingCarts ) {
                    productsRequest.add(new ProductRequest(product.getProductId(),product.getQuantity()));

                }
                productViewModel.getAll().removeObserver(this);
                saveOrder();
            }
        });


    }

    private void saveOrder() {

        loading = ProgressDialog.show(this, "Cargando", "Por favor espere...", false, false);

        int distributorId = SharedPreferencesManager.getIntValue(Constants.DISTRIBUTOR_ID);
        int branchId = SharedPreferencesManager.getIntValue(Constants.BRANCH_ID);

        OrderRequest orderRequest = new OrderRequest(this.deliveryAddressId, distributorId, branchId, 2, this.productsRequest);

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
                        orderId
                );

            }

            @Override
            public void OnError(String title, String message) {

               loading.dismiss();

                displayAlert(
                        title,
                        message,
                        false,
                        0
                );

            }
        });
    }

    private void displayAlert(@NonNull String title,
                              @Nullable String message,
                              boolean isKit,
                              int orderId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

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

            }
        });
        builder.setCancelable(false);
        builder.create().show();
    }
}