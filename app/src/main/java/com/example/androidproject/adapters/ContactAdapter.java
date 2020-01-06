package com.example.androidproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.androidproject.R;
import com.example.androidproject.models.com.Contact;

import java.util.ArrayList;

public class ContactAdapter extends ArrayAdapter<Contact> {
    private ArrayList<Contact> contacts;

    public ContactAdapter(Context context, int resource, ArrayList<Contact> contacts){
        super(context,resource,contacts);
        this.contacts = contacts;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.contact,parent,false); // instancier layout xml
        ImageView imageView = (ImageView) convertView.findViewById(R.id.contact_image);
        imageView.setBackgroundResource(contacts.get(position).getImageId());
        TextView fullName = (TextView) convertView.findViewById((R.id.contact_name));
        fullName.setText(contacts.get(position).getFullName());
        TextView email = (TextView) convertView.findViewById((R.id.contact_mail));
        email.setText(contacts.get(position).getEmail());
        return  convertView;

    }
}
