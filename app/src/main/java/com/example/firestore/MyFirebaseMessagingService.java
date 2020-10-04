package com.example.firestore;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService
{
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {
        super.onMessageReceived(remoteMessage);
        if(remoteMessage.getNotification() != null)
        {
            Log.d("LOGD", remoteMessage.getNotification().getBody()+"  "+remoteMessage.getNotification().getTitle());
        }
    }
}