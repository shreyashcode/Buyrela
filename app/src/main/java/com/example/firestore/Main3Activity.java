package com.example.firestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.firestore.Modal.Common;
import com.example.firestore.Modal.Prefs;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class Main3Activity extends AppCompatActivity {
    FirebaseFirestore fb = FirebaseFirestore.getInstance();
    public Bundle intent1;
    public String user1;
    public String user2;
    public Button fab;
    public Prefs prefs;
    public ImageView imageView;
    public TextView textView;
    CollectionReference cr = fb.collection("Category");
    FoodAdapter foodAdapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        imageView = findViewById(R.id.id);
        textView = findViewById(R.id.name);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main3Activity.this, search_activity.class));
            }
        });

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        String token = task.getResult().getToken();
                        Log.d("oncomplete", token);
                    }
                });
        //intent1 = getIntent().getExtras();
        //Log.d("USER", intent1.getString("name1"));
        prefs = new Prefs(this);
        //Toast.makeText(Main3Activity.this, prefs.getUserName()+prefs.getUserPhone()+"SHARED_PREFERENCES", Toast.LENGTH_SHORT).show();
        String name = prefs.getUserName();
        String phone = prefs.getUserPhone();
        Toast.makeText(Main3Activity.this, name+phone+"common", Toast.LENGTH_SHORT).show();
        Log.d("user_id", name+phone);
        //Toast.makeText(Main3Activity.this, prefs.getUserName()+prefs.getUserPhone(), Toast.LENGTH_SHORT).show();
        Query query = cr.orderBy("key",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Food> fro = new FirestoreRecyclerOptions.Builder<Food>()
                .setQuery(query,Food.class)
                .build();
        foodAdapter = new FoodAdapter(fro);
        RecyclerView recyclerView;
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(foodAdapter);
        User user1 = new User(name, phone, null);
        Common.user = user1;
        textView.setText("Hello! "+Common.user.getName());
        fab = findViewById(R.id.cart_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main3Activity.this , My_basket.class));
            }
        });
//        user1 = intent1.getString("name1");
//        user2 = intent1.getString("phone1");
        //Log.d("User", user1 + " " + user2);
        cr.document("potato").update("name","potato");
        Food food = new Food("Suraj","xyz",100);
        cr.document("Suraj").set(food);
        foodAdapter.setItemClickListener(new FoodAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Intent intent = new Intent(Main3Activity.this, MainActivity.class);
                Food food = documentSnapshot.toObject(Food.class);
                /*
                intent for displaying name and price(KEY) in main activity
                */
                Common.food = food;
//                intent.putExtra("name", food.getName().toString());
//                intent.putExtra("link", food.getLink().toString());
//                intent.putExtra("key", food.getKey());

//                intent.putExtra("USER1", user1);
//                intent.putExtra("USER2", user2);

                Toast.makeText(Main3Activity.this, "Intent", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        foodAdapter.startListening();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        foodAdapter.stopListening();
    }
}

