package com.bigtechsolutions.toniclifefenix.ui;

import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bigtechsolutions.toniclifefenix.R;
import com.bigtechsolutions.toniclifefenix.commons.Constants;
import com.bigtechsolutions.toniclifefenix.commons.SharedPreferencesManager;

public class ChooseCountryFragment extends DialogFragment {

    public static ChooseCountryFragment newInstance() {
        return new ChooseCountryFragment();
    }
    private View view;
    private RadioGroup countriesRBtn;
    private RadioButton radioMex, radioUsa;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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

                        SharedPreferencesManager.setStringValue(Constants.COUNTRY, String.valueOf(countryNumber));

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