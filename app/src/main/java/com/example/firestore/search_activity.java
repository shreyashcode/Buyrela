package com.example.firestore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.firestore.Modal.Common;
import com.example.firestore.Modal.Modal_Search;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class search_activity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView suggestions;
    EditText editText;
    List<String> list;
    public String name;
    Adapter_Search adapter;
    Suggestion_Adapter adapter_lastSearches;
    List<String> newFilterList;
    public CollectionReference collectionReference;
    CollectionReference lastsuggestion;
    public FirebaseFirestore firebaseFirestore;
    public ImageButton imageButton;
    public ImageView cart_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);

        firebaseFirestore = FirebaseFirestore.getInstance();
        lastsuggestion = firebaseFirestore.collection("users").document(Common.user.getName()+Common.user.getPhone()).collection("last_searches");
        initList();
        editText = findViewById(R.id.search_text);
        editText.requestFocus();
        cart_id = findViewById(R.id.cart_id);
        cart_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(search_activity.this, My_basket.class));
            }
        });

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter = new Adapter_Search(list);
        recyclerView.setAdapter(adapter);
        adapter.setItemClickListener(new Adapter_Search.OnItemClickListener() {
            @Override
            public void onItemClick(final String string, int position)
            {
                firebaseFirestore.collection("Category").get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots)
                            {
                                List<DocumentSnapshot> myList = new ArrayList<>();
                                myList = queryDocumentSnapshots.getDocuments();
                                for(DocumentSnapshot documentSnapshot: myList)
                                {
                                    if(documentSnapshot.get("name").equals(string))
                                    {
                                        Common.food = documentSnapshot.toObject(Food.class);
                                        Common.food.setKey(Integer.parseInt(documentSnapshot.get("key").toString()));
                                        Common.food.setLink(documentSnapshot.get("link").toString());
                                        Common.food.setName(documentSnapshot.get("name").toString());
                                        startActivity(new Intent(search_activity.this, MainActivity.class));
                                        break;
                                    }
                                }
                            }
                        });
            }
        });
        recyclerSetUp();



        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
        editText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

               /* if(event.getAction() == KeyEvent.KEYCODE_ENTER){
                    closeKeyboard();
                    Toast.makeText(MainActivity.this, "Enter Pressed",Toast.LENGTH_SHORT).show();
                }*/
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String toAdd = v.getText().toString();
                    Modal_Search ms = new Modal_Search(newFilterList.get(0));
                    lastsuggestion.add(ms);
                    name = newFilterList.get(0);
                    firebaseFirestore.collection("Category").get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    List<DocumentSnapshot> myList = new ArrayList<>();
                                    myList = queryDocumentSnapshots.getDocuments();
                                    for(DocumentSnapshot documentSnapshot: myList)
                                    {
                                        if(documentSnapshot.get("name").equals(name))
                                        {
                                            Common.food = documentSnapshot.toObject(Food.class);
                                            Common.food.setKey(Integer.parseInt(documentSnapshot.get("key").toString()));
                                            Common.food.setLink(documentSnapshot.get("link").toString());
                                            Common.food.setName(documentSnapshot.get("name").toString());
                                            startActivity(new Intent(search_activity.this, MainActivity.class));
                                            break;
                                        }
                                    }
                                }
                            });


                    return true;
                }
                return false;
            }
        });


    }

    private void recyclerSetUp() {
        suggestions = findViewById(R.id.r2);
        suggestions.setLayoutManager(new LinearLayoutManager(this));

        Query q = lastsuggestion.limit(74);
        FirestoreRecyclerOptions<Modal_Search> ls = new FirestoreRecyclerOptions.Builder<Modal_Search>()
                .setQuery(q,Modal_Search.class)
                .build();
        adapter_lastSearches = new Suggestion_Adapter(ls);
        suggestions.setHasFixedSize(true);
        suggestions.setAdapter(adapter_lastSearches);
        adapter_lastSearches.setItemClickListener(new Suggestion_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(final String string, int position)
            {
                firebaseFirestore.collection("Category").get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots)
                            {
                                List<DocumentSnapshot> myList = new ArrayList<>();
                                myList = queryDocumentSnapshots.getDocuments();
                                for(DocumentSnapshot documentSnapshot: myList)
                                {
                                    if(documentSnapshot.get("name").equals(string))
                                    {
                                        Common.food = documentSnapshot.toObject(Food.class);
                                        Common.food.setKey(Integer.parseInt(documentSnapshot.get("key").toString()));
                                        Common.food.setLink(documentSnapshot.get("link").toString());
                                        Common.food.setName(documentSnapshot.get("name").toString());
                                        startActivity(new Intent(search_activity.this, MainActivity.class));
                                        break;
                                    }
                                }
                            }
                        });
            }
        });
    }

    private void initList() {
        list = new ArrayList<>();
        list.add("tomato");
        list.add("potato");
        list.add("boda");
        list.add("palak");
        list.add("boda");
        list.add("gobi");
        list.add("lauki");
        list.add("Radish");
        list.add("adark");
        list.add("Kakdi");
        list.add("carrot");
    }

    public void closeKeyboard(){
        View view = this.getCurrentFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(),0);
    }

    private void filter(String text) {
        ArrayList<String> filteredLit = new ArrayList<>();
        for (String search : list){
            if(search.toLowerCase().contains(text.toLowerCase())){
                filteredLit.add(search);
            }
        }
        newFilterList = filteredLit;
        adapter.filter(filteredLit);


    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter_lastSearches.startListening();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        adapter_lastSearches.stopListening();
    }
}