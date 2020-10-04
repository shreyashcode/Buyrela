package com.example.firestore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.firestore.Modal.Cart;
import com.example.firestore.Modal.Common;
import com.example.firestore.Modal.UserSlots;
import com.example.firestore.adapter.SlotsAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    public Intent intent;
    public FirebaseFirestore firebaseFirestore;
    public CollectionReference collectionReference;
    public CollectionReference reference;
    public TextView price;
    public ImageView imageView;
    public TextView quantity1;
    public TextView name;
    public ElegantNumberButton enb;
    public double rs;
    public double x;
    public double quantity;
    public Button button;
    public String name1;
    public String link1;
    public RecyclerView recyclerView;
    public SlotsAdapter slotsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_details_activity);
        //intent = getIntent();
        firebaseFirestore = FirebaseFirestore.getInstance();
        quantity = 0.25;
        recyclerView = findViewById(R.id.slots);
        reference = firebaseFirestore.collection("delivery");
        Query query = reference.limit(6);
        FirestoreRecyclerOptions<UserSlots> cart = new FirestoreRecyclerOptions.Builder<UserSlots>()
                .setQuery(query, UserSlots.class)
                .build();

        slotsAdapter = new SlotsAdapter(cart);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        recyclerView.setAdapter(slotsAdapter);

//        collectionReference = firebaseFirestore.collection("users")
//                .document(""+intent.getStringExtra("USER1")+intent.getStringExtra("USER2"))
//                        .collection("orders");
        collectionReference = firebaseFirestore.collection("users").document(Common.user.getName() + Common.user.getPhone()).collection("orders");

        name1 = Common.food.getName();
        link1 = Common.food.getLink();
        x = (double)Common.food.getKey();

        quantity1 = findViewById(R.id.quantity);
        enb = findViewById(R.id.enb);
        button = findViewById(R.id.id_add);
        price = findViewById(R.id.price);
        name = findViewById(R.id.heading);
        name.setText(name1);
        imageView = findViewById(R.id.vege);
        Picasso.get().load(link1).into(imageView);
        enb.setNumber("4");
        enb.setRange(1, 300);
        price.setText("Rs. "+x);
        enb.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num = enb.getNumber();
                rs = (Double.parseDouble(num))*(x/4);
                price.setText("Rs. "+rs);
                quantity = Double.parseDouble(num)*(0.25);
                quantity1.setText(quantity + " Kg");
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                model model1 = new model(rs, name1);
//                collectionReference.document("order1").set(model1);
                rs = (Double.parseDouble(enb.getNumber()))*(x/4);
                quantity = (Double.parseDouble(enb.getNumber())*0.25);
                Cart cart = new Cart(String.valueOf(rs), String.valueOf(quantity),name1,link1);
                collectionReference.document(name1).set(cart);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        slotsAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        slotsAdapter.stopListening();
    }
}
