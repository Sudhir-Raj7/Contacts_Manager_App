package com.example.myapp;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

public class AddNewContactClickHandlers {
    Contacts contact;
    Context context;

    MyViewModel myViewModel;

    public AddNewContactClickHandlers(Contacts contact, Context context,MyViewModel myViewModel) {
        this.contact = contact;
        this.context = context;
        this.myViewModel = myViewModel;
    }

   public void onSubmitBtnClicked(View view){
        if(contact.getName() == null || contact.getEmail() == null){
            Toast.makeText(context, "All field are required", Toast.LENGTH_SHORT).show();
        }
        else{
            Intent i = new Intent(context,MainActivity.class);

            Contacts c = new Contacts(contact.getName(),contact.getEmail());

            myViewModel.addNewContact(c);
            Toast.makeText(context, "Contact added", Toast.LENGTH_SHORT).show();

            context.startActivity(i);
        }
    }

}
