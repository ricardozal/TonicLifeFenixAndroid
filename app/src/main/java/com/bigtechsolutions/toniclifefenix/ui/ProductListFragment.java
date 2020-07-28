package com.bigtechsolutions.toniclifefenix.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigtechsolutions.toniclifefenix.R;
import com.bigtechsolutions.toniclifefenix.api.responses.models.Product;
import com.bigtechsolutions.toniclifefenix.commons.MyFenixApp;
import com.bigtechsolutions.toniclifefenix.ui.shoppingcart.ShoppingCartActivity;
import com.bigtechsolutions.toniclifefenix.viewmodel.ProductViewModel;

import java.util.List;

public class ProductListFragment extends Fragment implements MyProductRecyclerViewAdapter.OnProductlistener {

    RecyclerView recyclerView;
    MyProductRecyclerViewAdapter adapter;
    List<Product> productList;
    ProductViewModel productViewModel;
    AppCompatImageButton goShoppingCart;
    ProgressDialog loading;

    public ProductListFragment() {
    }

    @SuppressWarnings("unused")
    public static ProductListFragment newInstance(int columnCount) {
        ProductListFragment fragment = new ProductListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        productViewModel = new ViewModelProvider(getActivity())
                .get(ProductViewModel.class);

       loading = ProgressDialog.show(getActivity(), "Cargando", "Por favor espere...", false, false);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);


        recyclerView = view.findViewById(R.id.products_list);
        goShoppingCart = view.findViewById(R.id.shopping_cart_icon);


        adapter = new MyProductRecyclerViewAdapter(
                getActivity(),
                productList,
                this
        );

        recyclerView.setAdapter(adapter);

        loadProductData();

        goShoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MyFenixApp.getContext(), ShoppingCartActivity.class);
                startActivity(i);
            }
        });


        return view;
    }

    private void loadProductData() {

        productViewModel.getProducts().observe(getActivity(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                productList = products;
                adapter.setDataList(productList);
            }
        });

        productViewModel.getDownloadFinished().observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean downloadFinished) {
                if (downloadFinished != null) {
                    if (downloadFinished) {
                        loading.dismiss();
                    }
                }
            }
        });

    }

    @Override
    public void onProductClick(int position) {

        Intent i = new Intent(MyFenixApp.getContext(), ProductDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("productId", productList.get(position).getId());
        i.putExtras(bundle);
        startActivity(i);

    }
}