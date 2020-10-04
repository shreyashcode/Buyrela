package com.example.firestore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.firestore.Modal.Common;
import com.example.firestore.Modal.Useraddress;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class address_order_activity extends AppCompatActivity {

    public FirebaseFirestore firebaseFirestore;
    public CollectionReference collectionReference;
    public TextInputLayout nickName;
    public TextInputLayout plotNumber;
    public TextInputLayout pinCode;
    public TextInputLayout landMark;
    public TextInputLayout strName;
    public TextInputLayout residentName;
    public Button add;
    public Useraddress useraddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseFirestore = FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_address_order_activity);
        useraddress = new Useraddress(null, null, null, null, null, null);
        nickName = findViewById(R.id.nickname);
        plotNumber = findViewById(R.id.number);
        pinCode = findViewById(R.id.pincode);
        landMark = findViewById(R.id.landmark);
        strName = findViewById(R.id.str_name);
        residentName = findViewById(R.id.resident);
        add = findViewById(R.id.add_id);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInput(v) == false)
                {
                    Toast.makeText(address_order_activity.this, "Added", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    collectionReference = firebaseFirestore.collection("users").document(Common.user.getName()+Common.user.getPhone()).collection("address");
                    useraddress.setArea(strName.getEditText().getText().toString().trim());
                    useraddress.setResidentName(residentName.getEditText().getText().toString().trim());
                    collectionReference.add(useraddress);
                    Intent intent = new Intent(address_order_activity.this, select_address_activity.class);
                    finish();
                    startActivity(intent);
                }
            }
        });
    }

    private boolean validateLandMark()
    {
        String landMark_string = landMark.getEditText().getText().toString().trim();
        if(landMark_string.isEmpty() == true)
        {
            landMark.setError("Cannot be empty");
            return false;
        }
        else
        {
            useraddress.setLandMark(landMark_string);
            landMark.setError(null);
            return true;
        }
    }

    private boolean validatePlotNumber()
    {
        String plotName = plotNumber.getEditText().getText().toString().trim();
        if(plotName.isEmpty() == true)
        {
            plotNumber.setError("Cannot be empth");
            return false;
        }
        else
        {
            useraddress.setPlotNumber(plotName);
            plotNumber.setError(null);
            return true;
        }
    }

    private boolean validateNickName()
    {
        String nick_name = nickName.getEditText().getText().toString().trim();
        if(nick_name.isEmpty() == true)
        {
            nickName.setError("Enter a nick name");
            return false;
        }
        else
        {
            useraddress.setName(nick_name);
            nickName.setError(null);
            return true;
        }
    }

    private boolean validatePinCode()
    {
        String get_pinCode = pinCode.getEditText().getText().toString().trim();
        if(get_pinCode.isEmpty() == true || get_pinCode.length() != 6)
        {
            pinCode.setError("Field must contain 6 characters");
            return false;
        }
        else
            {
                useraddress.setPinCode(get_pinCode);
                pinCode.setError(null);
            return true;
        }
    }

    public boolean validateInput(View v)
    {
        if(validateLandMark() == false || validateNickName() == false || validatePinCode() == false || validatePlotNumber() == false)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}