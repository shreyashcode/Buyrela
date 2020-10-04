package com.example.firestore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.firestore.Modal.Common;
import com.example.firestore.Modal.Order;
import com.example.firestore.Modal.Prefs;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class buy_activity extends AppCompatActivity {

    public TextView textView;
    public Button button;
    public Button buy;
    public CollectionReference collectionReference;
    public CollectionReference reference;
    public FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_activity);
        button = findViewById(R.id.id_id);
        buy = findViewById(R.id.id_buy);
        firebaseFirestore = FirebaseFirestore.getInstance();
       // collectionReference = firebaseFirestore.collection("user_orders");

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar.make(v, "Order placed", Snackbar.LENGTH_SHORT);
                snackbar.show();
                Order order = new Order();
                CollectionReference collectionReference = firebaseFirestore.collection("users");
                order.setAddress(Common.MAP.getInput());
                order.setMap_address(Common.MAP.getMap());
                order.setDelivery_when(null);
                order.setName(Common.user.getName());
                order.setPhone(Common.user.getPhone());
                order.setPrice(Common.ddouble);
                firebaseFirestore.collection("available_orders").document(Common.user.getName()+Common.user.getPhone()).set(order);
                reference = firebaseFirestore.collection("available_orders").document(Common.user.getName()+Common.user.getPhone()).collection("orders");
                firebaseFirestore.collection("users").document(Common.user.getName()+Common.user.getPhone()).collection("orders").get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                List<DocumentSnapshot> documentSnapshots = new ArrayList<>();
                                documentSnapshots = queryDocumentSnapshots.getDocuments();
                                for(DocumentSnapshot documentSnapshot1 : documentSnapshots)
                                {
                                    reference.document(documentSnapshot1.get("name").toString()).set(documentSnapshot1);
                                }
                            }
                        });
                firebaseFirestore.collection("users").document(Common.user.getName()+Common.user.getPhone()).collection("orders")
                        .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> documentSnapshots = new ArrayList<>();
                        documentSnapshots = queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot documentSnapshot: documentSnapshots)
                        {
                            documentSnapshot.getReference().delete();
                        }
                    }
                });
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(buy_activity.this, phone_login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
}