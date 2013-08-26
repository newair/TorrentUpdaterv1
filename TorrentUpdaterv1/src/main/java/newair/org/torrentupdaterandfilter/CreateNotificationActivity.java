package newair.org.torrentupdaterandfilter;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

public class CreateNotificationActivity extends Service {
    private NotificationManager notificationManager;

    @Override
    public void onCreate() {
        super.onCreate();

       /* Bundle data = getIntent().getExtras();
        String title = data.getString("title");
        String category = data.getString("category");
        String description = data.getString("description");
        String link = data.getString("link");

        createNotification(title,category,description,link);*/
    }

    private void createNotification(String title, String category, String description, String link, int id) {
        // Prepare intent which is triggered if the
        // notification is selected
        Intent intent = new Intent(this, NotificationReceiverActivity.class);
        Bundle sendBundle = new Bundle();
        sendBundle.putString("link", link);
        intent.putExtras(sendBundle);

        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
        // Build notification
        // Actions are just fake
       try{
           int icon;
           if(id == 0){

                icon = R.drawable.all_color;
           }else {
               icon = R.drawable.monitor_color;
           }

           String[] titleSplitted = title.split(" ");

           String title1 = "",title2 ="";

           for(String s : titleSplitted){

               if(title1.length() < 23)
               title1 = title1+" " +s ;
               else{
                   title2  = title2+" " +s;

               }
           }

                Notification noti = new NotificationCompat.Builder(this)
                .setContentTitle(title1)
                .setContentText(title2)
                .setSmallIcon(icon)
                //.setContentInfo(description)
                 .setTicker("torrent update")
                  .setContentInfo(category)
                .setWhen(0)
                .setContentIntent(pIntent)
                .build();


         notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Hide the notification after its selected
        noti.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(id, noti);}
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
        String title = data.getString("title");
        String category = data.getString("category");
        String description = data.getString("description");
        String link = data.getString("link");
        int id    = data.getInt("id");

        createNotification(title,category,description,link,id);
        return START_STICKY;
    }


    public class LocalBinder extends Binder {
        CreateNotificationActivity getService() {
            return CreateNotificationActivity.this;
        }
    }



}
