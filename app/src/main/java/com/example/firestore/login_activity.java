package com.example.firestore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firestore.Modal.Common;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class login_activity extends AppCompatActivity {
    public String name1;
    public String phone1;
    public EditText name;
    public EditText phone;
    public Button login;
    public Button sign_log;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    CollectionReference collectionReference = firebaseFirestore.collection("users");
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.editText);
        login = findViewById(R.id.login);
        sign_log = findViewById(R.id.id_log);
        sign_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(login_activity.this, phone_login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
//                     name1 = name.getText().toString().trim();
//                     phone1 = phone.getText().toString().trim();
//                     User USER = new User(name1,phone1, null);
//                     Common.user = USER;
                name1 = name.getText().toString().trim();
                phone1 = phone.getText().toString().trim();
                User USER = new User(name1 , phone1 , null);
                Common.user = USER;
                collectionReference.document(""+name1+phone1).set(USER);
                Intent intent = new Intent(login_activity.this, Main3Activity.class);
//                intent.putExtra("name1", name1);
//                intent.putExtra("phone1", phone1);
                Toast.makeText(login_activity.this, name1+phone1, Toast.LENGTH_SHORT).show();
//                intent.putExtra("USER1", USER.getName());
//                intent.putExtra("USER2", USER.getPhone());
                startActivity(intent);
            }
        });
    }
}

