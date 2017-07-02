package com.darte.vaibhav.anti_detention;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

/**
 * Created by VAIBHAV on 14-09-2016.
 */
public class Mytrigger extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

       // Toast.makeText(context, "Alarm triggered", Toast.LENGTH_LONG).show();

        NotificationManager notificationManager= (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);


        PendingIntent pendingIntent=PendingIntent.getActivity(context,100,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        Uri sound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder=new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.noticon)
                .setContentTitle("Reminder")
                .setContentText("Time for the lecture")
                .setSound(sound)
                .setAutoCancel(true);
        notificationManager.notify(100, builder.build());
    }
}
