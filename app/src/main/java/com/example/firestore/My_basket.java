package com.example.firestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.firestore.Modal.Cart;
import com.example.firestore.Modal.Common;
import com.example.firestore.adapter.Cart_Adapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.model.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class My_basket extends AppCompatActivity {
    RecyclerView recyclerView;
    Cart_Adapter adapter;
    CollectionReference collectionReference;
    CollectionReference collectionReference1;
    CollectionReference savedRef;
    public Intent intent;
    FirebaseFirestore firebaseFirestore;
    FirebaseFirestore firebaseFirestore1;
    public Button buy;
    public AlertDialog.Builder alert;
    public AlertDialog dialog;
    public TextView map;
    public String string_string;
    public TextView input;
    public Button button1;
    public Button button2;
    public TextView cart_value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_basket);
        buy = findViewById(R.id.buy);
        Common.my_int = 0;
        cart_value = findViewById(R.id.id_id_1);
        firebaseFirestore = FirebaseFirestore.getInstance();
        collectionReference =  firebaseFirestore.collection("users").document(Common.user.getName() + Common.user.getPhone()).collection("orders");
        //savedRef = firebaseFirestore.collection("users").document(Common.user.getName() + Common.user.getPhone()).collection("Final Order");
      //  setItemPrice();
        Query q = collectionReference.limit(4);
        FirestoreRecyclerOptions<Cart> cart = new FirestoreRecyclerOptions.Builder<Cart>()
                .setQuery(q,Cart.class)
                .build();
        adapter = new Cart_Adapter(cart);
        recyclerView = findViewById(R.id.recycler_view_cart);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        /*Task<DocumentSnapshot> documentSnapshot = firebaseFirestore.collection("users").document(Common.user.getName() + Common.user.getPhone()).collection("order_price").document("Item_price").get();
        documentSnapshot.addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                string_string = documentSnapshot.get("price").toString();
                Common.ddouble = Double.parseDouble(string_string);
            }
        });*/
        cart_value.setText(Common.ddouble+"");
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Log.d("LOGD", "HEREONCLICK");
                firebaseFirestore.collection("users").document(Common.user.getName()+Common.user.getPhone()).collection("order_details").document("order_address").get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(final DocumentSnapshot documentSnapshot) {
                                Log.d("LOGD", "ONClick");

                                if(documentSnapshot.exists() == true)
                                {
                                    alert = new AlertDialog.Builder(My_basket.this);
                                    View view = getLayoutInflater().inflate(R.layout.newxml, null);

                                    map = view.findViewById(R.id.textView2);
                                    input = view.findViewById(R.id.textView);
                                    button1 = view.findViewById(R.id.button2);
                                    button2 = view.findViewById(R.id.button3);
                                    button1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            MapAddress mapAddress = new MapAddress();
                                            mapAddress.setMap(documentSnapshot.get("map").toString());
                                            mapAddress.setInput(documentSnapshot.get("input").toString());
                                            Common.MAP = mapAddress;
                                            intent = new Intent(My_basket.this, buy_activity.class);
                                            startActivity(intent);
                                        }
                                    });
                                    button2.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            intent = new Intent(My_basket.this, select_address_activity.class);
                                            startActivity(intent);
                                        }
                                    });
                                    map.setText(documentSnapshot.get("map").toString());
                                    input.setText(documentSnapshot.get("input").toString());
                                    alert.setView(view);
                                    dialog = alert.create();
                                    dialog.show();
                                }
                                else
                                {
                                    Log.d("LOGD", "HERE");
                                    intent = new Intent(My_basket.this, select_address_activity.class);
                                    startActivity(intent);
                                }
                            }
                        });
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Common.INT = 0;
        adapter.startListening();
        setItemPrice();
    }

    public void setItemPrice()
    {
        firebaseFirestore.collection("users").document(Common.user.getName()+Common.user.getPhone()).collection("orders").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.d("MESSAGEE", "messagee1");
                        Common.ddouble = 0.0;
                        List<DocumentSnapshot> my_list = new ArrayList<>();
                        my_list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot document : my_list) {
                            Common.ddouble = Common.ddouble + Double.parseDouble(document.get("price").toString());
                        }
                        cart_value.setText(Common.ddouble.toString());
                        Common.textView = cart_value;
                        Map<String, Object> map = new HashMap<>();
                        map.put("price", Common.ddouble);
                        firebaseFirestore1 = FirebaseFirestore.getInstance();
                        savedRef = firebaseFirestore1.collection("users").document(Common.user.getName() + Common.user.getPhone()).collection("order_price");
                        savedRef.document("Item_price").set(map);
                        Log.d("MESSAGEE", "messagee");
                    }
                });

    }

    @Override
    protected void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }
}