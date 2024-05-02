package com.example.myapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Data Source
   private ContactDatabase contactDatabase;
   private ArrayList<Contacts> contactsArrayList = new ArrayList<>();

    //Adapter

    private MyAdapter  myAdapter;

    //Binding
    private ActivityMainBinding mainBinding;
   private MainActivityClickHandlers handlers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        handlers = new MainActivityClickHandlers(this);

        mainBinding.setClickHandler(handlers);

        //Recycler view

        RecyclerView recyclerView = mainBinding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        //Database

        contactDatabase = ContactDatabase.getInstance(this);

        //ViewModel
        MyViewModel viewModel = new ViewModelProvider(this)
                .get(MyViewModel.class);


        //Inserting a new contact just for testing

//        Contacts c1 = new Contacts("Jack","jack@gmail.com");
//        viewModel.addNewContact(c1);

        //Loading the data from the room db
        viewModel.getAllContacts().observe(
                this,
                new Observer<List<Contacts>>() {
                    @Override
                    public void onChanged(List<Contacts> contacts) {

                        contactsArrayList.clear();

                        for(Contacts c : contacts){
                            Log.v("TAGY",c.getName());
                            contactsArrayList.add(c);

                        }

                        myAdapter.notifyDataSetChanged();
                    }
                }
        );

        //Adapter
        myAdapter = new MyAdapter(contactsArrayList);

        //Linking the recycler view with the adapter

        recyclerView.setAdapter(myAdapter);

        //swipe to delete
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                //on swiped left
                Contacts c = contactsArrayList.get(viewHolder.getAdapterPosition());
                viewModel.deleteContact(c);
                Toast.makeText(recyclerView.getContext(), "Contact deleted", Toast.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView(recyclerView);



    }
}