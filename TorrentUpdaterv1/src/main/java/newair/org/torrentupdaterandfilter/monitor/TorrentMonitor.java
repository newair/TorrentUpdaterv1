package newair.org.torrentupdaterandfilter.monitor;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.xml.validation.TypeInfoProvider;

import newair.org.torrentupdaterandfilter.DistanceCalculator.StringManager;
import newair.org.torrentupdaterandfilter.R;

/**
 * Created by newair on 7/12/13.
 */
public class TorrentMonitor extends Activity {
    private SharedPreferences preferences;

    private String defaultsString = "";
    private JSONArray json;
    private ArrayList<String> monitoringTorrents;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.torrent_monitor);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);


        //load all data
        //  defaultsSet = new HashSet<String>();

        String monitoringTorrentsAsString = preferences.getString("moitoringTorrents", "");

        monitoringTorrents = StringManager.breakString(monitoringTorrentsAsString);

    }

    public void monitorClicked(View view) {

        TextView txt = (TextView) findViewById(R.id.editText);

        if (txt.getTextSize() == 0) Toast.makeText(TorrentMonitor.this, "Please enter content", Toast.LENGTH_SHORT).show();
        else {

            String torrent = txt.getText().toString();
            SharedPreferences.Editor editor = preferences.edit();

            // moitoringTorrents.add(torrent);
            monitoringTorrents.add(torrent);
                 String constructedString = StringManager.constructString(monitoringTorrents.toArray(new String[monitoringTorrents.size()]));

            editor.putString("moitoringTorrentsString", constructedString);
            try{
            editor.commit();
            }catch(Exception e){

                Toast.makeText(TorrentMonitor.this,"Torrent entering failed", Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(TorrentMonitor.this,"Monitoring started", Toast.LENGTH_SHORT).show();
            txt.getEditableText().clear();
        }

    }




    public void monitoringListClicked(View view) {

        String moitoringTorrentsString = preferences.getString("moitoringTorrentsString", defaultsString);

        monitoringTorrents = StringManager.breakString(moitoringTorrentsString);

        if(monitoringTorrents != null){
        Intent intent = new Intent(getApplicationContext(), TorrentMonitorList.class);

        intent.putExtra("torrentList", monitoringTorrents.toArray(new String[monitoringTorrents.size()]));
        startActivity(intent);}

        else Toast.makeText(TorrentMonitor.this,"No torrents to display",Toast.LENGTH_SHORT).show();

    }


}

