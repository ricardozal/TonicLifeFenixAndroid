package com.bigtechsolutions.toniclifefenix.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigtechsolutions.toniclifefenix.R;
import com.google.android.material.textfield.TextInputLayout;


public class ShoppingCartChangeQuantityFragment extends DialogFragment {

    public static ShoppingCartChangeQuantityFragment newInstance() {
        return new ShoppingCartChangeQuantityFragment();
    }

    private View view;
    TextInputLayout quantity;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Cambiar cantidad");
        builder.setMessage("Ingrese la nueva cantidad de este producto")
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        String quantityStr = quantity.getEditText().getText().toString();

                        if (quantityStr.isEmpty() || Integer.parseInt(quantityStr) < 1){
                            quantity.setError("Debes elegir la cantidad de producto válida");
                        } else {

                        }

                    }
                });

        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.fragment_shopping_cart_change_quantity, null);

//        countriesRBtn = view.findViewById(R.id.radioGroupCountry);
        quantity = view.findViewById(R.id.updateQuantity);

        builder.setView(view);

        // Create the AlertDialog object and return it
        return builder.create();
    }

}