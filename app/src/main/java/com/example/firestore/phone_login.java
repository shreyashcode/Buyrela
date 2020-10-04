package com.example.firestore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firestore.Modal.Common;
import com.example.firestore.Modal.Prefs;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

public class phone_login extends AppCompatActivity {

    public Button button;
    public TextInputLayout editText;
    public String number;
    public TextInputLayout name1;
    public FirebaseFirestore firestore;
    public CollectionReference collectionReference;
    public Prefs prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);
        FirebaseMessaging.getInstance().subscribeToTopic("NOTIFY");
        button = findViewById(R.id.button);
        editText = findViewById(R.id.edit_text_phone);
        name1 = findViewById(R.id.name);
        prefs = new Prefs(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = editText.getEditText().getText().toString().trim();
                String name = name1.getEditText().getText().toString().trim();
                Toast.makeText(phone_login.this, name+number+number.length(), Toast.LENGTH_SHORT).show();
                if (number.isEmpty() == true || number.length() != 10 || name.isEmpty() == true)
                {
                    Toast.makeText(phone_login.this, "Enter", Toast.LENGTH_LONG).show();
                }
                else
                {
                        User USER = new User(name , number , null);
                        prefs = new Prefs(phone_login.this);
                        prefs.setUserName(name);
                        prefs.setUserPhone(number);
                        //Toast.makeText(phone_login.this, prefs.getUserName()+prefs.getUserName(), Toast.LENGTH_SHORT).show();
                        Common.user = USER;
                        firestore = FirebaseFirestore.getInstance();
                        collectionReference = firestore.collection("users");
                        collectionReference.document(""+name+number).set(USER);
                    Intent intent = new Intent(phone_login.this, verify_login.class);
                    intent.putExtra("num", "+91"+number);
                    Toast.makeText(phone_login.this, ""+Common.user.getPhone()+Common.user.getName(), Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent intent = new Intent(phone_login.this, Main3Activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}