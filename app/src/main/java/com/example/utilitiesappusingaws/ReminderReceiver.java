package com.example.utilitiesappusingaws;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
//Create new activity - destinationForNotification
public class ReminderReceiver extends BroadcastReceiver {
    //private final static String default_notification_channel_id = "notification_id" ;
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context,DestinationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent1,0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"notification_id");
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setContentTitle("Your reminder here!");
        builder.setContentText("Scheduled Notification");
        builder.setAutoCancel(true);
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(123,builder.build());
    }
}
