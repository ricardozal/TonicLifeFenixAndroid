package mx.com.bigtechsolutions.toniclifefenix.ui;

import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import mx.com.bigtechsolutions.toniclifefenix.R;
import mx.com.bigtechsolutions.toniclifefenix.commons.Constants;
import mx.com.bigtechsolutions.toniclifefenix.commons.MyFenixApp;
import mx.com.bigtechsolutions.toniclifefenix.commons.SharedPreferencesManager;
import mx.com.bigtechsolutions.toniclifefenix.login.MainActivity;
import mx.com.bigtechsolutions.toniclifefenix.viewmodel.ShoppingCartViewModel;

public class ChooseCountryFragment extends DialogFragment {

    public static ChooseCountryFragment newInstance() {
        return new ChooseCountryFragment();
    }
    private View view;
    private RadioGroup countriesRBtn;
    private RadioButton radioMex, radioUsa;
    ShoppingCartViewModel mViewModel;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = new ViewModelProvider(getActivity()).get(ShoppingCartViewModel.class);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Elija un país");
        builder.setMessage("Debe elegir un país para continuar")
                .setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        int countryNumber = 0;

                        switch (countriesRBtn.getCheckedRadioButtonId()){
                            case R.id.radioButtonMexico:
                                countryNumber = 1;
                                break;
                            case R.id.radioButtonUSA:
                                countryNumber = 2;
                                break;
                        }

                        mViewModel.deleteAll();

                        SharedPreferencesManager.removeValue(Constants.BRANCH_ID);
                        SharedPreferencesManager.setStringValue(Constants.COUNTRY, String.valueOf(countryNumber));
                        SharedPreferencesManager.removeValue(Constants.NAME_PRODUCT_SEARCH);
                        Intent i = new Intent(MyFenixApp.getContext(), BottomNavigationActivity.class);
                        startActivity(i);
                        getActivity().finish();

                    }
                });

        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.choose_country_fragment, null);

        countriesRBtn = view.findViewById(R.id.radioGroupCountry);
        radioMex = view.findViewById(R.id.radioButtonMexico);
        radioUsa = view.findViewById(R.id.radioButtonUSA);

        String selected = SharedPreferencesManager.getStringValue(Constants.COUNTRY);

        if(selected != null){

            if(selected.equals("1")){
                radioMex.setChecked(true);
            } else if(selected.equals("2")){
                radioUsa.setChecked(true);
            }

        }

        builder.setView(view);

        // Create the AlertDialog object and return it
        return builder.create();
    }

}