package com.bigtechsolutions.toniclifefenix.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.bigtechsolutions.toniclifefenix.R;
import com.bigtechsolutions.toniclifefenix.api.AuthApiClient;
import com.bigtechsolutions.toniclifefenix.api.AuthApiService;
import com.bigtechsolutions.toniclifefenix.api.responses.GenericResponse;
import com.bigtechsolutions.toniclifefenix.api.responses.models.Product;
import com.bigtechsolutions.toniclifefenix.commons.MyFenixApp;
import com.bigtechsolutions.toniclifefenix.data.entity.ShoppingCart;
import com.bigtechsolutions.toniclifefenix.login.MainActivity;
import com.bigtechsolutions.toniclifefenix.viewmodel.ProductViewModel;
import com.bigtechsolutions.toniclifefenix.viewmodel.ShoppingCartViewModel;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity implements View.OnClickListener {

    EditText productNameHidden, productIdHidden, productImageUrlHidden, productPriceHidden, productPointsHidden;
    TextView categoryTxt, distributorPriceTxt, taxTxt, totalTxt, pointsTxt;
    Toolbar toolbar;
    CollapsingToolbarLayout toolbarLayout;
    FloatingActionButton addToShoppingCart;
    AppCompatImageView productImage;

    ShoppingCartViewModel mViewModel;

    AuthApiClient authApiClient;
    AuthApiService authApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        findViews();
        events();
        toolbarConfig();
        retrofitInit();

        Bundle bundle = getIntent().getExtras();

        setProductInfo(bundle.getInt("productId"));

        mViewModel = new ViewModelProvider(this).get(ShoppingCartViewModel.class);

    }

    private void findViews() {

        toolbar = findViewById(R.id.toolbar_product_details);
        toolbarLayout = findViewById(R.id.toolbar_layout);
        addToShoppingCart = findViewById(R.id.add_shopping_cart);

        productIdHidden = findViewById(R.id.productIdHidden);
        productNameHidden = findViewById(R.id.productNameHidden);
        productImageUrlHidden = findViewById(R.id.productImageUrlHidden);
        productPriceHidden = findViewById(R.id.productPriceHidden);
        productPointsHidden = findViewById(R.id.productPointsHidden);

        categoryTxt = findViewById(R.id.categoryDetails);
        distributorPriceTxt = findViewById(R.id.distributorPriceDetails);
        taxTxt = findViewById(R.id.taxDetails);
        totalTxt = findViewById(R.id.totalDetails);
        pointsTxt = findViewById(R.id.pointsDetails);

        productImage = findViewById(R.id.image_product_detail);


    }

    private void toolbarConfig() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        toolbarLayout.setExpandedTitleTextAppearance(R.style.Theme_TonicLife);
        toolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.colorWhite));
        toolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorWhite));
        toolbarLayout.setCollapsedTitleTextAppearance(R.style.Theme_TonicLife);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void events() {

        addToShoppingCart.setOnClickListener(this);

    }

    private void setProductInfo(int productId){

        Call<GenericResponse<Product>> call = authApiService.productDetails(productId);

        final ProgressDialog loading = ProgressDialog.show(this, "Cargando", "Por favor espere...", false, false);

        call.enqueue(new Callback<GenericResponse<Product>>() {
            @Override
            public void onResponse(Call<GenericResponse<Product>> call, Response<GenericResponse<Product>> response) {

                if(response.isSuccessful())
                {
                    if (response.body().isSuccess()){

                        Product product = response.body().getData();

                        productIdHidden.setText(String.valueOf(product.getId()));
                        productNameHidden.setText(product.getName());
                        productImageUrlHidden.setText(product.getImageUrl());
                        productPriceHidden.setText(String.valueOf(product.getTotal()));
                        productPointsHidden.setText(String.valueOf(product.getPoints()));

                        String distributorPrice = "Precio distribuidor: $" + product.getDistributorPrice();
                        String tax = "Impuesto: $" + product.getTax();
                        String price = "Precio neto: $" + product.getTotal();
                        String points = "Puntos: " + product.getPoints();

                        categoryTxt.setText(product.getCategory());
                        distributorPriceTxt.setText(distributorPrice);
                        taxTxt.setText(tax);
                        totalTxt.setText(price);
                        pointsTxt.setText(points);

                        Glide.with(getApplicationContext())
                                .load(product.getImageUrl()).into(productImage);

                        toolbarLayout.setTitle(product.getName());

                        loading.dismiss();

                    } else{
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        loading.dismiss();

                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Error en el servidor", Toast.LENGTH_SHORT).show();

                    loading.dismiss();

                }

            }

            @Override
            public void onFailure(Call<GenericResponse<Product>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error en la conexión", Toast.LENGTH_SHORT).show();

                loading.dismiss();
            }
        });
    }

    private void retrofitInit() {

        authApiClient = AuthApiClient.getInstance();
        authApiService = authApiClient.getAuthApiService();

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.add_shopping_cart) {

            String productName = productNameHidden.getText().toString();
            double price = Double.parseDouble(productPriceHidden.getText().toString());
            double points = Double.parseDouble(productPointsHidden.getText().toString());
            String imageUrl = productImageUrlHidden.getText().toString();
            int productId = Integer.parseInt(productIdHidden.getText().toString());

            mViewModel.insert(new ShoppingCart(productName, price,points, imageUrl, 2,productId));
        }

    }
}