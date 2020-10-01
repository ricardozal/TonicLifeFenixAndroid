package mx.com.bigtechsolutions.toniclifefenix.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import mx.com.bigtechsolutions.toniclifefenix.R;
import mx.com.bigtechsolutions.toniclifefenix.commons.MyFenixApp;
import mx.com.bigtechsolutions.toniclifefenix.viewmodel.DistributorViewModel;
import mx.com.bigtechsolutions.toniclifefenix.viewmodel.interfaces.OnResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class WaitingActivity extends AppCompatActivity {

    DistributorViewModel distributorViewModel;
    ProgressDialog loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);

        loading = ProgressDialog.show(this, "Cargando", "Por favor espere...", false, false);


        distributorViewModel = new ViewModelProvider(this)
                .get(DistributorViewModel.class);

        setupFirebase();

    }

    public void setupFirebase()
    {

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {

                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            loading.dismiss();
                            Intent i = new Intent(MyFenixApp.getContext(), BottomNavigationActivity.class);
                            startActivity(i);
                            finish();
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        distributorViewModel.saveFirebaseToken(token, new OnResponse() {
                            @Override
                            public void OnSuccess(String title, String message) {
                                loading.dismiss();
                                FragmentManager fm = getSupportFragmentManager();
                                ChooseCountryFragment dialog = new ChooseCountryFragment();
                                dialog.setCancelable(false);
                                dialog.show(fm, "ChooseCountryFragment");
                            }

                            @Override
                            public void OnError(String title, String message) {
                                loading.dismiss();
                                Intent i = new Intent(MyFenixApp.getContext(), BottomNavigationActivity.class);
                                startActivity(i);
                                finish();
                            }
                        });
                    }
                });

    }
}