package com.bigtechsolutions.toniclifefenix.ui.share_points;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.bigtechsolutions.toniclifefenix.R;
import com.bigtechsolutions.toniclifefenix.commons.Constants;
import com.bigtechsolutions.toniclifefenix.commons.MyFenixApp;
import com.bigtechsolutions.toniclifefenix.commons.SharedPreferencesManager;
import com.bigtechsolutions.toniclifefenix.ui.BottomNavigationActivity;
import com.bigtechsolutions.toniclifefenix.ui.profile.register_points.RegisterPointsActivity;
import com.bigtechsolutions.toniclifefenix.viewmodel.ShoppingCartViewModel;

public class ChooseSharePoints extends DialogFragment {

    public static ChooseSharePoints newInstance(){
        return new ChooseSharePoints();
    }

    private View view;
    private RadioGroup optionsRBtn;
    private RadioButton radioOpt1, radioOpt2;
    int addressId;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle mArgs = getArguments();
        addressId = mArgs.getInt("deliveryAddressId");

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Elija una opción");
        builder.setMessage("Debe elegir una opción para continuar")
                .setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        switch (optionsRBtn.getCheckedRadioButtonId()){
                            case R.id.radioButtonShare:
                                Intent intent = new Intent(MyFenixApp.getContext(), RegisterPointsActivity.class);
                                Bundle bundles = new Bundle();
                                bundles.putInt("addressId", addressId);
                                intent.putExtras(bundles);
                                startActivity(intent);
                                getActivity().finish();
                                break;
                            case R.id.radioButtonNotShare:
                                Intent i = new Intent(MyFenixApp.getContext(), FinishProcessActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putInt("addressId", addressId);
                                i.putExtras(bundle);
                                startActivity(i);
                                getActivity().finish();
                                break;
                        }
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });

        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.choose_options_share, null);

        optionsRBtn = view.findViewById(R.id.radioGroupOptions);

        builder.setView(view);

        // Create the AlertDialog object and return it
        return builder.create();
    }
}
