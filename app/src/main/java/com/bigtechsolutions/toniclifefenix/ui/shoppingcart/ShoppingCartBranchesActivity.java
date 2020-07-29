package com.bigtechsolutions.toniclifefenix.ui.shoppingcart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bigtechsolutions.toniclifefenix.R;
import com.bigtechsolutions.toniclifefenix.api.responses.models.Branch;
import com.bigtechsolutions.toniclifefenix.commons.MyFenixApp;
import com.bigtechsolutions.toniclifefenix.viewmodel.AddressViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class ShoppingCartBranchesActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerView;
    BranchesRecyclerViewAdapter adapter;
    List<Branch> addressesList;
    AddressViewModel addressViewModel;
    Toolbar toolbar;
    MaterialButton continueToPayBtn;
    ProgressDialog loading;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_addresses_branch_list);

        loading = ProgressDialog.show(this, "Cargando", "Por favor espere...", false, false);


        addressViewModel = new ViewModelProvider(this)
                .get(AddressViewModel.class);

        recyclerView = findViewById(R.id.addresses_branch_list);
        toolbar = findViewById(R.id.toolbarAddressBranchCart);
        continueToPayBtn = findViewById(R.id.continueToPayBranchBtn);

        continueToPayBtn.setOnClickListener(this);

        toolbarConfig();

        adapter = new BranchesRecyclerViewAdapter(
                this,
                addressesList
        );

        recyclerView.setAdapter(adapter);

        loadAddressData();
    }


    private void toolbarConfig() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MyFenixApp.getContext(), ShoppingCartActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    private void loadAddressData() {

        addressViewModel.getBranches().observe(this, new Observer<List<Branch>>() {
            @Override
            public void onChanged(List<Branch> branches) {
                addressesList = branches;
                adapter.setDataList(branches);
            }
        });

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

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(R.id.continueToPayBranchBtn == id)
        {
            Intent i = new Intent(MyFenixApp.getContext(), CheckOutActivity.class);
            Bundle bundle = new Bundle();
            bundle.putBoolean("branchSelected", true);
            i.putExtras(bundle);
            startActivity(i);
            finish();
        }
    }
}
