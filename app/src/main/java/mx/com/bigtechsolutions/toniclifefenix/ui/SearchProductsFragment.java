package mx.com.bigtechsolutions.toniclifefenix.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import mx.com.bigtechsolutions.toniclifefenix.R;
import mx.com.bigtechsolutions.toniclifefenix.commons.Constants;
import mx.com.bigtechsolutions.toniclifefenix.commons.MyFenixApp;
import mx.com.bigtechsolutions.toniclifefenix.commons.SharedPreferencesManager;
import mx.com.bigtechsolutions.toniclifefenix.viewmodel.ShoppingCartViewModel;
import com.google.android.material.textfield.TextInputLayout;

public class SearchProductsFragment extends DialogFragment {

    private View view;
    TextInputLayout nameProduct;

    public static SearchProductsFragment newInstance(){
        return new SearchProductsFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Buscar productos");
        builder.setMessage("Ecriba el nombre del producto")
                .setPositiveButton("Buscar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        String nameStr = nameProduct.getEditText().getText().toString();

                        if (nameStr.isEmpty()){
                            Toast.makeText(MyFenixApp.getContext(), "Debes escribir el nombre de un producto", Toast.LENGTH_SHORT).show();
                        } else {

                            SharedPreferencesManager.setStringValue(Constants.NAME_PRODUCT_SEARCH, nameStr);

                            Intent i = new Intent(MyFenixApp.getContext(), BottomNavigationActivity.class);
                            startActivity(i);
                            getActivity().finish();

                        }

                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.search_product_fragment, null);

        nameProduct = view.findViewById(R.id.nameProduct);


        builder.setView(view);

        // Create the AlertDialog object and return it
        return builder.create();
    }

}
