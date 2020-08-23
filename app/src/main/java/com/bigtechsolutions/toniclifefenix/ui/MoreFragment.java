package com.bigtechsolutions.toniclifefenix.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bigtechsolutions.toniclifefenix.R;


public class MoreFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_more, container, false);

        ImageView blog = (ImageView)rootView.findViewById(R.id.ivBlog);
        blog.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://youtube.com"));
                getActivity().startActivity(intent);
            }
        });

        ImageView office = (ImageView)rootView.findViewById(R.id.ivOffice);
        office.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://youtube.com"));
                getActivity().startActivity(intent);
            }
        });

        ImageView square = (ImageView)rootView.findViewById(R.id.ivSquere);
        square.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://youtube.com"));
                getActivity().startActivity(intent);
            }
        });

        ImageView facebook = (ImageView)rootView.findViewById(R.id.iconfacebook);
        facebook.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://facebook.com"));
                getActivity().startActivity(intent);
            }
        });

        ImageView instagram = (ImageView)rootView.findViewById(R.id.iconInstagram);
        instagram.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/"));
                getActivity().startActivity(intent);
            }
        });

        ImageView whatsapp = (ImageView)rootView.findViewById(R.id.iconWhatsApp);
        whatsapp.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/5217223490089"));
                getActivity().startActivity(intent);
            }
        });

        ImageView tiktok = (ImageView)rootView.findViewById(R.id.iconTikTok);
        tiktok.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.tiktok.com/es/"));
                getActivity().startActivity(intent);
            }
        });

        return rootView;
    }
}