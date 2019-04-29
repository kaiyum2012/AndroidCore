package pixel3d.io.androidcore.feature;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import static android.app.Notification.EXTRA_NOTIFICATION_ID;

public class NotificationExamplesActivity extends AppCompatActivity {

    public static final String CHANNEL_ID="Default Channel";
    public static final String TAP_NOTIFICATION = "Tap notification";
    public static final int NOTIFICATION_ID = 1;
    public static final String NOTIFICATION_WITH_ACTION_BUTTON = "Notification With Action Button";
    public static final String ACTION_BUTTON = "Action Button";
    public static final String ACTION_SNOOZE = "Snooze";
    public static final String DIRECT_REPLY = "Direct Reply";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_examples);
    }

    public void TapActionNotification(View view){

        createNotificationChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.droid)
                .setContentTitle(TAP_NOTIFICATION)
                .setContentText("Example for Notification with tap handler")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


//        creating intent fot Tap action handler
        Intent intent = new Intent(this,AlertDialog.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);


        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);
//        or
//        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,intent,0);

//        setting up pending intent to notification builder
        builder.setContentIntent(pendingIntent);
//        on tap notification will disappear
        builder.setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());
    }

    public void ActionButtonNotification(View view){

        createNotificationChannel();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID);
        builder.setSmallIcon(R.drawable.notification_icon)
                .setContentText(NOTIFICATION_WITH_ACTION_BUTTON)
                .setContentTitle(ACTION_BUTTON)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

//       creating and adding Action button (e.g. Snooze) using intent
        Intent snoozeIntent = new Intent(this,MyBroadcastReceiver.class);
        snoozeIntent.setAction(ACTION_SNOOZE);
        snoozeIntent.putExtra(EXTRA_NOTIFICATION_ID,0);

        PendingIntent pendingSnoozeIntent = PendingIntent.getBroadcast(this,0,snoozeIntent,0);


        builder.addAction(R.drawable.droid,getString(R.string.snooze),pendingSnoozeIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());
    }


    public void ReplyActionNotification(View view){

//TODO:: implement when woking on conversation / chat module
        createNotificationChannel();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID);
        builder.setSmallIcon(R.drawable.droid)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentTitle(DIRECT_REPLY)
                .setContentText("Option to reply message/string directly from notification without opening up activity");

    }

    public void ExpandableNotification(View view){

        createNotificationChannel();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID);
    }


    private void createNotificationChannel() {

        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_desc);

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }


}

