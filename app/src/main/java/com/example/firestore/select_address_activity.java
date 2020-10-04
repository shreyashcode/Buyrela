package com.example.firestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.firestore.Modal.Cart;
import com.example.firestore.Modal.Common;
import com.example.firestore.Modal.Useraddress;
import com.example.firestore.adapter.Cart_Adapter;
import com.example.firestore.adapter.address_adapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class select_address_activity extends AppCompatActivity {

    public Useraddress useraddress1;
    public Button edit;
    public RecyclerView recyclerView;
    public FirebaseFirestore firebaseFirestore;
    public CollectionReference collectionReference;
    public address_adapter adapter;
    public Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address_activity);
        button = findViewById(R.id.buy1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(Common.my_int == -1)
                {
                Intent intent = new Intent(select_address_activity.this, MapsActivity.class);
                finish();
                startActivity(intent);
                Common.my_int = 0;
                }
                else
                {
                    Toast.makeText(select_address_activity.this,"Please select an address OR add", Toast.LENGTH_SHORT).show();
                }}});
        edit = findViewById(R.id.my_select);
        recyclerView = findViewById(R.id.recyclerview_id);
        firebaseFirestore = FirebaseFirestore.getInstance();
        collectionReference =  firebaseFirestore.collection("users").document(Common.user.getName()+Common.user.getPhone()).collection("address");
        //savedRef = firebaseFirestore.collection("users").document(Common.user.getName() + Common.user.getPhone()).collection("Final Order");

        Query q = collectionReference.limit(4);
        FirestoreRecyclerOptions<Useraddress> useraddress = new FirestoreRecyclerOptions.Builder<Useraddress>()
                .setQuery(q,Useraddress.class)
                .build();
        useraddress1 = new Useraddress("name", "plot", "resident", "area", "new", "440030");
        adapter = new address_adapter(useraddress);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        collectionReference.document(useraddress1.getName()).set(useraddress1);
        Log.d("USER", useraddress1.getArea()+useraddress1.getName());
        //collectionReference.add(Common.useraddress);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                    Intent intent = new Intent(select_address_activity.this, address_order_activity.class);
                    startActivity(intent);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }

    }