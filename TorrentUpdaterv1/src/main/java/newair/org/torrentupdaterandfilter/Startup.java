package newair.org.torrentupdaterandfilter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.HashMap;

import newair.org.torrentupdaterandfilter.DistanceCalculator.DistanceMeasurer;
import newair.org.torrentupdaterandfilter.RSS.RSSReaderEx;
import newair.org.torrentupdaterandfilter.monitor.MonitorNotification;
import newair.org.torrentupdaterandfilter.monitor.TorrentMonitor;

public class Startup extends Activity {

  private  SharedPreferences preferences;
  private  BackgroundTorrentUpdate backgroundTorrentUpdate;
    private Resources res;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      /*   timer = new Timer();

        Context context = getApplicationContext();
        timer.schedule(new ScheduleActivator(context), 0, 10000);
*/

         preferences = PreferenceManager.getDefaultSharedPreferences(this);


        setContentView(R.layout.startup);
        setToggleButtonValues();



        //set up dafault button values

        backgroundTorrentUpdate = new BackgroundTorrentUpdate();
        backgroundTorrentUpdate.execute();

        /*Intent i = new Intent(this,MainWindow.class);
        startActivity(i);*/

    }

    private void setToggleButtonValues() {

       ToggleButton moviesbutton = (ToggleButton) findViewById(R.id.moviesbutton);
        ToggleButton tvshowsbutton = (ToggleButton) findViewById(R.id.tvshowsbutton);
        ToggleButton animationbutton = (ToggleButton) findViewById(R.id.animationbutton);
        ToggleButton gamesbutton = (ToggleButton) findViewById(R.id.gamesbutton);
        ToggleButton audiobutton = (ToggleButton) findViewById(R.id.audiobutton);
        ToggleButton ebooksbutton = (ToggleButton) findViewById(R.id.ebooksbutton);
        ToggleButton otherbutton = (ToggleButton) findViewById(R.id.otherbutton);

         res = getResources();

        String[] categories = res.getStringArray(R.array.categories_array);

        for(String category :categories){

            boolean enabled =preferences.getBoolean(category,true);

            if(category.equals(res.getString(R.string.movies)))
                moviesbutton.setChecked(enabled);
            else if(category.equals(res.getString(R.string.tvshows)))
                tvshowsbutton.setChecked(enabled);
            else if(category.equals(res.getString(R.string.animation))) animationbutton.setChecked(enabled);
            else if(category.equals(res.getString(R.string.games))) gamesbutton.setChecked(enabled);
            else if(category.equals(res.getString(R.string.audio))) audiobutton.setChecked(enabled);
            else if(category.equals(res.getString(R.string.ebooks))) ebooksbutton.setChecked(enabled);
            else if(category.equals(res.getString(R.string.other))) otherbutton.setChecked(enabled);
            else
                System.out.println("");

        }

    }

    public class BackgroundTorrentUpdate extends
            AsyncTask<Void, HashMap<String,String>, Void> {

        private RSSReaderEx reader=null;

        /**
         *
         * @param params
         * @return void
         * description this method executes the main process of reading the RSS
         */

        @Override
        protected Void doInBackground(Void... params) {

            String previousTitle = null;
            while(true)
            {

                HashMap<String,String> result = reader.fetchResult();

                SystemClock.sleep(3000);
                if(result != null){

                    String title =result.get("title");



                    if(!title.equals(previousTitle)){
                        String matchedEntry=DistanceMeasurer.measure(title,preferences.getString("moitoringTorrentsString",""));

                        notifyMatchedEntry(result,matchedEntry);
                    }


                    previousTitle = title;
                }
                else System.out.println("Result Null");

            }
            //return null;
        }

        private void notifyMatchedEntry(HashMap<String, String> result, String matchedEntry) {

            if(matchedEntry != null){
              //  Toast.makeText(getApplicationContext(),"Torrent monitored :"+matchedEntry,Toast.LENGTH_LONG).show();
                result.put("id","1");

            }else{
              //  Toast.makeText(getApplicationContext(),"No match",Toast.LENGTH_SHORT).show();
                result.put("id","0");

            }
            onProgressUpdate(result);

        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub

           // reader.closeConnection();

        }

        @Override
        protected void onPreExecute() {

            System.out.println("Initializing RssReader");
            reader = RSSReaderEx.getInstance();
           // reader.openConnection(RssURL);
            reader.setContext(getApplicationContext());
        }

        /**
         *
         * @param values
         * description update when notification receives
         */

        @Override
        protected void onProgressUpdate(HashMap<String,String>... values) {

            String category = values[0].get("category");

      //      String[] categories = res.getStringArray(R.array.categories_array);

            int id = Integer.parseInt((String) values[0].get("id"));

            boolean storedState = true;

             if(category.toLowerCase().contains("movie")){
                 storedState =    preferences.getBoolean(res.getString(R.string.movies), true);

             }else if(category.toLowerCase().contains("tv")){
                 storedState =    preferences.getBoolean(res.getString(R.string.tvshows), true);

             }else if(category.toLowerCase().contains("games")){
                 storedState =    preferences.getBoolean(res.getString(R.string.games), true);

             }else if(category.toLowerCase().contains("anim")){
                 storedState =    preferences.getBoolean(res.getString(R.string.animation), true);

             }else if(category.toLowerCase().contains("audio") || category.toLowerCase().contains("music")){
                 storedState =    preferences.getBoolean(res.getString(R.string.audio), true);

             }else if(category.toLowerCase().contains("ebook")){
                 storedState =    preferences.getBoolean(res.getString(R.string.ebooks), true);

             }else if((category.toLowerCase().contains("porn") || category.toLowerCase().contains("xxx")) && category.toLowerCase().contains("video")){
                 storedState =    preferences.getBoolean(res.getString(R.string.other), true);

             }

            if(storedState){

            String title = values[0].get("title");
            String description = values[0].get("description");
            String link = values[0].get("link");

            // System.out.println(values[0].get("title"));
            if (!(category.toLowerCase().contains("porn") || category.toLowerCase().contains("xxx")) && category.toLowerCase().contains("video")) {

                System.out.println("Title: " + title);

                System.out.println("Category: " + category);

                System.out.println(description.split("Hash:")[0]);

            } else {

                System.out.println("got porn site");
            }

            Bundle sendBundle = new Bundle();
            sendBundle.putString("title", title);
            sendBundle.putString("category",category);
            sendBundle.putString("description",description.split("Hash:")[0]);
            sendBundle.putString("link",link);
            sendBundle.putInt("id",id);

            Context context = getApplicationContext();
            Intent i = new Intent(context, CreateNotificationActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtras(sendBundle);
            context.startService(i);


        }



        }

    }


    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }


    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.startup);
        setToggleButtonValues();

    }

    public void onMoviesClicked(View view){

        switchButton(view,res.getString(R.string.movies));


    }
    public void onTvShowsClicked(View view){

        switchButton(view,res.getString(R.string.tvshows));
    }
    public void onGamesClicked(View view){

        switchButton(view,res.getString(R.string.games));
    }
    public void onAnimationClicked(View view){

        switchButton(view, res.getString(R.string.animation));
    }
    public void onEbooksClicked(View view){

        switchButton(view, res.getString(R.string.ebooks));
    }
    public void onAudioClicked(View view){

        switchButton(view,res.getString(R.string.audio));
    }
    public void onOtherClicked(View view){

        switchButton(view, res.getString(R.string.other));
    }
    public void onExitClicked(View view){

        // Open confirmation dialog

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Startup.this);

        // Setting Dialog Title
        alertDialog.setTitle("Confirm Exit");

        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want to exit the Application?");

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {

                backgroundTorrentUpdate.cancel(true);

                        finish();
               // System.exit(0);
                // Write your code here to invoke YES event

            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                Toast.makeText(getApplicationContext(), "Exiting cancelled", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();

    }

    public void onViewAllClicked(View view){

        Intent intent = new Intent(getApplicationContext(),TorrentList.class);
        startActivity(intent);

    }
    public void onMonitorTorrentClicked(View view){

        Intent intent = new Intent(getApplicationContext(),TorrentMonitor.class);
        startActivity(intent);

    }


    private void switchButton(View view, String category){

        boolean on = ((ToggleButton) view).isChecked();

        SharedPreferences.Editor editor = preferences.edit();

        if (on) {
            Toast.makeText(this, category + " On", Toast.LENGTH_SHORT).show();
            editor.putBoolean(category,true);

        } else {
            Toast.makeText(this,category +" Off",Toast.LENGTH_SHORT).show();
            editor.putBoolean(category,false);
        }

        editor.commit();

    }

}