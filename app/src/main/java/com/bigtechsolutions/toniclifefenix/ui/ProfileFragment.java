package com.bigtechsolutions.toniclifefenix.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bigtechsolutions.toniclifefenix.R;
import com.bigtechsolutions.toniclifefenix.api.ApiClient;
import com.bigtechsolutions.toniclifefenix.api.AuthApiClient;
import com.bigtechsolutions.toniclifefenix.api.AuthApiService;
import com.bigtechsolutions.toniclifefenix.api.responses.GenericResponse;
import com.bigtechsolutions.toniclifefenix.commons.Constants;
import com.bigtechsolutions.toniclifefenix.commons.MyFenixApp;
import com.bigtechsolutions.toniclifefenix.commons.SharedPreferencesManager;
import com.bigtechsolutions.toniclifefenix.login.MainActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment  implements MyOptionRecyclerViewAdapter.OnOptionlistener {

    public ArrayList<String> options = new ArrayList<>();

    AuthApiClient authApiClient;
    AuthApiService authApiService;
    RecyclerView recyclerView;

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

        distributorName.setText(greetings);
        distributorPoints.setText("Puntos acumulados en este mes: 1524");

        options.add("Promociones");
        options.add("Mis compras");
        options.add("Direcciones");
        options.add("Métodos de pago");
        options.add("Cerrar sesión");

        recyclerView.setAdapter(new MyOptionRecyclerViewAdapter(options, this));

        return view;
    }

    @Override
    public void onOptionClick(int position) {

        switch (position)
        {
            case 0:
                Toast.makeText(MyFenixApp.getContext(), "Promos", Toast.LENGTH_LONG).show();
                break;
            case 1:
                Toast.makeText(MyFenixApp.getContext(), "Mis compras", Toast.LENGTH_LONG).show();
                break;
            case 2:
                Toast.makeText(MyFenixApp.getContext(), "Direcciones", Toast.LENGTH_LONG).show();
                break;
            case 3:
                Toast.makeText(MyFenixApp.getContext(), "Metodos de pago", Toast.LENGTH_LONG).show();
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
                        SharedPreferencesManager.removeValue(Constants.ACCESS_TOKEN);

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