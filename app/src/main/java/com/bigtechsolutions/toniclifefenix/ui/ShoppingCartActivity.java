package com.bigtechsolutions.toniclifefenix.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.List;

public class ShoppingCartActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ShoppingCartRecyclerViewAdapter adapter;
    List<ShoppingCart> productList;
    ShoppingCartViewModel productViewModel;
    Toolbar toolbar;
    TextView totalCart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        productViewModel = new ViewModelProvider(this)
                .get(ShoppingCartViewModel.class);

        recyclerView = findViewById(R.id.shopping_card_list);
        toolbar = findViewById(R.id.toolbarCart);
        totalCart = findViewById(R.id.totalCart);

        toolbarConfig();

        adapter = new ShoppingCartRecyclerViewAdapter(
                this,
                productList);

        recyclerView.setAdapter(adapter);

        loadProductData();

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
//                productList = shoppingCarts;
                adapter.setDataList(shoppingCarts);
            }
        });

    }
}