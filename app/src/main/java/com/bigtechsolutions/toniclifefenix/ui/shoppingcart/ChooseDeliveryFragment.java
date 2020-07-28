package com.bigtechsolutions.toniclifefenix.ui.shoppingcart;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.bigtechsolutions.toniclifefenix.R;
import com.bigtechsolutions.toniclifefenix.login.MainActivity;
import com.bigtechsolutions.toniclifefenix.ui.BottomNavigationActivity;


public class ChooseDeliveryFragment extends DialogFragment{

    public static ChooseDeliveryFragment newInstance() {
        return new ChooseDeliveryFragment();
    }

    private View view;
    private RadioGroup deliveryOptions;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Seleccione un método de entrega")
                .setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent i;

                        switch (deliveryOptions.getCheckedRadioButtonId()){
                            case R.id.radioButtonAddresses:
                                i = new Intent(getContext(), ShoppingCartAddressesActivity.class);
                                startActivity(i);
                                getActivity().finish();
                                break;
                            case R.id.radioButtonBranches:
                                i = new Intent(getContext(), ShoppingCartBranchesActivity.class);
                                startActivity(i);
                                getActivity().finish();
                                break;
                        }

                    }
                });

        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.fragment_choose_delibery, null);

        deliveryOptions = view.findViewById(R.id.radioGroupDeliveryOptions);

        builder.setView(view);

        // Create the AlertDialog object and return it
        return builder.create();
    }

}