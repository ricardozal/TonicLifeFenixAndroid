package mx.com.bigtechsolutions.toniclifefenix.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import mx.com.bigtechsolutions.toniclifefenix.R;
import mx.com.bigtechsolutions.toniclifefenix.api.ApiClient;
import mx.com.bigtechsolutions.toniclifefenix.api.AuthApiClient;
import mx.com.bigtechsolutions.toniclifefenix.api.AuthApiService;
import mx.com.bigtechsolutions.toniclifefenix.api.responses.GenericResponse;
import mx.com.bigtechsolutions.toniclifefenix.commons.Constants;
import mx.com.bigtechsolutions.toniclifefenix.commons.MyFenixApp;
import mx.com.bigtechsolutions.toniclifefenix.commons.SharedPreferencesManager;
import mx.com.bigtechsolutions.toniclifefenix.login.MainActivity;
import mx.com.bigtechsolutions.toniclifefenix.ui.profile.MyAddressesActivity;
import mx.com.bigtechsolutions.toniclifefenix.ui.profile.MyBankDataActivity;
import mx.com.bigtechsolutions.toniclifefenix.ui.profile.MyOrdersActivity;
import mx.com.bigtechsolutions.toniclifefenix.ui.profile.MyPromosActivity;
import mx.com.bigtechsolutions.toniclifefenix.ui.shoppingcart.StripePayActivity;
import mx.com.bigtechsolutions.toniclifefenix.viewmodel.ShoppingCartViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment  implements MyOptionRecyclerViewAdapter.OnOptionlistener {

    public ArrayList<String> options = new ArrayList<>();

    AuthApiClient authApiClient;
    AuthApiService authApiService;
    RecyclerView recyclerView;

    ShoppingCartViewModel mViewModel;

    public ProfileFragment() {
    }

    @SuppressWarnings("unused")
    public static ProfileFragment newInstance(int columnCount) {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(getActivity()).get(ShoppingCartViewModel.class);

        retrofitInit();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_list, container, false);

        recyclerView = view.findViewById(R.id.list_op);

        TextView distributorName = view.findViewById(R.id.distributor_name_profile);
        TextView distributorPoints = view.findViewById(R.id.accumulated_points_profile);

        String greetings = "Hola, " + SharedPreferencesManager.getStringValue(Constants.DISTRIBUTOR_NAME);
        String currentPoints = "Puntos acumulados en este mes: "+SharedPreferencesManager.getStringValue(Constants.CURRENT_POINTS);

        distributorName.setText(greetings);
        distributorPoints.setText(currentPoints);

        options.add("Promociones");
        options.add("Mis compras");
        options.add("Direcciones");
        options.add("Datos bancarios");
        options.add("Cerrar sesión");

        recyclerView.setAdapter(new MyOptionRecyclerViewAdapter(options, this));

        return view;
    }

    @Override
    public void onOptionClick(int position) {

        switch (position)
        {
            case 0:
                Intent intent = new Intent(MyFenixApp.getContext(), MyPromosActivity.class);
                startActivity(intent);
                break;
            case 1:
                Intent i = new Intent(MyFenixApp.getContext(), MyOrdersActivity.class);
                startActivity(i);
                break;
            case 2:
                Intent intentAddress = new Intent(MyFenixApp.getContext(), MyAddressesActivity.class);
                startActivity(intentAddress);
                break;
            case 3:
                Intent intentMyBankData = new Intent(MyFenixApp.getContext(), MyBankDataActivity.class);
                startActivity(intentMyBankData);
                break;
            case 4:
                logout();
                break;
        }

    }

    public void logout()
    {

        Call<GenericResponse<String>> call = authApiService.logout();
        final ProgressDialog loading = ProgressDialog.show(getContext(), "Cargando", "Por favor espere...", false, false);

        call.enqueue(new Callback<GenericResponse<String>>() {
            @Override
            public void onResponse(Call<GenericResponse<String>> call, Response<GenericResponse<String>> response) {

                if(response.isSuccessful())
                {
                    if (response.body().isSuccess())
                    {

                        mViewModel.deleteAll();

                        SharedPreferencesManager.removeValue(Constants.ACCESS_TOKEN);
                        SharedPreferencesManager.removeValue(Constants.BRANCH_ID);
                        SharedPreferencesManager.removeValue(Constants.COUNTRY);
                        SharedPreferencesManager.removeValue(Constants.DIST_COUNTRY);
                        SharedPreferencesManager.removeValue(Constants.CURRENT_POINTS);
                        SharedPreferencesManager.removeValue(Constants.NAME_PRODUCT_SEARCH);
                        SharedPreferencesManager.removeValue(Constants.STATE_NAME);
                        SharedPreferencesManager.removeValue(Constants.CURRENT_COUNTRY);

                        loading.dismiss();

                        Intent i = new Intent(MyFenixApp.getContext(), MainActivity.class);
                        startActivity(i);
                        getActivity().finish();
                    }
                } else{
                    loading.dismiss();
                    Toast.makeText(MyFenixApp.getContext(), "Error en el servidor", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<GenericResponse<String>> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(MyFenixApp.getContext(), "Error de conexión", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void retrofitInit() {

        authApiClient = AuthApiClient.getInstance();
        authApiService = authApiClient.getAuthApiService();

    }

}