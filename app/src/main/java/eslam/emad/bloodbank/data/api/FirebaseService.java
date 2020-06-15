package eslam.emad.bloodbank.data.api;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.emad.bloodbank.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import eslam.emad.bloodbank.data.Constants;
import eslam.emad.bloodbank.data.MyApplication;

public class FirebaseService extends FirebaseMessagingService {

    private NotificationManagerCompat notificationManager;

    @Override
    public void onNewToken(String token) {
        //createNotification("Token", token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        createNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
    }

    private void createNotification(String title, String msg) {
        notificationManager = NotificationManagerCompat.from(getApplicationContext());

        Intent intent = new Intent("eslam.emad.bloodbank.ui.activities.HomeActivity");
        PendingIntent pIntent = PendingIntent.getActivity(MyApplication.getInstance(), 1 ,intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new NotificationCompat.Builder(MyApplication.getInstance(), MyApplication.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentText(msg)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setAutoCancel(true)
                .setContentIntent(pIntent)
                .build();

        notificationManager.notify(Constants.notification_id++, notification);
    }
}
