package com.bigtechsolutions.toniclifefenix.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bigtechsolutions.toniclifefenix.R;
import com.bigtechsolutions.toniclifefenix.api.responses.models.ContentMobileResponse;
import com.bigtechsolutions.toniclifefenix.api.responses.models.Product;
import com.bigtechsolutions.toniclifefenix.commons.Constants;
import com.bigtechsolutions.toniclifefenix.commons.MyFenixApp;
import com.bigtechsolutions.toniclifefenix.ui.shoppingcart.ShoppingCartActivity;
import com.bigtechsolutions.toniclifefenix.viewmodel.ProductViewModel;
import com.bigtechsolutions.toniclifefenix.viewmodel.interfaces.OnContentResponse;

import java.util.List;


public class MoreFragment extends Fragment {

    ProductViewModel productViewModel;
    ProgressDialog loading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_more, container, false);

        loading = ProgressDialog.show(getActivity(), "Cargando", "Por favor espere...", false, false);

        productViewModel = new ViewModelProvider(getActivity())
                .get(ProductViewModel.class);

        productViewModel.getContentMobileApp(new OnContentResponse() {
            @Override
            public void OnSuccess(List<ContentMobileResponse> contents) {

                String blogYoutube = null;
                String virtualOffice = null;
                String square = null;
                String businessPlan = null;
                String catalog = null;
                String kits = null;
                String tutorial = null;
                String facebook = null;
                String instagram = null;
                String whatsapp = null;
                String tiktok = null;

                for (ContentMobileResponse content : contents) {
                    if(content.getKey().equals("BLOG_YOUTUBE") && content.getUrl() != null){
                        blogYoutube = content.getUrl();
                    } else if(content.getKey().equals("VIRTUAL_OFFICE") && content.getUrl() != null){
                        virtualOffice = content.getUrl();
                    } else if(content.getKey().equals("SQUARE") && content.getUrl() != null){
                        square = content.getUrl();
                    } else if(content.getKey().equals("BUSINESS_PLAN") && content.getUrl() != null){
                        businessPlan = content.getAbsoluteUrl();
                    } else if(content.getKey().equals("CATALOG") && content.getUrl() != null){
                        catalog = content.getAbsoluteUrl();
                    } else if(content.getKey().equals("KITS") && content.getUrl() != null){
                        kits = content.getAbsoluteUrl();
                    } else if(content.getKey().equals("TUTORIAL") && content.getUrl() != null){
                        tutorial = content.getAbsoluteUrl();
                    } else if(content.getKey().equals("FACEBOOK") && content.getUrl() != null){
                        facebook = content.getUrl();
                    } else if(content.getKey().equals("INSTAGRAM") && content.getUrl() != null){
                        instagram = content.getUrl();
                    } else if(content.getKey().equals("WHATSAPP") && content.getUrl() != null){
                        whatsapp = "https://wa.me/" + content.getUrl();
                    } else if(content.getKey().equals("TIKTOK") && content.getUrl() != null){
                       tiktok = content.getUrl();
                    }
                }


                ImageView blog = (ImageView)rootView.findViewById(R.id.ivBlog);
                String finalBlogYoutube = blogYoutube;
                blog.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        if(finalBlogYoutube != null){
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(finalBlogYoutube));
                            getActivity().startActivity(intent);
                        } else {
                            displayAlert("Atención", "Contenido no disponible", true);
                        }
                    }
                });

                ImageView office = (ImageView)rootView.findViewById(R.id.ivOffice);
                String finalVirtualOffice = virtualOffice;
                office.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        if(finalVirtualOffice != null){
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(finalVirtualOffice));
                            getActivity().startActivity(intent);
                        } else {
                            displayAlert("Atención", "Contenido no disponible", true);
                        }

                    }
                });

                ImageView squareImg = (ImageView)rootView.findViewById(R.id.ivSquere);
                String finalSquare = square;
                squareImg.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        if(finalSquare != null){
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(finalSquare));
                            getActivity().startActivity(intent);
                        } else {
                            displayAlert("Atención", "Contenido no disponible", true);
                        }
                    }
                });

                ImageView facebookImg = (ImageView)rootView.findViewById(R.id.iconfacebook);
                String finalFacebook = facebook;
                facebookImg.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        if(finalFacebook != null){
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(finalFacebook));
                            getActivity().startActivity(intent);
                        } else {
                            displayAlert("Atención", "Contenido no disponible", true);
                        }
                    }
                });

                ImageView instagramImg = (ImageView)rootView.findViewById(R.id.iconInstagram);
                String finalInstagram = instagram;
                instagramImg.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        if(finalInstagram != null){
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(finalInstagram));
                            getActivity().startActivity(intent);
                        } else {
                            displayAlert("Atención", "Contenido no disponible", true);
                        }
                    }
                });

                ImageView whatsappImg = (ImageView)rootView.findViewById(R.id.iconWhatsApp);
                String finalWhatsapp = whatsapp;
                whatsappImg.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        if(finalWhatsapp != null){
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(finalWhatsapp));
                            getActivity().startActivity(intent);
                        } else {
                            displayAlert("Atención", "Contenido no disponible", true);
                        }
                    }
                });

                ImageView tiktokImg = (ImageView)rootView.findViewById(R.id.iconTikTok);
                String finalTiktok = tiktok;
                tiktokImg.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        if(finalTiktok != null){
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(finalTiktok));
                            getActivity().startActivity(intent);
                        } else {
                            displayAlert("Atención", "Contenido no disponible", true);
                        }
                    }
                });

                ImageView plan = (ImageView)rootView.findViewById(R.id.ivPlan);
                String finalBusinessPlan = businessPlan;
                plan.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        if(finalBusinessPlan != null){
                            Bundle bundle = new Bundle();
                            bundle.putString("pdf", finalBusinessPlan);
                            bundle.putString("name", "Plan de Negocio");
                            Intent intent = new Intent(getActivity(), DocumentsViewActivity.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        } else {
                            displayAlert("Atención", "Contenido no disponible", true);
                        }

                    }
                });

                ImageView catalogImg = (ImageView)rootView.findViewById(R.id.ivCatalog);
                String finalCatalog = catalog;
                catalogImg.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        if(finalCatalog != null){
                            Bundle bundle = new Bundle();
                            bundle.putString("pdf", finalCatalog);
                            bundle.putString("name", "Catálogo");
                            Intent intent = new Intent(getActivity(), DocumentsViewActivity.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        } else {
                            displayAlert("Atención", "Contenido no disponible", true);
                        }
                    }
                });

                ImageView kitsImg = (ImageView)rootView.findViewById(R.id.ivKits);
                String finalKits = kits;
                kitsImg.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        if(finalKits != null){
                            Bundle bundle = new Bundle();
                            bundle.putString("pdf", finalKits);
                            bundle.putString("name", "Kits");
                            Intent intent = new Intent(getActivity(), DocumentsViewActivity.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        } else {
                            displayAlert("Atención", "Contenido no disponible", true);
                        }
                    }
                });

                ImageView tutorialImg = (ImageView)rootView.findViewById(R.id.ivTuto);
                String finalTutorial = tutorial;
                tutorialImg.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        if(finalTutorial != null){
                            Bundle bundle = new Bundle();
                            bundle.putString("pdf", finalTutorial);
                            bundle.putString("name", "Tutorial");
                            Intent intent = new Intent(getActivity(), DocumentsViewActivity.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        } else {
                            displayAlert("Atención", "Contenido no disponible", true);
                        }
                    }
                });

                loading.dismiss();

            }

            @Override
            public void OnError(String title, String message) {
                loading.dismiss();
                displayAlert(title, message, false);
            }
        });

        return rootView;
    }

    private void displayAlert(@NonNull String title,
                              @Nullable String message,
                              Boolean success) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle(title)
                .setMessage(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(success){
                    dialog.dismiss();
                } else {
                    Intent intent = new Intent(MyFenixApp.getContext(), BottomNavigationActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });
        builder.setCancelable(false);
        builder.create().show();
    }
}