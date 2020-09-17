package com.example.vigil;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.android.volley.toolbox.StringRequest;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class FcmMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String title = remoteMessage.getNotification().getTitle();
        String message = remoteMessage.getNotification().getBody();
        Map<String,String> data = remoteMessage.getData();
        String lat = data.get("lat");
        String lon = data.get("lon");
        String click_action = remoteMessage.getNotification().getClickAction();

        if(click_action.equals("CONFIRM")){
            Intent intent = new Intent(this, Confirmation.class);
            intent.putExtra("lat",lat);
            intent.putExtra("lon",lon);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
            notificationBuilder.setContentTitle(title);
            notificationBuilder.setContentText(message);
            notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
            notificationBuilder.setAutoCancel(true);
            notificationBuilder.setContentIntent(pendingIntent);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0,notificationBuilder.build());
        }

    }
}
