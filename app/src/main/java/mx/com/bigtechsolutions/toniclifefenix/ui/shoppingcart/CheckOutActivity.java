package mx.com.bigtechsolutions.toniclifefenix.ui.shoppingcart;

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
import mx.com.bigtechsolutions.toniclifefenix.api.AuthApiClient;
import mx.com.bigtechsolutions.toniclifefenix.api.AuthApiService;
import mx.com.bigtechsolutions.toniclifefenix.api.requests.ProductRequest;
import mx.com.bigtechsolutions.toniclifefenix.api.requests.ValidateInvRequest;
import mx.com.bigtechsolutions.toniclifefenix.api.responses.models.Address;
import mx.com.bigtechsolutions.toniclifefenix.api.responses.models.Branch;
import mx.com.bigtechsolutions.toniclifefenix.commons.Constants;
import mx.com.bigtechsolutions.toniclifefenix.commons.MyFenixApp;
import mx.com.bigtechsolutions.toniclifefenix.commons.SharedPreferencesManager;
import mx.com.bigtechsolutions.toniclifefenix.data.entity.ShoppingCart;
import mx.com.bigtechsolutions.toniclifefenix.ui.BottomNavigationActivity;
import mx.com.bigtechsolutions.toniclifefenix.ui.ChooseCountryFragment;
import mx.com.bigtechsolutions.toniclifefenix.ui.share_points.ChooseSharePoints;
import mx.com.bigtechsolutions.toniclifefenix.viewmodel.AddressViewModel;
import mx.com.bigtechsolutions.toniclifefenix.viewmodel.interfaces.OnResponse;
import mx.com.bigtechsolutions.toniclifefenix.viewmodel.interfaces.OnSuccess;
import mx.com.bigtechsolutions.toniclifefenix.viewmodel.OrderViewModel;
import mx.com.bigtechsolutions.toniclifefenix.viewmodel.ShoppingCartViewModel;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.button.MaterialButton;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CheckOutActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    CollapsingToolbarLayout toolbarLayout;
    MaterialButton toPay;
    AppBarLayout appBarLayout;

    TextView countProductsCheckout, totalPayCheckout, shippingPriceCheckout, totalToPayCheckout;
    TextView deliveryMethod, aliasAddress, fullAddress;

    RecyclerView productListCheckout;
    CheckoutProductsRecyclerViewAdapter adapter;
    List<ShoppingCart> productList;

    AuthApiClient authApiClient;
    AuthApiService authApiService;

    ShoppingCartViewModel productViewModel;
    AddressViewModel addressViewModel;
    OrderViewModel orderViewModel;

    boolean branchSelected;

    ProgressDialog loading;

    int deliveryAddressId;

    List<ProductRequest> productsRequest = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        loading = ProgressDialog.show(this, "Cargando", "Por favor espere...", false, false);

        productViewModel = new ViewModelProvider(this)
                .get(ShoppingCartViewModel.class);

        addressViewModel = new ViewModelProvider(this)
                .get(AddressViewModel.class);

        orderViewModel = new ViewModelProvider(this)
                .get(OrderViewModel.class);

        Bundle bundle = getIntent().getExtras();
        branchSelected = bundle.getBoolean("branchSelected");

        findViews();
        events();
        toolbarConfig();
        retrofitInit();
        setHeaderData();
        setRecyclerView();
        setAddressDelivery();

    }

    private void setRecyclerView() {

        adapter = new CheckoutProductsRecyclerViewAdapter(
                this,
                productList);

        productListCheckout.setAdapter(adapter);

        productViewModel.getAll().observe(this, new Observer<List<ShoppingCart>>() {
            @Override
            public void onChanged(List<ShoppingCart> shoppingCarts) {
                productList = shoppingCarts;

                for ( ShoppingCart product : shoppingCarts ) {

                    productsRequest.add(new ProductRequest(product.getProductId(),product.getQuantity()));

                }

                adapter.setDataList(shoppingCarts);
            }
        });

    }

    private void setHeaderData() {

        DecimalFormat f = new DecimalFormat("##.00");

        double totalOrder = productViewModel.getTotalOrder();
        int countProduct = productViewModel.getCountProducts();

        String countProductStr = "Productos("+countProduct+")";
        String totalPayStr = "$"+f.format(totalOrder);
        String shippingPriceStr = "Costo de envío variable (Pendiente), en caso de entrega a domicilio";
        String totalToPayStr = "$"+f.format(totalOrder);

        countProductsCheckout.setText(countProductStr);
        totalPayCheckout.setText(totalPayStr);
        shippingPriceCheckout.setText(shippingPriceStr);
        totalToPayCheckout.setText(totalToPayStr);

    }

    private void setAddressDelivery() {

        if(branchSelected){
            deliveryMethod.setText("Recoger en sucursal");

            addressViewModel.getBranches().observe(this, new Observer<List<Branch>>() {
                @Override
                public void onChanged(List<Branch> branches) {

                    for ( Branch branch : branches ) {

                        if(branch.getId().equals(SharedPreferencesManager.getIntValue(Constants.BRANCH_ID))){

                            aliasAddress.setText(branch.getName());
                            fullAddress.setText(branch.getAddress().getFullAddress());

                        }

                    }

                }
            });


        } else{
            deliveryMethod.setText("Entrega a domicilio");

            addressViewModel.getAddresses().observe(this, new Observer<List<Address>>() {
                @Override
                public void onChanged(List<Address> addresses) {

                    for ( Address address : addresses ) {

                        if(address.isSelected()){
                            aliasAddress.setText(address.getAlias());
                            fullAddress.setText(address.getFullAddress());
                            deliveryAddressId = address.getId();
                        }

                    }

                }
            });

        }

        addressViewModel.getDownloadFinished().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean downloadFinished) {
                if (downloadFinished != null) {
                    if (downloadFinished) {
                        loading.dismiss();
                    }
                }
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

    private void toolbarConfig() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        toolbarLayout.setExpandedTitleTextAppearance(R.style.Theme_TonicLife);
        toolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.colorWhite));
        toolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorWhite));
        toolbarLayout.setCollapsedTitleTextAppearance(R.style.Theme_TonicLife);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    toolbarLayout.setTitle("Confirmar tu compra");
                    isShow = true;
                } else if(isShow) {
                    toolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MyFenixApp.getContext(), ShoppingCartActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void events() {

        toPay.setOnClickListener(this);

    }

    private void findViews() {

        toolbar = findViewById(R.id.toolbar_product_checkout);
        toolbarLayout = findViewById(R.id.toolbar_checkout_layout);
        appBarLayout = findViewById(R.id.product_checkout_bar);
        toPay = findViewById(R.id.payBtn);

        deliveryMethod = findViewById(R.id.deliveryMethodCheckout);
        aliasAddress = findViewById(R.id.alias_address_checkout);
        fullAddress = findViewById(R.id.address_checkout);

        countProductsCheckout = findViewById(R.id.countProductsCheckout);
        totalPayCheckout = findViewById(R.id.totalPayCheckout);
        shippingPriceCheckout = findViewById(R.id.shipping_price_checkout);
        totalToPayCheckout = findViewById(R.id.total_to_pay_checkout);

        productListCheckout = findViewById(R.id.product_list_checkout);


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(R.id.payBtn == id)
        {

            loading = ProgressDialog.show(this, "Cargando", "Por favor espere...", false, false);

            int addressId = branchSelected ? 0 : deliveryAddressId;
            int branchId = branchSelected ? SharedPreferencesManager.getIntValue(Constants.BRANCH_ID) : 0;

            ValidateInvRequest validateInvRequest = new ValidateInvRequest(addressId,branchId, productsRequest);

            orderViewModel.validateInventory(validateInvRequest, new OnResponse() {
                @Override
                public void OnSuccess(String title, String message) {

                    Bundle bundle = new Bundle();
                    bundle.putInt("deliveryAddressId", addressId);

                    FragmentManager fm = getSupportFragmentManager();
                    ChooseSharePoints dialog = new ChooseSharePoints();
                    dialog.setCancelable(false);
                    dialog.setArguments(bundle);
                    dialog.show(fm, "ChooseSharePoints");

                }

                @Override
                public void OnError(String title, String message) {
                    displayAlert(title, message);
                }
            });

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
                dialog.dismiss();
            }
        });
        builder.setCancelable(false);
        builder.create().show();
    }
}