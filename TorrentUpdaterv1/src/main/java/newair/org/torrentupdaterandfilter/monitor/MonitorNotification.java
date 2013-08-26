package newair.org.torrentupdaterandfilter.monitor;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import newair.org.torrentupdaterandfilter.NotificationReceiverActivity;
import newair.org.torrentupdaterandfilter.R;

/**
 * Created by newair on 7/21/13.
 */
public class MonitorNotification extends Service {
    private NotificationManager notificationManager;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    private void createNotification(String savedTitle, String link) {
        // Prepare intent which is triggered if the
        // notification is selected
        Intent intent = new Intent(this, NotificationReceiverActivity.class);
        Bundle sendBundle = new Bundle();
       sendBundle.putString("link", link);
        intent.putExtras(sendBundle);

        PendingIntent pIntent = PendingIntent.getActivity(this, 1, intent, 0);
        // Build notification
        // Actions are just fake
        try{ Notification noti = new NotificationCompat.Builder(this)
                .setContentTitle("Torrent Arrived")
                .setContentText(savedTitle).setSmallIcon(R.drawable.ic_launcher)
                .setContentInfo("Description ").setSmallIcon(R.drawable.ic_launcher)
                .setContentIntent(pIntent)
                .build();


            notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            // Hide the notification after its selected
               noti.flags |= Notification.FLAG_AUTO_CANCEL;

            notificationManager.notify(1, noti);}
        catch (Exception e)
        {e.printStackTrace();}

    }

    private final IBinder mBinder = new LocalBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        notificationManager.cancel(Integer.parseInt(NOTIFICATION_SERVICE));

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Bundle data = intent.getExtras();
       String savedTitle = data.getString("savedTitle");
        /* String category = data.getString("category");
        String description = data.getString("description");
    */    String link = data.getString("link");

        createNotification(savedTitle,link);
        return START_STICKY;
    }


    public class LocalBinder extends Binder {
        MonitorNotification getService() {
            return MonitorNotification.this;
        }
    }

}
