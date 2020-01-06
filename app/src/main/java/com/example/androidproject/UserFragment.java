package com.example.androidproject;

import android.content.Context;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;



public class UserFragment extends Fragment {


    @BindView(R.id.user_email)
    TextView user_email;

    @BindView(R.id.user_name)
    TextView user_name;

    @BindView(R.id.user_image)
    ImageView user_image;

    public UserFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this, view);
        setElements("youness","elhousni.yns@gmail.com",R.mipmap.ic_user_face);
        return  view;
    }

    public void setElements(String name,String email,int img){
        user_name.setText(name);
        user_email.setText(email);
        user_image.setBackgroundResource(img);

    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
