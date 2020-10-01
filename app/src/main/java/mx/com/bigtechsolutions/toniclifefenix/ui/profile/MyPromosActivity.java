package mx.com.bigtechsolutions.toniclifefenix.ui.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import mx.com.bigtechsolutions.toniclifefenix.R;
import mx.com.bigtechsolutions.toniclifefenix.api.responses.models.OrderItem;
import mx.com.bigtechsolutions.toniclifefenix.api.responses.models.Promotion;
import mx.com.bigtechsolutions.toniclifefenix.commons.MyFenixApp;
import mx.com.bigtechsolutions.toniclifefenix.ui.BottomNavigationActivity;
import mx.com.bigtechsolutions.toniclifefenix.viewmodel.DistributorViewModel;
import mx.com.bigtechsolutions.toniclifefenix.viewmodel.OrderViewModel;

import java.util.List;

public class MyPromosActivity extends AppCompatActivity {

    DistributorViewModel distributorViewModel;
    ProgressDialog loading;
    RecyclerView recyclerView;
    Toolbar toolbar;
    MyPromosAdapter adapter;
    List<Promotion> promotionList;
    TextView txtEmptyPromos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_promos);

        loading = ProgressDialog.show(this, "Cargando", "Por favor espere...", false, false);

        distributorViewModel = new ViewModelProvider(this)
                .get(DistributorViewModel.class);

        recyclerView = findViewById(R.id.promos_list);
        toolbar = findViewById(R.id.toolbarPromos);
        txtEmptyPromos = findViewById(R.id.txtEmptyPromos);

        toolbarConfig();

        adapter = new MyPromosAdapter(
                this,
                promotionList
        );

        recyclerView.setAdapter(adapter);

        loadPromosData();

    }

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }

    private void loadPromosData() {
        distributorViewModel.getPromotions().observe(this, new Observer<List<Promotion>>() {
            @Override
            public void onChanged(List<Promotion> promotions) {
                promotionList = promotions;
                if(promotionList.size() < 1)
                    txtEmptyPromos.setVisibility(View.VISIBLE);
                adapter.setDataList(promotions);
            }
        });

        distributorViewModel.getDownloadFinished().observe(this, new Observer<Boolean>() {
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
}