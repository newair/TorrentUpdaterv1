package newair.org.torrentupdaterandfilter;


import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import newair.org.torrentupdaterandfilter.RSS.RSSReaderEx;

/**
 * Created by newair on 7/3/13.
 */
public class TorrentList extends ListActivity {

    private NodeList torrentsUnformatted = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

  //setContentView(R.layout.torrentlist);

      while(torrentsUnformatted == null)
         torrentsUnformatted = RSSReaderEx.getStoredNodes();

        String[] torrents =  TorrentListManager.formatTorrents(torrentsUnformatted);

        setListAdapter(new ArrayAdapter<String>(TorrentList.this, R.layout.torrentlist, R.id.label, torrents));

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        super.onListItemClick(l, v, position, id);
        Toast.makeText(TorrentList.this,Integer.toString(position),Toast.LENGTH_SHORT);

      /*  Bundle data = getIntent().getExtras();
        String link = data.getString("link");*/   //commented for now

        Element element = (Element)torrentsUnformatted.item(position);
       String link= TorrentListManager.getElementValue(element,"link");


        Uri uri = Uri.parse(link);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);

    }

    @Override
    protected void onPause() {
        super.onPause();


    }


}