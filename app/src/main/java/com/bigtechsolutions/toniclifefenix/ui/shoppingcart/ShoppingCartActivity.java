package com.bigtechsolutions.toniclifefenix.ui.shoppingcart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bigtechsolutions.toniclifefenix.R;
import com.bigtechsolutions.toniclifefenix.commons.MyFenixApp;
import com.bigtechsolutions.toniclifefenix.data.entity.ShoppingCart;
import com.bigtechsolutions.toniclifefenix.ui.BottomNavigationActivity;
import com.bigtechsolutions.toniclifefenix.viewmodel.ShoppingCartViewModel;
import com.google.android.material.button.MaterialButton;

import android.app.AlertDialog;
import java.util.List;

public class ShoppingCartActivity extends AppCompatActivity implements ShoppingCartRecyclerViewAdapter.OnRemovelistener,
        ShoppingCartRecyclerViewAdapter.OnChangeQuantitylistener, View.OnClickListener {

    RecyclerView recyclerView;
    ShoppingCartRecyclerViewAdapter adapter;
    List<ShoppingCart> productList;
    ShoppingCartViewModel productViewModel;
    Toolbar toolbar;
    TextView totalCart, emptyCartTxt;
    AlertDialog.Builder builder;
    MaterialButton checkoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        productViewModel = new ViewModelProvider(this)
                .get(ShoppingCartViewModel.class);

        recyclerView = findViewById(R.id.shopping_card_list);
        toolbar = findViewById(R.id.toolbarCart);
        totalCart = findViewById(R.id.totalCart);
        emptyCartTxt = findViewById(R.id.txtEmptyCart);
        checkoutBtn = findViewById(R.id.checkoutBtn);

        checkoutBtn.setOnClickListener(this);

        toolbarConfig();

        adapter = new ShoppingCartRecyclerViewAdapter(
                this,
                productList,this,this);

        recyclerView.setAdapter(adapter);

        loadProductData();

        builder = new AlertDialog.Builder(this);

        double totalOrder = productViewModel.getTotalOrder() != null ? productViewModel.getTotalOrder() : 0.00;

        String total = "$" + totalOrder;

        totalCart.setText(total);

    }

    private void toolbarConfig() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MyFenixApp.getContext(), BottomNavigationActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }

    private void loadProductData() {

        productViewModel.getAll().observe(this, new Observer<List<ShoppingCart>>() {
            @Override
            public void onChanged(List<ShoppingCart> shoppingCarts) {
                productList = shoppingCarts;
                if(productList.size() < 1)
                    emptyCartTxt.setVisibility(View.VISIBLE);
                adapter.setDataList(shoppingCarts);
            }
        });

    }

    @Override
    public void onRemoveClick(int position) {

        builder.setTitle("Atención");
        builder.setMessage("¿Estás seguro de eliminar el producto seleccionado de tu carrito de compras?");
        builder.setCancelable(false);
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                productViewModel.deleteById(productList.get(position).getId());

                double totalOrder = 0.00;


                if(productList.size() > 1)
                {
                    for (ShoppingCart product:productList) {
                        if(product.getId() != productList.get(position).getId())
                            totalOrder += (product.getQuantity() * product.getPrice());
                    }
                }

                if(productList.size() == 1)
                    emptyCartTxt.setVisibility(View.VISIBLE);


                String total = "$" + totalOrder;
                totalCart.setText(total);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();
    }

    @Override
    public void onChangeQuantityClick(int position) {

        Bundle bundle = new Bundle();
        bundle.putInt("productId", productList.get(position).getProductId());

        FragmentManager fm = getSupportFragmentManager();
        ShoppingCartChangeQuantityFragment dialog = new ShoppingCartChangeQuantityFragment();
        dialog.setArguments(bundle);
        dialog.show(fm, "ShoppingCartChangeQuantityFragment");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(R.id.checkoutBtn == id)
        {
            FragmentManager fm = getSupportFragmentManager();
            ChooseDeliveryFragment dialog = new ChooseDeliveryFragment();
            dialog.show(fm, "ChooseDeliveryFragment");
        }

    }
}