package com.example.myapp;

import android.app.Application;
import android.os.Looper;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import android.os.Handler;

import androidx.lifecycle.LiveData;

public class Repository {

    private final ContactDAO contactDAO;
    public ExecutorService executor;
   public Handler handler;

    public Repository(Application application) {

        ContactDatabase contactDatabase = ContactDatabase.getInstance(application);

        this.contactDAO = contactDatabase.getContactDao();
        executor = Executors.newSingleThreadExecutor();

        handler = new Handler(Looper.getMainLooper());
    }

    //methods in dao being executed from Repository

    public void addContact(Contacts contact){

        executor.execute(new Runnable() {
            @Override
            public void run() {
                contactDAO.insert(contact);
            }
        });


    }

    public void deleteContact(Contacts contact){

        executor.execute(new Runnable() {
            @Override
            public void run() {
                contactDAO.delete(contact);
            }
        });


    }

    public LiveData<List<Contacts>> getAllContacts(){

        return contactDAO.getAllContacts();
    }

    }

