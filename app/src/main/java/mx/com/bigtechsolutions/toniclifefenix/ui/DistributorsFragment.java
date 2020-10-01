package mx.com.bigtechsolutions.toniclifefenix.ui;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import mx.com.bigtechsolutions.toniclifefenix.R;
import mx.com.bigtechsolutions.toniclifefenix.commons.Constants;
import mx.com.bigtechsolutions.toniclifefenix.commons.SharedPreferencesManager;

public class DistributorsFragment extends Fragment {

    WebView webViewOrgChart;
    ProgressDialog loading;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_distributors,container, false);

        loading = ProgressDialog.show(getActivity(), "Cargando", "Por favor espere...", false, false);


        webViewOrgChart = (WebView)view.findViewById(R.id.WebViewOrgChart);
        webViewOrgChart.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                loading.dismiss();


            }


        });
        webViewOrgChart.getSettings().setJavaScriptEnabled(true);
        String url = Constants.API_TONIC_LIFE_FENIX_URL + "/" + SharedPreferencesManager.getStringValue(Constants.DISTRIBUTOR_TONIC_LIFE_ID) + "/org-chart-dist";
        webViewOrgChart.loadUrl(url);
        return view;
    }
}