package pixel3d.io.androidcore.feature;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * NotificationActivity.java - This Activity demonstrate how to open up an activity from the notification
 * @author abdulkaiyum
 * @version 1.0
 *
 *  There are two ways
 *
 *  1. Regular Activity -- e.g. OnRegularActivityClick()
 *  Regular activity
 *         This is an activity that exists as a part of your app's normal UX flow.
 *         So when the user arrives in the activity from the notification, the new task should include a complete back stack,
 *         allowing them to press Back and navigate up the app hierarchy.
 *
 *  2. Special Activity -- e.g. OnSpecialActivityClick()
 *  Special activity
 *         The user only sees this activity if it's started from a notification.
 *         In a sense, this activity extends the notification UI by providing information that would be hard to display in
 *         the notification itself. So this activity does not need a back stack.
 */


public class NotificationActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "Channel1";
    private static final int NOTIFICATION_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
    }

    public void OnSpecialActivityClick(View view){
        Intent specialActivityIntent = new Intent(this,SpecialNotificationActivity.class);
        specialActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,specialActivityIntent,PendingIntent.FLAG_UPDATE_CURRENT);

//         FOR Supporting Android Version 7.1+ | SDK 26+
        createNotificationChannel();

        NotificationCompat.Builder builder  = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentTitle("Special Notification")
                .setContentText("On click of this you will navigate to a Special notification activity " +
                        "which can only be opened from this notification. Additionally this activity wont be put into the current navigation stack")
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());

    }

    public void OnRegularActivityClick(View view){

//        Create an intent for the activity we want to open
        Intent regularActivityIntent = new Intent(this, RegularNotificationActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        stackBuilder.addNextIntentWithParentStack(regularActivityIntent);

        PendingIntent regularActivityPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);


        createNotificationChannel();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentTitle("Regular Notification")
                .setContentText("On click of this you will navigate to a regular notification activity " +
                        "which is part of the App UX flow")
                .setContentIntent(regularActivityPendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());
    }

    private  void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
            CharSequence name = getString(R.string.channel_name);
            String desc = getString(R.string.channel_desc);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,name,importance);

            notificationChannel.setDescription(desc);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }

    }
}
