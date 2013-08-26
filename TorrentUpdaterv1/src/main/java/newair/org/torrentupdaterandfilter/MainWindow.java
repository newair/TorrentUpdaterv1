package newair.org.torrentupdaterandfilter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by newair on 7/3/13.
 */
public class MainWindow extends Activity   {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainwindow);

       Button menue = (Button) findViewById(R.id.button1);

       menue.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               Intent i = new Intent(MainWindow.this,TorrentList.class);
               startActivity(i);

           }
       });

    }

    @Override
    protected void onPause() {
        super.onPause();

        finish();

    }
}