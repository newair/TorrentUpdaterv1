package newair.org.torrentupdaterandfilter.monitor;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import newair.org.torrentupdaterandfilter.R;

/**
 * Created by newair on 7/12/13.
 */
public class TorrentMonitorList extends ListActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // String[] testArray = {"hello","world"};
        String[] torrentList = getIntent().getExtras().getStringArray("torrentList");

        setListAdapter(new ArrayAdapter<String>(TorrentMonitorList.this, R.layout.torrentlist, R.id.label, torrentList));

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }
}


