package pixel3d.io.androidcore.feature;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String TEST_TOAST_MESSAGE = "Test Toast Message";
    public static final int LENGTH_SHORT = Toast.LENGTH_SHORT;
    public static final String THIS_IS_MY_CUSTOM_TOAST_MESSAGE = "This is my custom Toast Message";
    private static final String CHANNEL_ID = "TEST_CHANNEL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showToast(View view){

        Context context = getApplicationContext();
//      TODO:: Toast make and show

//        Method 1 : Normal way
        Toast toast =Toast.makeText(context, TEST_TOAST_MESSAGE, LENGTH_SHORT);
        toast.show();

//        Method 2: Using Chaining rule
//        Toast.makeText(context,TEST_TOAST_MESSAGE, Toast.LENGTH_SHORT).show();


//      TODO:: Toast positioning
//        toast.setGravity(Gravity.TOP | Gravity.LEFT,0,0); // Top left corner

//        toast.setGravity(Gravity.CENTER,0,0); // centre position

//        Gravity.LEFT | Gravity.RIGHT | Gravity.TOP | Gravity.BOTTOM | Gravity.CENTER

        toast.setGravity(Gravity.BOTTOM,0,0); // Bottom

    }

//    TODO:: Inflate Custom Toast layout and show
//    Note: Do not use the public constructor for a Toast unless you are going to define the layout with setView(View).
//    If you do not have a custom layout to use, you must use makeText(Context, int, int) to create the Toast.
    public void showCustomToast(View view){

        LayoutInflater layoutInflater = getLayoutInflater();

//      SIDE NOTE :: WE CANT CHOOSE PART OF A LAYOUT FROM THE GIVEN LAYOUT FILE ( e.g. custom_toast layout file),
//      whereas custom_toast_container is an id of root element (LinearLayout) in the given layout file(custom_toast)
//      when tried to access "custom_sub_toast_container" it gave entire layout instead of intended one.

        View layout = layoutInflater.inflate(R.layout.custom_toast,(ViewGroup) findViewById(R.id.custom_toast_container));

        TextView textView =(TextView) layout.findViewById(R.id.toast_text);
        textView.setText(THIS_IS_MY_CUSTOM_TOAST_MESSAGE);


        // only use Toast Pubic constructor if and only if we can able to provide Layout for toast to inflate/render
        Toast toast = new Toast(getApplicationContext());


        toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
        toast.setDuration(LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }


//    TODO:: Create Notification and Notify

    public void CreateAndNotifyNotification(View view){

//        Creating Explicit intent to pass to notification for tap event handling
        Intent intent = new Intent(this,AlertDialog.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);

//        CREATING NOTIFICATION
        String textTitle = "notification title";
        String textContent = "here is some description for my notification";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(textTitle)
                .setContentText(textContent)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        createNotificationChannel();
//        SHOW NOTIFICATION

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        int notificationId = 1;
        notificationManagerCompat.notify(notificationId,builder.build());
    }

//   Create notification channel for SDK ver. 26+
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



//    Navigate to separate activity for Notification Activity Examples

    public void OpenNotificationActivityScreen(View view){
        Intent notificationActivity = new Intent(this,NotificationActivity.class);
        startActivity(notificationActivity);
    }

    public void OpenNotificationExampleActivityScreen(View view){
        Intent notificationExampleActivity = new Intent(this,NotificationExamplesActivity.class);
        startActivity(notificationExampleActivity);
    }

}
