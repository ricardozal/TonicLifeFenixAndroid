package com.bigtechsolutions.toniclifefenix.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bigtechsolutions.toniclifefenix.R;
import com.bigtechsolutions.toniclifefenix.api.responses.models.Product;
import com.bigtechsolutions.toniclifefenix.commons.MyFenixApp;
import com.bigtechsolutions.toniclifefenix.data.entity.ShoppingCart;
import com.bigtechsolutions.toniclifefenix.viewmodel.ProductViewModel;
import com.bigtechsolutions.toniclifefenix.viewmodel.ShoppingCartViewModel;
import android.app.AlertDialog;
import java.util.List;

public class ShoppingCartActivity extends AppCompatActivity implements ShoppingCartRecyclerViewAdapter.OnRemovelistener, ShoppingCartRecyclerViewAdapter.OnChangeQuantitylistener {

    RecyclerView recyclerView;
    ShoppingCartRecyclerViewAdapter adapter;
    List<ShoppingCart> productList;
    ShoppingCartViewModel productViewModel;
    Toolbar toolbar;
    TextView totalCart, emptyCartTxt;
    AlertDialog.Builder builder;

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
        FragmentManager fm = getSupportFragmentManager();
        ShoppingCartChangeQuantityFragment dialog = new ShoppingCartChangeQuantityFragment();
        dialog.show(fm, "ShoppingCartChangeQuantityFragment");
    }
}