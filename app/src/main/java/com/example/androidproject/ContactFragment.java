package com.example.androidproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.androidproject.adapters.ContactAdapter;
import com.example.androidproject.models.com.Contact;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;


public class ContactFragment extends Fragment {

    @BindView(R.id.list_contacts)
    ListView list_contacts;

    ArrayList<Contact> contacts = new ArrayList<>();


    public ContactFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        ButterKnife.bind(this, view);

        contacts.add(new Contact(R.mipmap.contact_maker,"Nohaila Zouhair El Azami","nouhaila.zouhair @uit.ac.ma"));
        contacts.add(new Contact(R.mipmap.contact_maker,"Mohammed Daoudi","mohammed.daoudi@uit.ac.ma"));
        contacts.add(new Contact(R.mipmap.contact_maker,"Mestour Zouhair","mestour.zouhair@uit.ac.ma"));
        contacts.add(new Contact(R.mipmap.contact_maker,"Douirek Youssef","douirek.youssef@uit.ac.ma"));
        contacts.add(new Contact(R.mipmap.contact_maker,"Youness EL Housni","elhousni.yns@gmail.com"));
        ContactAdapter contactAdapter = new ContactAdapter(view.getContext(),R.layout.contact,contacts);
        list_contacts.setAdapter(contactAdapter);
        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }

    @OnItemClick(R.id.list_contacts)
    public void onListMovieItemClicked(int position){
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        Contact contact =contacts.get(position);
        String[] mails = new String[]{ contact.getEmail()};
        emailIntent.putExtra(Intent.EXTRA_EMAIL,mails);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,"project group : " + contact.getFullName() );
        emailIntent.putExtra(Intent.EXTRA_TEXT,"message");
        emailIntent.setType("text/plain");
        startActivity(Intent.createChooser(emailIntent,"send mail..."));
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }
}
