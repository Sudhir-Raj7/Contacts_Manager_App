package com.example.myapp;

import android.content.Context;
import android.content.Intent;
import android.view.View;

public class MainActivityClickHandlers {

    Context context;

    public MainActivityClickHandlers(Context context) {
        this.context = context;
    }

    public void onFabClicked(View view){
        Intent i = new Intent(view.getContext(),AddNewContactActivity.class);
        context.startActivity(i);
    }
}
