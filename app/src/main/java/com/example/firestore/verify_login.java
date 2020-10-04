package com.example.firestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class verify_login extends AppCompatActivity {

    public EditText editText;
    public Button button;
    public String mVerificationId;
    public FirebaseAuth mAuth;
    public CountDownTimer countDownTimer;
    public TextView coundD;
    public long timeLong = 1000;
    public Button resendOTP;
    public PhoneAuthProvider.ForceResendingToken mResend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_login);
        mAuth = FirebaseAuth.getInstance();
        final String phonenumber = getIntent().getStringExtra("num");
        sendVerificationCode(phonenumber);
        editText = findViewById(R.id.otp);
        button = findViewById(R.id.verify);
        coundD = findViewById(R.id.resend);
        resendOTP = findViewById(R.id.resendButton);
        resendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendVerificationCode(phonenumber, mResend);
                timeLong = 9000;
            }
        });
        countDownTimer = new CountDownTimer(timeLong, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String minTime = "";
                String secTime = "";
                if(millisUntilFinished == 0)
                {
                    Toast.makeText(verify_login.this, "0", Toast.LENGTH_SHORT).show();
                }
                timeLong = millisUntilFinished;
                int min = (int)timeLong/60000;
                int sec = (int)timeLong%60000;
                sec = sec / 1000;
                if(sec < 10)
                {
                    secTime = "0";
                }
                coundD.setText(secTime+sec + "sec");
            }

            @Override
            public void onFinish() {
                resendOTP.setVisibility(View.VISIBLE);
            }
        }.start();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String code = editText.getText().toString().trim();
                if(code.isEmpty() == true || code.length() < 6)
                {
                    Toast.makeText(verify_login.this, "Enter correct OTP", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    verifyVerificationCode(code);
                }
            }
        });
    }
    private void verifyVerificationCode(String code)
    {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential)
    {
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Intent intent = new Intent(verify_login.this, Main3Activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(verify_login.this, "OnError", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }

    private void sendVerificationCode(String mobile) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                 mobile,
                5,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    {
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {



            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                //storing the verification id that is sent to the user
                mVerificationId = s;
                mResend = forceResendingToken;
            }
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                //Getting the code sent by SMS
                String code = phoneAuthCredential.getSmsCode();

                //sometime the code is not detected automatically
                //in this case the code will be null
                //so user has to manually enter the code
                if (code != null) {
                    editText.setText(code);
                    //verifying the code
                    verifyVerificationCode(code);
                }
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(verify_login.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() != null)
        {
            Intent intent = new Intent(verify_login.this, login_activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}