package mx.com.bigtechsolutions.toniclifefenix.ui.shoppingcart;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import mx.com.bigtechsolutions.toniclifefenix.R;
import mx.com.bigtechsolutions.toniclifefenix.api.requests.ChangeQuantityRequest;
import mx.com.bigtechsolutions.toniclifefenix.commons.MyFenixApp;
import mx.com.bigtechsolutions.toniclifefenix.viewmodel.ShoppingCartViewModel;
import com.google.android.material.textfield.TextInputLayout;


public class ShoppingCartChangeQuantityFragment extends DialogFragment {

    public static ShoppingCartChangeQuantityFragment newInstance() {
        return new ShoppingCartChangeQuantityFragment();
    }

    private View view;
    TextInputLayout quantity;
    int productId;
    ShoppingCartViewModel productViewModel;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle mArgs = getArguments();
        productId = mArgs.getInt("productId");

        productViewModel = new ViewModelProvider(this)
                .get(ShoppingCartViewModel.class);

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
                            Toast.makeText(MyFenixApp.getContext(), "Debes elegir la cantidad de producto válida", Toast.LENGTH_SHORT).show();
                        } else {
                            ChangeQuantityRequest changeQuantityRequest = new ChangeQuantityRequest(Integer.parseInt(quantityStr), productId);
                            productViewModel.updateQuantity(changeQuantityRequest);

                            Intent i = new Intent(MyFenixApp.getContext(), ShoppingCartActivity.class);
                            startActivity(i);
                            getActivity().finish();
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